package idv.natsucamellia.yuumi.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import idv.natsucamellia.yuumi.YuumiApplication
import idv.natsucamellia.yuumi.data.SummonerProfile
import idv.natsucamellia.yuumi.data.YuumiRepository
import kotlinx.coroutines.launch

class SummonerViewModel(
    private val yuumiRepository: YuumiRepository
) : ViewModel() {

    var summonerUiState: SummonerUiState by mutableStateOf(SummonerUiState.Loading)
        private set

    fun searchBySummonerName(summonerName: String) {
        viewModelScope.launch {
            summonerUiState = SummonerUiState.Loading
            val summonerDto = yuumiRepository.getSummonerDtoByName(summonerName)
            summonerUiState = when (summonerDto) {
                null -> SummonerUiState.NotFound
                else -> SummonerUiState.Success(
                    yuumiRepository.getSummonerProfile(summonerDto)
                )
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as YuumiApplication)
                val yuumiRepository = application.container.yuumiRepository
                SummonerViewModel(yuumiRepository)
            }
        }
    }
}

sealed interface SummonerUiState {
    data class Success(val summonerProfile: SummonerProfile): SummonerUiState
    object NotFound: SummonerUiState
    object Loading: SummonerUiState
}