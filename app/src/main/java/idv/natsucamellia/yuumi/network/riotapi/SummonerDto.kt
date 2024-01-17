package idv.natsucamellia.yuumi.network.riotapi

import kotlinx.serialization.Serializable

@Serializable
data class SummonerDto(
    val accountId: String = "",
    val profileIconId: Int = 0,
    val revisionDate: Long = 0L,
    val name: String = "",
    val id: String = "",
    val puuid: String = "",
    val summonerLevel: Long = 0L
)
