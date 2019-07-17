package net.feghome.clouduploader.WebServices

import android.content.Context
import android.widget.Toast
import net.feghome.clouduploader.Utils.toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceCalls(currentContext: Context) {
    var context: Context = currentContext

    fun getUploadList(files: FilesCompare) {
        var restService = RestService()
        val call = restService.service.postDeviceFiles(files)
        call.enqueue(object : Callback<String?> {
            override fun onResponse(call: Call<String?>, response: Response<String?>) {
                if (response.code() == 200) {
                    var response = response.body()
                    var a: String = ""
                } else {
                    context.toast("Erro: " + response.raw().toString())
                }
            }

            override fun onFailure(call: Call<String?>, error: Throwable) {
                context.toast("Erro: " +  error.message.toString())
            }
        })
    }
}