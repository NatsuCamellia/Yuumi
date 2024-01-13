package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.SummonerDto

data class SummonerProfile(
    val summonerDto: SummonerDto,
    val info: SummonerInfo
)

data class SummonerInfo(
    val name: String,
    val level: Long,
    val profileIconUrl: String
)