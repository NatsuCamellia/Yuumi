package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.riotapi.MatchDto
import idv.natsucamellia.yuumi.network.riotapi.RiotApiService
import idv.natsucamellia.yuumi.network.riotapi.SummonerDto
import idv.natsucamellia.yuumi.network.riotapi.getParticipant
import retrofit2.HttpException

interface YuumiRepository {
    suspend fun getSummonerDtoByName(summonerName: String): SummonerDto?
    suspend fun getSummonerProfile(summonerDto: SummonerDto): SummonerProfile
}

class NetworkYuumiRepository(
    private val apiKey: String,
    private val riotApiService: RiotApiService,
    private val dataDragonRepository: DataDragonRepository
): YuumiRepository {
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
            profileIconUrl = dataDragonRepository.getProfileIconUrl(summonerDto.profileIconId)
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
                championIconUrl = dataDragonRepository.getChampionIconUrl(participant.championId),
                summoner1IconUrl = dataDragonRepository.getSummonerSpellIconUrl(participant.summoner1Id),
                summoner2IconUrl = dataDragonRepository.getSummonerSpellIconUrl(participant.summoner2Id),
                perk1IconUrl = dataDragonRepository.getRuneIconUrl(participant.perks.styles[0].selections[0].perk),
                perk2IconUrl = dataDragonRepository.getRuneStyleIconUrl(participant.perks.styles[1].style),
                kills = participant.kills,
                deaths = participant.deaths,
                assists = participant.assists,
                item0Icon = dataDragonRepository.getItemIconUrl(participant.item0),
                item1Icon = dataDragonRepository.getItemIconUrl(participant.item1),
                item2Icon = dataDragonRepository.getItemIconUrl(participant.item2),
                item3Icon = dataDragonRepository.getItemIconUrl(participant.item3),
                item4Icon = dataDragonRepository.getItemIconUrl(participant.item4),
                item5Icon = dataDragonRepository.getItemIconUrl(participant.item5),
                item6Icon = dataDragonRepository.getItemIconUrl(participant.item6),
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
            ChampionMastery(
                championMasteryDto = it,
                championId = it.championId,
                championName = dataDragonRepository.getChampionName(it.championId.toInt()),
                championIconUrl = dataDragonRepository.getChampionIconUrl(it.championId.toInt()),
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
}