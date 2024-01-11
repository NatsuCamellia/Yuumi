package idv.natsucamellia.yuumi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import idv.natsucamellia.yuumi.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.verticalScroll(rememberScrollState())
    ) {
        InfoPanel(
            modifier = Modifier.fillMaxWidth()
        )
        RankPanel(

        )
        MasteryPanel(
            modifier = Modifier.fillMaxWidth()
        )
        MatchPanel(
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun InfoPanel(
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
                url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/profileicon/1385.png",
                contentDescription = "Profile icon",
                height = 70.dp
            )
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "NatsuCamellia #4890",
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Level 357"
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
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.secondaryContainer)
    ) {
        item {
            MasteryItem(
                modifier = Modifier.padding(16.dp)
            )
            MasteryItem(
                modifier = Modifier.padding(16.dp)
            )
            MasteryItem(
                modifier = Modifier.padding(16.dp)
            )
            MasteryItem(
                modifier = Modifier.padding(16.dp)
            )
            MasteryItem(
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun MasteryItem(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        SquareAssets(
            url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/champion/Aatrox.png",
            contentDescription = "Champion icon",
            height = 50.dp
        )
        Text(
            text = "Mastery 7"
        )
        Text(
            text = "473924"
        )
    }
}


@Composable
fun MatchPanel(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        MatchItem()
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
                .padding(4.dp)
                .fillMaxHeight()
        ) {
            Text("W")
            Text(
                text = "19:53",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(8.dp)
        ) {
            Column {
                Row {
                    SquareAssets(
                        url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/champion/Aatrox.png",
                        contentDescription = "Champion icon",
                        height = 50.dp
                    )
                }
                // Builds
                LazyRow {
                    item {
                        SquareAssets(
                            url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                            contentDescription = "Item name",
                            height = 30.dp
                        )
                        SquareAssets(
                            url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                            contentDescription = "Item name",
                            height = 30.dp
                        )
                        SquareAssets(
                            url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                            contentDescription = "Item name",
                            height = 30.dp
                        )
                        SquareAssets(
                            url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                            contentDescription = "Item name",
                            height = 30.dp
                        )
                        SquareAssets(
                            url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                            contentDescription = "Item name",
                            height = 30.dp
                        )
                        SquareAssets(
                            url = "https://ddragon.leagueoflegends.com/cdn/14.1.1/img/item/1001.png",
                            contentDescription = "Item name",
                            height = 30.dp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SquareAssets(
    url: String,
    contentDescription: String,
    height: Dp
) {
    AsyncImage(
        model = ImageRequest
            .Builder(context = LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        placeholder = painterResource(R.drawable.ic_launcher_background),
        contentDescription = contentDescription,
        modifier = Modifier
            .height(height)
            .clip(MaterialTheme.shapes.large)
    )
}