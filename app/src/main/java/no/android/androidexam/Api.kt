package no.android.androidexam

import android.content.Context
import android.util.JsonReader
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.serialization.internal.throwMissingFieldException
import org.json.JSONArray

class ApiClient (){
    fun getByWebUrl(imageUrl: String, searchEngine: String): JSONArray {
        var result: JSONArray = JSONArray();
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


    fun getBySendingImage(imageLocation: String): JSONArray {
        var result: JSONArray = JSONArray();

        AndroidNetworking.post("http://api-edu.gtl.ai/api/v1/imagesearch/upload")
            .addBodyParameter("image", imageLocation)
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
}