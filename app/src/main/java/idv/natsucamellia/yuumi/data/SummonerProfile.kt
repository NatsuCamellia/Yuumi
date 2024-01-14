package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.ChampionMasteryDto
import idv.natsucamellia.yuumi.network.SummonerDto

data class SummonerProfile(
    val summonerDto: SummonerDto,
    val info: SummonerInfo,
    val championMasteryList: List<ChampionMastery>
)

data class SummonerInfo(
    val name: String,
    val level: Long,
    val profileIconUrl: String
)

data class ChampionMastery(
    val championMasteryDto: ChampionMasteryDto,
    val championId: Long,
    val championName: String,
    val championIconUrl: String,
    val championLevel: Long,
    val championPoints: Int
)