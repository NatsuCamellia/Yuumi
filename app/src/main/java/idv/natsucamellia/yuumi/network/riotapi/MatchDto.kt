package idv.natsucamellia.yuumi.network.riotapi

import kotlinx.serialization.Serializable

@Serializable
data class MatchDto(
    val metadata: MetadataDto,
    val info: InfoDto
)

@Serializable
data class MetadataDto(
    val dataVersion: String,
    val matchId: String,
    val participants: List<String>
)

@Serializable
data class InfoDto(
    val gameCreation: Long,
    val gameDuration: Long,
    val gameEndTimestamp: Long,
    val gameId: Long,
    val gameMode: String,
    val gameName: String,
    val gameStartTimestamp: Long,
    val gameType: String,
    val gameVersion: String,
    val mapId: Int,
    val participants: List<ParticipantDto>,
    val platformId: String,
    val queueId: Int,
    val teams: List<TeamDto>,
    val tournamentCode: String
)

@Serializable
data class ParticipantDto(
    val assists: Int,
    val championId: Int,
    val championName: String,
    val deaths: Int,
    val item0: Int,
    val item1: Int,
    val item2: Int,
    val item3: Int,
    val item4: Int,
    val item5: Int,
    val item6: Int,
    val kills: Int,
    val perks: PerksDto,
    val puuid: String,
    val riotIdGameName: String,
    val riotIdTagline: String,
    val summoner1Id: Int,
    val summoner2Id: Int,
    val summonerLevel: Int,
    val summonerName: String,
    val teamId: Int,
    val timePlayed: Int,
    val win: Boolean
)

@Serializable
data class PerksDto(
    val statPerks: PerkStatsDto,
    val styles: List<PerkStyleDto>
)

@Serializable
data class PerkStatsDto(
    val defense: Int,
    val flex: Int,
    val offense: Int
)

@Serializable
data class PerkStyleDto(
    val description: String,
    val selections: List<PerkStyleSelectionDto>,
    val style: Int
)

@Serializable
data class PerkStyleSelectionDto(
    val perk: Int,
    val var1: Int,
    val var2: Int,
    val var3: Int
)

@Serializable
data class TeamDto(
    val bans: List<BanDto>,
    val objectives: ObjectivesDto,
    val teamId: Int,
    val win: Boolean
)

@Serializable
data class BanDto(
    val championId: Int,
    val pickTurn: Int
)

@Serializable
data class ObjectivesDto(
    val baron: ObjectiveDto,
    val champion: ObjectiveDto,
    val dragon: ObjectiveDto,
    val inhibitor: ObjectiveDto,
    val riftHerald: ObjectiveDto,
    val tower: ObjectiveDto
)

@Serializable
data class ObjectiveDto(
    val first: Boolean,
    val kills: Int
)

fun MatchDto.getParticipant(
    puuid: String
): ParticipantDto? {
    for (participant in this.info.participants) {
        if (participant.puuid == puuid) {
            return participant
        }
    }
    return null
}