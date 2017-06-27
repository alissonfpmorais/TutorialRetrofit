package br.com.eintritt.tutorialretrofit.models

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by STwis on 26/06/2017.
 */
interface UdacityService {
    companion object {
        val BASE_URL = "https://www.udacity.com/public-api/v0/"
    }

    @GET("courses")
    fun listCatalog(): Call<UdacityCatalog>
}