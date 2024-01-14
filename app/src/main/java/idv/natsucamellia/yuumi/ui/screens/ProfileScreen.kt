package idv.natsucamellia.yuumi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import idv.natsucamellia.yuumi.R
import idv.natsucamellia.yuumi.data.ChampionMastery
import idv.natsucamellia.yuumi.data.SummonerInfo
import idv.natsucamellia.yuumi.ui.SummonerUiState

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
                item{
                    MatchItem(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                    )
                }
                item{
                    MatchItem(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                    )
                }
                item{
                    MatchItem(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                    )
                }
                item{
                    MatchItem(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                    )
                }
                item{
                    MatchItem(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                    )
                }
                item{
                    MatchItem(
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
                height = 70.dp
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
fun RankPanel(

) {

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
            height = 50.dp
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
    modifier: Modifier = Modifier
) {
    Row(
       modifier = modifier.height(100.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Color.Red)
                .fillMaxHeight()
                .padding(8.dp)
        ) {
            Text("W")
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
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/champion/Aatrox.png",
                        contentDescription = "Champion icon",
                        height = 54.dp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        // Summoner spells
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            SquareAssets(
                                url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/spell/SummonerHeal.png",
                                contentDescription = "Summoner spell 1",
                                height = 25.dp
                            )
                            SquareAssets(
                                url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/spell/SummonerFlash.png",
                                contentDescription = "Summoner spell 2",
                                height = 25.dp
                            )
                        }
                        // Runes
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            SquareAssets(
                                url = "https://ddragon.leagueoflegends.com/cdn/img/perk-images/Styles/Precision/Conqueror/Conqueror.png",
                                contentDescription = "Summoner spell 1",
                                height = 25.dp
                            )
                            SquareAssets(
                                url = "https://ddragon.leagueoflegends.com/cdn/img/perk-images/Styles/7200_Domination.png",
                                contentDescription = "Summoner spell 2",
                                height = 25.dp
                            )
                        }

                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(
                            text = "4 / 2 / 6",
                            style = MaterialTheme.typography.labelLarge
                        )
                        Text(
                            text = "KDA 5.0",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
                // Builds
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    SquareAssets(
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                        contentDescription = "Item name",
                        height = 25.dp
                    )
                    SquareAssets(
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                        contentDescription = "Item name",
                        height = 25.dp
                    )
                    SquareAssets(
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                        contentDescription = "Item name",
                        height = 25.dp
                    )
                    SquareAssets(
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                        contentDescription = "Item name",
                        height = 25.dp
                    )
                    SquareAssets(
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                        contentDescription = "Item name",
                        height = 25.dp
                    )
                    SquareAssets(
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                        contentDescription = "Item name",
                        height = 25.dp
                    )
                    SquareAssets(
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                        contentDescription = "Item name",
                        height = 25.dp
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "Summoner's Rift",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "19:53",
                    style = MaterialTheme.typography.labelLarge
                )
                Text(
                    text = "2024/01/11",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
fun SquareAssets(
    url: String,
    contentDescription: String,
    height: Dp,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = contentDescription,
        modifier = modifier
            .height(height)
    )
}