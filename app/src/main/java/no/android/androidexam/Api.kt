package no.android.androidexam

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import org.json.JSONArray
import org.json.JSONObject

import com.androidnetworking.interfaces.JSONObjectRequestListener
import java.io.File


class ApiClient {
    fun getByWebUrl(imageUrl: String, searchEngine: String): JSONArray {
        var result: JSONArray = JSONArray()
        AndroidNetworking.get("http://api-edu.gtl.ai/api/v1/imagesearch/" + searchEngine)
            .addQueryParameter("url", imageUrl)
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    Log.i("Response: ", response.toString())
                    result = response
                }
                override fun onError(error: ANError) {
                    Log.e("An error occured: ", error.toString())
                }
            })
        return result
    }

    fun getBySendingImage(imageLocation: File): String {
        var result = ""
        AndroidNetworking.upload("http://api-edu.gtl.ai/api/v1/imagesearch/upload")
            .addMultipartFile("image", imageLocation)
            .addMultipartParameter("image", "value")
            .setContentType("image/jpeg")
            .setPriority(Priority.HIGH)
            .build()
            .setUploadProgressListener { bytesUploaded, totalBytes ->
            }
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.i("Response", response.toString())
                    result=response.toString()
                }

                override fun onError(error: ANError) {
                }
            })
        return result
    }
}