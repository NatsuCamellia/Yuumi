package idv.natsucamellia.yuumi.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RiotApiService {
    @GET("lol/summoner/v4/summoners/by-name/{summonerName}")
    suspend fun getSummonerDtoByName(
        @Path("summonerName")
        summonerName: String,
        @Query("api_key")
        apiKey: String
    ): SummonerDto

    @GET("/lol/champion-mastery/v4/champion-masteries/by-puuid/{encryptedPUUID}/top")
    suspend fun getChampionMasteryTopByPuuid(
        @Path("encryptedPUUID")
        puuid: String,
        @Query("count")
        count: Int,
        @Query("api_key")
        apiKey: String
    ): List<ChampionMasteryDto>
}