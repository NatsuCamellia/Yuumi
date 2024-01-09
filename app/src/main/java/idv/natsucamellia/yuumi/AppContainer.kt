package idv.natsucamellia.yuumi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import idv.natsucamellia.yuumi.data.NetworkYuumiRepository
import idv.natsucamellia.yuumi.data.YuumiRepository
import idv.natsucamellia.yuumi.network.RiotApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val yuumiRepository: YuumiRepository
}

class DefaultAppContainer: AppContainer {

    private val apiKey = "RGAPI-060b87e5-e508-4a92-a397-57cbd23a07ce"
    private val baseUrl = "https://tw2.api.riotgames.com"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: RiotApiService by lazy {
        retrofit.create(RiotApiService::class.java)
    }

    override val yuumiRepository: YuumiRepository by lazy {
        NetworkYuumiRepository(apiKey, retrofitService)
    }
}