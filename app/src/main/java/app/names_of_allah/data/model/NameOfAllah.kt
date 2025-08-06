package app.names_of_allah.data.model

data class NameOfAllah(
    val name: String,
    val transliteration: String,
    val en: EnMeaning
)

data class EnMeaning(
    val meaning: String
)

data class NamesOfAllahResponse(
    val data: List<NameOfAllah>
)