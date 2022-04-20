package no.android.androidexam

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.File

class ApiClient {
    fun getByWebUrl(imageUrl: String, searchEngine: String): ArrayList<ImageLinks> {
        var listOfLinks = ArrayList<ImageLinks>()
        AndroidNetworking.get("http://api-edu.gtl.ai/api/v1/imagesearch/" + searchEngine)
            .addQueryParameter("url", imageUrl)
            .setPriority(Priority.HIGH)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
                    for (i in 0 until response.length()) {
                        val element = response.getJSONObject(i)
                        val imagelink = ImageLinks(element.get("thumbnail_link") as String,
                            element.get("store_link") as String
                        )

                        listOfLinks.add(imagelink)
                    }
                    Log.i("Response: ", listOfLinks.toString())
                }
                override fun onError(error: ANError) {
                    Log.e("An error occured: ", error.toString())
                }
            })
        return listOfLinks
    }

    suspend fun getBySendingImage(file: File): String {

        var result = ""
        var done = true
        val request: String = withContext(Dispatchers.IO) {
            AndroidNetworking.upload("http://api-edu.gtl.ai/api/v1/imagesearch/upload")
                .addMultipartFile("image", file)
                .setPriority(Priority.HIGH)
                .build()
                .getAsString(object : StringRequestListener {
                    override fun onResponse(response: String) {
                        Log.i("Response", response)
                        result = response
                        done = false
                    }

                    override fun onError(error: ANError) {
                        Log.e("Error", error.toString())
                    }
                }).toString()
        }
        while(done){
            delay(10)
        }
        return result
    }
}                    