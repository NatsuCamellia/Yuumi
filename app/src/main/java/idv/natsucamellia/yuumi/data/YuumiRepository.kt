package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.DataDragonApiService
import idv.natsucamellia.yuumi.network.MatchDto
import idv.natsucamellia.yuumi.network.RiotApiService
import idv.natsucamellia.yuumi.network.SummonerDto
import idv.natsucamellia.yuumi.network.getParticipant
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.HttpException

interface YuumiRepository {
    suspend fun getSummonerDtoByName(summonerName: String): SummonerDto?
    suspend fun getSummonerProfile(summonerDto: SummonerDto): SummonerProfile
}

class NetworkYuumiRepository(
    private val apiKey: String,
    private val riotApiService: RiotApiService,
    private val dataDragonApiService: DataDragonApiService
): YuumiRepository {

    private val version by lazy {
        runBlocking {
            dataDragonApiService.getVersions()[0]
        }
    }
    private val championIdNameMap: Map<Long, String> by lazy {
        getChampions(version)
    }

    private fun getChampions(
        version: String
    ): MutableMap<Long, String> {
        val championIdNameMap: MutableMap<Long, String> = mutableMapOf()
        runBlocking {
            launch {
                val champions = dataDragonApiService.getChampions(version)
                for (entry in champions.data) {
                    val name = entry.value.name
                    val id = entry.value.key.toLong()
                    championIdNameMap[id] = name
                }
            }
        }
        return championIdNameMap
    }

    override suspend fun getSummonerDtoByName(summonerName: String): SummonerDto? {
        return try {
            riotApiService.getSummonerDtoByName(summonerName, apiKey)
        } catch (e: HttpException) {
            null
        }
    }

    override suspend fun getSummonerProfile(summonerDto: SummonerDto): SummonerProfile {
        val summonerInfo = SummonerInfo(
            name = summonerDto.name,
            level = summonerDto.summonerLevel,
            profileIconUrl = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/profileicon/${summonerDto.profileIconId}.png"
        )
        val championMasteryList = getChampionMasteryTop(
            puuid = summonerDto.puuid,
            count = 5
        )
        val matchDtoList = getRecentMatches(
            puuid = summonerDto.puuid,
            count = 5
        )
        val matches = matchDtoList.map {
            val participant = it.getParticipant(puuid = summonerDto.puuid)!!
            MatchSummary(
                win = participant.win,
                championIconUrl = getChampionIconUrl(participant.championName),
                summoner1IconUrl = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/spell/SummonerHeal.png",
                summoner2IconUrl = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/spell/SummonerFlash.png",
                perk1IconUrl = "https://ddragon.leagueoflegends.com/cdn/img/perk-images/Styles/Precision/Conqueror/Conqueror.png",
                perk2IconUrl = "https://ddragon.leagueoflegends.com/cdn/img/perk-images/Styles/7200_Domination.png",
                kills = participant.kills,
                deaths = participant.deaths,
                assists = participant.assists,
                item0Icon = getItemIconUrl(participant.item0),
                item1Icon = getItemIconUrl(participant.item1),
                item2Icon = getItemIconUrl(participant.item2),
                item3Icon = getItemIconUrl(participant.item3),
                item4Icon = getItemIconUrl(participant.item4),
                item5Icon = getItemIconUrl(participant.item5),
                item6Icon = getItemIconUrl(participant.item6),
                gameDuration = it.info.gameDuration,
                gameEndTimestamp = it.info.gameEndTimestamp,
                gameMode = it.info.gameMode
            )

        }
        return SummonerProfile(
            summonerDto = summonerDto,
            info = summonerInfo,
            championMasteryList = championMasteryList,
            matches = matches
        )
    }

    private suspend fun getChampionMasteryTop(
        puuid: String,
        count: Int
    ): List<ChampionMastery> {
        val dtoList = riotApiService.getChampionMasteryTopByPuuid(
            puuid = puuid,
            count = count,
            apiKey = apiKey
        )
        return dtoList.map {
            val championName = championIdNameMap[it.championId]
            ChampionMastery(
                championMasteryDto = it,
                championId = it.championId,
                championName = championName!!,
                championIconUrl = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/champion/${championName}.png",
                championLevel = it.championLevel,
                championPoints = it.championPoints
            )
        }
    }

    private suspend fun getRecentMatches(
        puuid: String,
        count: Int
    ): List<MatchDto> {
        return riotApiService.getMatchIds(
            puuid = puuid,
            count = count,
            apiKey = apiKey
        ).map {
            riotApiService.getMatch(
                matchId = it,
                apiKey = apiKey
            )
        }
    }

    private fun getChampionIconUrl(
        name: String
    ): String {
        return "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/champion/${name}.png"
    }

    private fun getItemIconUrl(
        id: Int
    ): String {
        return "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/${id}.png"
    }
}