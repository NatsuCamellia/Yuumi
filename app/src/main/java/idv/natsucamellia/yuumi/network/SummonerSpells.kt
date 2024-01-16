package idv.natsucamellia.yuumi.network

import kotlinx.serialization.Serializable

@Serializable
data class SummonerSpells(
    val type: String,
    val version: String,
    val data: Map<String, SummonerSpell>
)

@Serializable
data class SummonerSpell(
    val id: String,
    val name: String,
    val key: String,
    val image: SummonerSpellImage
)

@Serializable
data class SummonerSpellImage(
    val full: String,
    val sprite: String,
    val group: String,
    val x: Int,
    val y: Int,
    val w: Int,
    val h: Int
)

fun SummonerSpellImage.getFullUrl(
    version: String
): String {
    return "https://ddragon.leagueoflegends.com/cdn/${version}/img/spell/${full}"
}