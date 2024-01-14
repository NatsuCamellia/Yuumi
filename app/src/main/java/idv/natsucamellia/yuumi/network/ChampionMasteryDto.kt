package idv.natsucamellia.yuumi.network

import kotlinx.serialization.Serializable

@Serializable
data class ChampionMasteryDto(
    val puuid: String,
    val championPointsUntilNextLevel: Long,
    val chestGranted: Boolean,
    val championId: Long,
    val lastPlayTime: Long,
    val championLevel: Long,
    val summonerId: String,
    val championPoints: Int,
    val championPointsSinceLastLevel: Long,
    val tokensEarned: Int
)