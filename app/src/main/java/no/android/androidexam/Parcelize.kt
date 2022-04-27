package no.android.androidexam

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class ImageLinks(val storeLink: String, val thumbNailLink: String):Parcelable
@Parcelize
class ResData(
    val originalImage: OriginalImage, val searchEngine: String? = "",
    val links: ArrayList<ImageLinks> = ArrayList(),
):Parcelable

@Parcelize
class OriginalImage(
    val bitmap: Bitmap,
    val link: String? = ""
):Parcelable

@Parcelize
class ResultImages(
    val foreignKey: Int,
    val bitmaps: ArrayList<ResultImage> = ArrayList(),
):Parcelable

@Parcelize
class ResultImage(
    val id: Int,
    val bitmap: Bitmap,
):Parcelable

class ParentModel(private var imageBitmap: Bitmap, private var id: Int) {
    fun parentBitmap(): Bitmap {
        return imageBitmap
    }
    fun parentId(): Int {
        return id
    }
}