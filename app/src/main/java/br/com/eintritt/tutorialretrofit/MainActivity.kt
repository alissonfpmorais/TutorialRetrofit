package br.com.eintritt.tutorialretrofit

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import br.com.eintritt.tutorialretrofit.models.UdacityCatalog
import br.com.eintritt.tutorialretrofit.models.UdacityService
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val TAG = "MAIN_APK_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        val retrofit = Retrofit.Builder()
                .baseUrl(UdacityService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val udacityService = retrofit.create(UdacityService::class.java)
        val requestUdacityCatalog = udacityService.listCatalog()
        requestUdacityCatalog.enqueue(object : Callback<UdacityCatalog> {
            override fun onFailure(call: Call<UdacityCatalog>?, t: Throwable?) {
                Log.d(TAG, "Erro: ${t?.message}")
            }

            override fun onResponse(call: Call<UdacityCatalog>?, response: Response<UdacityCatalog>?) {
                response?.let {
                    if (response.isSuccessful) {
                        val udacityCatalog = response.body()

                        udacityCatalog?.let {
                            it.courses.forEach {
                                Log.d(TAG, "${it.title}: ${it.subtitle}")
                                it.instructors.forEach {
                                    Log.d(TAG, "Instructor: ${it.name}, Life: ${it.bio}")
                                }

                                Log.d(TAG, "----------------------------------------------")
                            }
                        }
                    } else {
                        Log.d(TAG, "Erro: ${response.code()}")
                    }
                }
            }
        })
    }

    inner class MainActivityUI: AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>): View = ui.apply {
            linearLayout {
                button {
                    onClick { Toast.makeText(applicationContext, "Retrofit", Toast.LENGTH_SHORT).show() }
                }
            }
        }.view
    }
}
