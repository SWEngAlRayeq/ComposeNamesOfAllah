package app.names_of_allah.data.model

data class NamesUiState(
    val names: List<NameOfAllah> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)