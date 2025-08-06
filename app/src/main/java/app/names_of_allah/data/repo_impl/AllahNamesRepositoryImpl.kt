package app.names_of_allah.data.repo_impl

import app.names_of_allah.data.api.AllahNamesApiService
import app.names_of_allah.data.model.NameOfAllah
import app.names_of_allah.domain.repository.AllahNamesRepository
import javax.inject.Inject

class AllahNamesRepositoryImpl @Inject constructor(
    private val apiService: AllahNamesApiService
): AllahNamesRepository {
    override suspend fun getNames(): List<NameOfAllah> {
        return apiService.getNamesOfAllah().data
    }
}