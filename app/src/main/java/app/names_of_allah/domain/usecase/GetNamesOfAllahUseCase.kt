package app.names_of_allah.domain.usecase

import app.names_of_allah.data.model.NameOfAllah
import app.names_of_allah.domain.repository.AllahNamesRepository
import javax.inject.Inject

class GetNamesOfAllahUseCase @Inject constructor(
    private val repository: AllahNamesRepository
) {
    suspend operator fun invoke(): List<NameOfAllah> {
        return repository.getNames()
    }
}