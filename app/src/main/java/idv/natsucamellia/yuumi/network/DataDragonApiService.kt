package idv.natsucamellia.yuumi.network

import retrofit2.http.GET
import retrofit2.http.Path

interface DataDragonApiService {
    @GET("{version}/data/en_US/champion.json")
    suspend fun getChampions(
        @Path("version")
        version: String
    ): Champions
}