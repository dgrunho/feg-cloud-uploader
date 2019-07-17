package net.feghome.clouduploader.WebServices

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

class RestService {
    val service: apiService

    init {
        val client = OkHttpClient.Builder()
            //.addInterceptor(BasicAuthInterceptor(USER, PASS))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

        service = retrofit.create(apiService::class.java)
    }

    companion object {
        public val URL = "http://172.16.132.38/FEG/"
        public val USER = ""
        public val PASS = ""
    }
}

public interface apiService {

    @GET("api/values")
    fun getRoutes(): Call<MutableList<String?>>

    @GET("api/files")
    fun getUploadList(@Query("req") req: FilesCompare): Call<MutableList<String?>>

    @POST("api/files")
    fun postDeviceFiles(@Body body: FilesCompare): Call<String?>

}