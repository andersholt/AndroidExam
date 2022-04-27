package no.android.androidexam

import android.app.Activity
import android.content.Context
import android.net.NetworkInfo

import android.net.ConnectivityManager



fun internetIsConnected(activity : Activity): Boolean {
    var connected = false
    val connectivityManager = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
    connected =
        connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)!!.state == NetworkInfo.State.CONNECTED ||
            connectivityManager!!.getNetworkInfo(ConnectivityManager.TYPE_WIFI)!!.state == NetworkInfo.State.CONNECTED
    return connected
}
