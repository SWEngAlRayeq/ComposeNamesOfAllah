package app.names_of_allah.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.names_of_allah.data.model.NamesUiState
import app.names_of_allah.domain.usecase.GetNamesOfAllahUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NamesViewModel @Inject constructor(
    private val getNamesOfAllahUseCase: GetNamesOfAllahUseCase
) : ViewModel() {

    var uiState by mutableStateOf(NamesUiState())
        private set

    init {
        fetchNames()
    }

    fun fetchNames() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, error = null)
            try {
                val result = getNamesOfAllahUseCase()
                uiState = uiState.copy(names = result, isLoading = false)
            } catch (e: Exception) {
                uiState = uiState.copy(error = e.localizedMessage, isLoading = false)
            }
        }
    }


}