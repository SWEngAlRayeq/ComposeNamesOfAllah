package app.names_of_allah.data.api

import app.names_of_allah.data.model.NamesOfAllahResponse
import retrofit2.http.GET

interface AllahNamesApiService {

    @GET("asmaAlHusna")
    suspend fun getNamesOfAllah(): NamesOfAllahResponse

}