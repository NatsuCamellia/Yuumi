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
import idv.natsucamellia.yuumi.data.SummonerUiState
import idv.natsucamellia.yuumi.data.YuumiRepository
import kotlinx.coroutines.launch
import java.io.IOException

class SummonerViewModel(
    private val yuumiRepository: YuumiRepository
) : ViewModel() {

    var summonerUiState: SummonerUiState by mutableStateOf(SummonerUiState())
        private set

    fun searchBySummonerName(summonerName: String) {
        viewModelScope.launch {
            summonerUiState = try {
                SummonerUiState(yuumiRepository.getSummonerDtoByName(summonerName))
            } catch (e: IOException) {
                // TODO: return SummonerUiState.Error
                SummonerUiState()
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