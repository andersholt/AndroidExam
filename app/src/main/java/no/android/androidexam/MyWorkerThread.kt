package no.android.androidexam

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView

class MyWorkerThread(context: Context, layout: Int) {
    val loadingDialog = context.let {
        MaterialDialog(it).noAutoDismiss().customView(layout)
    }
    @WorkerThread
    fun start(context: Context) {
            ContextCompat.getMainExecutor(context).execute {
                loadingDialog.show()
            }

    }
    fun stop(context: Context) {
        context.let {
            ContextCompat.getMainExecutor(it).execute {
                loadingDialog.dismiss()
            }
        }
    }
}