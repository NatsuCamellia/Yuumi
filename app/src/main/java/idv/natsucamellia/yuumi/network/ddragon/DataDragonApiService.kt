package idv.natsucamellia.yuumi.network.ddragon

import retrofit2.http.GET
import retrofit2.http.Path

interface DataDragonApiService {
    @GET("cdn/{version}/data/en_US/champion.json")
    suspend fun getChampions(
        @Path("version")
        version: String
    ): Champions

    @GET("api/versions.json")
    suspend fun getVersions(): List<String>

    @GET("cdn/{version}/data/en_US/summoner.json")
    suspend fun getSummonerSpells(
        @Path("version")
        version: String
    ): SummonerSpells
}