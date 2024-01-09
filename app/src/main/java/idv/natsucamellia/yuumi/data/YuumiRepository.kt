package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.RiotApiService
import idv.natsucamellia.yuumi.network.SummonerDto

interface YuumiRepository {
    suspend fun getSummonerDtoByName(summonerName: String): SummonerDto
}

class NetworkYuumiRepository(
    private val apiKey: String,
    private val riotApiService: RiotApiService
): YuumiRepository {
    override suspend fun getSummonerDtoByName(summonerName: String): SummonerDto {
        return riotApiService.getSummonerDtoByName(summonerName, apiKey)
    }
}