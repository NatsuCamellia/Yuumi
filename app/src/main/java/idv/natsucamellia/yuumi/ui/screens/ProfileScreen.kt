package idv.natsucamellia.yuumi.ui.screens

import android.text.format.DateUtils
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import idv.natsucamellia.yuumi.data.ChampionMastery
import idv.natsucamellia.yuumi.data.MatchSummary
import idv.natsucamellia.yuumi.data.SummonerInfo
import idv.natsucamellia.yuumi.ui.SummonerUiState
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ProfileScreen(
    summonerUiState: SummonerUiState,
    modifier: Modifier = Modifier
) {
    when (summonerUiState) {
        is SummonerUiState.Loading -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
            ) {
                Text(
                    text = "Loading..."
                )
            }
        }
        is SummonerUiState.NotFound -> {
            Box(
                contentAlignment = Alignment.Center,
                modifier = modifier
            ) {
                Text(
                    text = "Summoner not found."
                )
            }
        }
        is SummonerUiState.Success -> {
            val profile = summonerUiState.summonerProfile
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = modifier
            ) {
                item {
                    InfoPanel(
                        info = profile.info,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                item {
                    MasteryPanel(
                        championMasteryList = profile.championMasteryList,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                items(
                    profile.matches
                ){
                    MatchItem(
                        matchSummary = it,
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun InfoPanel(
    info: SummonerInfo,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            SquareAssets(
                url = info.profileIconUrl,
                contentDescription = "Profile icon",
                modifier = Modifier
                    .height(70.dp)
                    .aspectRatio(1f, true)
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = info.name,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Level ${info.level}"
                )
            }
        }
    }
}

@Composable
fun MasteryPanel(
    championMasteryList: List<ChampionMastery>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        items(
            championMasteryList
        ) {
            MasteryItem(
                championMastery = it,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun MasteryItem(
    championMastery: ChampionMastery,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        SquareAssets(
            url = championMastery.championIconUrl,
            contentDescription = "Champion icon",
            modifier = Modifier
                .height(50.dp)
                .aspectRatio(1f, true)
        )
        Text(
            text = "Mastery ${championMastery.championLevel}"
        )
        Text(
            text = "${championMastery.championPoints}"
        )
    }
}

@Composable
fun MatchItem(
    matchSummary: MatchSummary,
    modifier: Modifier = Modifier
) {
    Row(
       modifier = modifier.height(100.dp)
    ) {
        val kda: Double = (matchSummary.kills + matchSummary.assists).toDouble() / matchSummary.deaths
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(if (matchSummary.win) Color.Green else Color.Gray)
                .fillMaxHeight()
                .width(30.dp)
                .padding(8.dp)
        ) {
            Text(
                text = if (matchSummary.win) "W" else "L",
                color = if (matchSummary.win) Color.Black else Color.White
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxHeight()
            ) {
                // Champion, spells, KDA
                Row (
                    modifier = Modifier.height(54.dp)
                ) {
                    SquareAssets(
                        url = matchSummary.championIconUrl,
                        contentDescription = "Champion icon",
                        modifier = Modifier
                            .height(54.dp)
                            .aspectRatio(1f, true)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        SummonerSpellColumn(
                            summoner1IconUrl = matchSummary.summoner1IconUrl,
                            summoner2IconUrl = matchSummary.summoner2IconUrl
                        )
                        RuneColumn(
                            perk1IconUrl = matchSummary.perk1IconUrl,
                            perk2IconUrl = matchSummary.perk2IconUrl
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = "${matchSummary.kills} / ${matchSummary.deaths} / ${matchSummary.assists}",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "KDA ${"%.1f".format(kda)}",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                BuildRow(
                    matchSummary = matchSummary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = matchSummary.gameMode,
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = DateUtils.formatElapsedTime(matchSummary.gameDuration),
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = matchSummary.gameEndTimestamp.toTimeDateString(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun SummonerSpellColumn(
    summoner1IconUrl: String,
    summoner2IconUrl: String,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        SquareAssets(
            url = summoner1IconUrl,
            contentDescription = "Summoner spell 1",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
        SquareAssets(
            url = summoner2IconUrl,
            contentDescription = "Summoner spell 2",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
    }
}

@Composable
fun RuneColumn(
    perk1IconUrl: String,
    perk2IconUrl: String
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        SquareAssets(
            url = perk1IconUrl,
            contentDescription = "Rune 1",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
                .background(Color.Black)
        )
        SquareAssets(
            url = perk2IconUrl,
            contentDescription = "Rune 2",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
    }
}

@Composable
fun BuildRow(
    matchSummary: MatchSummary,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
    ) {
        SquareAssets(
            url = matchSummary.item0Icon,
            contentDescription = "Item name",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
        SquareAssets(
            url = matchSummary.item1Icon,
            contentDescription = "Item name",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
        SquareAssets(
            url = matchSummary.item2Icon,
            contentDescription = "Item name",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
        SquareAssets(
            url = matchSummary.item3Icon,
            contentDescription = "Item name",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
        SquareAssets(
            url = matchSummary.item4Icon,
            contentDescription = "Item name",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
        SquareAssets(
            url = matchSummary.item5Icon,
            contentDescription = "Item name",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
        SquareAssets(
            url = matchSummary.item6Icon,
            contentDescription = "Item name",
            modifier = Modifier
                .height(25.dp)
                .aspectRatio(1f, true)
        )
    }
}

@Composable
fun SquareAssets(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        error = ColorPainter(Color.Gray),
        placeholder = ColorPainter(Color.Gray),
        contentDescription = contentDescription,
        modifier = modifier
    )
}

fun Long.toTimeDateString(): String {
    val dateTime = Date(this)
    val format = SimpleDateFormat("yyyy/MM/dd", java.util.Locale.CHINESE)
    return format.format(dateTime)
}