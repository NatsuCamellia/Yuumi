package idv.natsucamellia.yuumi.data

import idv.natsucamellia.yuumi.network.riotapi.ChampionMasteryDto
import idv.natsucamellia.yuumi.network.riotapi.SummonerDto

data class SummonerProfile(
    val summonerDto: SummonerDto,
    val info: SummonerInfo,
    val championMasteryList: List<ChampionMastery>,
    val matches: List<MatchSummary>
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

data class MatchSummary(
    val win: Boolean,
    val championIconUrl: String,
    val summoner1IconUrl: String,
    val summoner2IconUrl: String,
    val perk1IconUrl: String,
    val perk2IconUrl: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val item0Icon: String,
    val item1Icon: String,
    val item2Icon: String,
    val item3Icon: String,
    val item4Icon: String,
    val item5Icon: String,
    val item6Icon: String,
    val gameDuration: Long,
    val gameEndTimestamp: Long,
    val gameMode: String,
)