package idv.natsucamellia.yuumi

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import idv.natsucamellia.yuumi.data.NetworkYuumiRepository
import idv.natsucamellia.yuumi.data.YuumiRepository
import idv.natsucamellia.yuumi.network.DataDragonApiService
import idv.natsucamellia.yuumi.network.RiotApiService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val yuumiRepository: YuumiRepository
}

class DefaultAppContainer: AppContainer {

    private val apiKey = BuildConfig.RIOT_API_KEY
    private val dataDragonBaseUrl = "https://ddragon.leagueoflegends.com"
    private val baseUrl = "https://tw2.api.riotgames.com"
    private val json = Json {
        ignoreUnknownKeys = true
    }
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: RiotApiService by lazy {
        retrofit.create(RiotApiService::class.java)
    }

    private val dataDragonRetrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(dataDragonBaseUrl)
        .build()

    private val dataDragonApiService: DataDragonApiService by lazy {
        dataDragonRetrofit.create(DataDragonApiService::class.java)
    }

    override val yuumiRepository: YuumiRepository by lazy {
        NetworkYuumiRepository(
            apiKey = apiKey,
            riotApiService =  retrofitService,
            dataDragonApiService = dataDragonApiService
        )
    }
}