package idv.natsucamellia.yuumi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import idv.natsucamellia.yuumi.data.SummonerUiState
import idv.natsucamellia.yuumi.network.SummonerDto

@Composable
fun HomeScreen(
    summonerUiState: SummonerUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        SummonerInfo(summonerUiState.summonerDto)
    }
}

@Composable
fun SummonerInfo(
    summonerDto: SummonerDto,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "召喚師名稱：${summonerDto.name}"
        )
        Text(
            text = "召喚師等級：${summonerDto.summonerLevel}"
        )
    }
}