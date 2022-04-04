package no.android.androidexam

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import org.json.JSONArray


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

    fun getBySendingImage(imageLocation: String): String {
        var result = ""

        Log.i("Image", imageLocation)

        AndroidNetworking.post("http://api-edu.gtl.ai/api/v1/imagesearch/upload/")
            .addBodyParameter("image", imageLocation)
            .setContentType("multipart/form-data")
            .setTag("test")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsString(object: StringRequestListener {
                override fun onResponse(response: String) {
                    Log.i("Response", response)
                    result=response
                }

                override fun onError(error: ANError) {
                    // handle error
                }
            })
        return result
    }
}