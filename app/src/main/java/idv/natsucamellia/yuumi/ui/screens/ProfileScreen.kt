package idv.natsucamellia.yuumi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import idv.natsucamellia.yuumi.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            InfoPanel(
                modifier = Modifier.fillMaxWidth()
            )
            RankPanel()
            MasteryPanel()
        }
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
            AsyncImage(
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data("https://ddragon.leagueoflegends.com/cdn/14.1.1/img/profileicon/1385.png")
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(R.drawable.ic_launcher_background),
                contentDescription = "Profile icon",
                modifier = Modifier
                    .height(70.dp)
                    .clip(MaterialTheme.shapes.large)
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

) {

}