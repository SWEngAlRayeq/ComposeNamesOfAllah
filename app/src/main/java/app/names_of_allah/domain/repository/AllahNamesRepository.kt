package app.names_of_allah.domain.repository

import app.names_of_allah.data.model.NameOfAllah

interface AllahNamesRepository {
    suspend fun getNames(): List<NameOfAllah>
}