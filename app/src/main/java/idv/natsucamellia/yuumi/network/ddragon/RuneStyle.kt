package idv.natsucamellia.yuumi.network.ddragon

import kotlinx.serialization.Serializable

@Serializable
data class RuneStyle(
    val id: Int,
    val key: String,
    val icon: String,
    val name: String,
    val slots: List<RuneSelection>
)

@Serializable
data class RuneSelection(
    val runes: List<Rune>
)

@Serializable
data class Rune(
    val id: Int,
    val key: String,
    val icon: String,
    val name: String,
    val shortDesc: String,
    val longDesc: String
)