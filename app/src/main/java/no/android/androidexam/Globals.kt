package no.android.androidexam

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore

fun UriToBitmap(context: Context, id: Int?, uri: String?): Bitmap {
    val image: Bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(uri))
    return image
}

fun getBitmap(context: Context, id: Int?, uri: String?, decoder: (Context, Int?, String?) -> Bitmap): Bitmap {
    return decoder(context, id, uri)
}


fun internetIsConnected(): Boolean {
    try {
        var command = "ping -c 1 google.com";
        return (Runtime.getRuntime().exec(command).waitFor() == 0);
    } catch (e: Exception) {
        return false;
    }
}