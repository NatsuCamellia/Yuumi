package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.DataDragonApiService
import idv.natsucamellia.yuumi.network.RiotApiService
import idv.natsucamellia.yuumi.network.SummonerDto
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

    private val championIdNameMap: Map<Long, String> by lazy {
        getChampions("14.1.1")
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
        return SummonerProfile(
            summonerDto = summonerDto,
            info = summonerInfo,
            championMasteryList = championMasteryList
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
}