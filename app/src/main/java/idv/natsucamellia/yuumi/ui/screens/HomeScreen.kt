package idv.natsucamellia.yuumi.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import idv.natsucamellia.yuumi.data.SummonerUiState
import idv.natsucamellia.yuumi.network.SummonerDto

@Composable
fun HomeScreen(
    summonerUiState: SummonerUiState,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var summonerName by remember { mutableStateOf("") }

    Column(
        modifier = modifier
    ) {
        SearchBar(
            title = "Summoner Name",
            value = summonerName,
            onValueChange = { summonerName = it },
            onSearch = {
                onSearch(summonerName)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        SummonerInfo(summonerUiState.summonerDto)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    title: String,
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        label = { Text(text = title) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search by summoner name"
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch(value)
                focusManager.clearFocus()
            }
        ),
        modifier = modifier
    )
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