package no.android.androidexam

import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.StringRequestListener
import org.json.JSONArray
import java.io.File
import android.provider.MediaStore

import android.app.Activity
import android.database.Cursor
import android.net.Uri
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.lang.Thread.sleep
import java.nio.channels.FileLockInterruptionException
import kotlin.coroutines.CoroutineContext

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

    fun getBySendingImage(file: File): String  = runBlocking{
        var result: String = ""

        val request = AndroidNetworking.upload("http://api-edu.gtl.ai/api/v1/imagesearch/upload")
            .addMultipartFile("image", file)
            .setPriority(Priority.HIGH)
            .build()
            .getAsString(object: StringRequestListener {
                override fun onResponse(response: String) {
                    Log.i("Response", response)
                    result = response
                }
                override fun onError(error: ANError) {
                    Log.e("Error", error.toString())
                }
            })
        delay(8000)
        result
    }
}