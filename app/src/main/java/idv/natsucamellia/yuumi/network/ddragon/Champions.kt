package idv.natsucamellia.yuumi.network.ddragon

import kotlinx.serialization.Serializable

@Serializable
data class Champions(
    val type: String,
    val format: String,
    val version: String,
    val data: Map<String, ChampionSummary>
)

@Serializable
data class ChampionSummary(
    val key: String,
    val name: String
)