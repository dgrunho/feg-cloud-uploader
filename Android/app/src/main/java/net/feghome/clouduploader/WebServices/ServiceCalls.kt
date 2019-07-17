package net.feghome.clouduploader.WebServices

import android.content.Context
import android.widget.Toast
import net.feghome.clouduploader.Utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceCalls(context: Context) {
    var ctx: Context = context

    fun getValues() {
        var restService = RestService()
        val call = restService.service.getRoutes()
        call.enqueue(object : Callback<MutableList<String?>> {
            override fun onResponse(call: Call<MutableList<String?>>, response: Response<MutableList<String?>>) {
                if (response.code() == 200) {
                    var response = response.body()
                    var a: String = ""
                } else {
                    ctx.toast("Erro: " + response.raw().toString())
                }
            }

            override fun onFailure(call: Call<MutableList<String?>>, error: Throwable) {
                ctx.toast("Erro: " +  error.message.toString())
            }
        })
    }
}