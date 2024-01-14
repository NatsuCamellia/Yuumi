package idv.natsucamellia.yuumi.network

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
}