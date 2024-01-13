package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.RiotApiService
import idv.natsucamellia.yuumi.network.SummonerDto
import retrofit2.HttpException

interface YuumiRepository {
    suspend fun getSummonerDtoByName(summonerName: String): SummonerDto?
    suspend fun getSummonerProfile(summonerDto: SummonerDto): SummonerProfile
}

class NetworkYuumiRepository(
    private val apiKey: String,
    private val riotApiService: RiotApiService
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
            profileIconUrl = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/profileicon/${summonerDto.profileIconId}.png"
        )
        return SummonerProfile(
            summonerDto = summonerDto,
            info = summonerInfo
        )
    }
}