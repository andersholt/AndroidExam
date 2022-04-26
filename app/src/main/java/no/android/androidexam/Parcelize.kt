package no.android.androidexam

import android.content.Context
import android.graphics.Bitmap
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.parcelize.Parcelize
import no.android.androidexam.adapters.ParentRecyclerViewAdapter


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