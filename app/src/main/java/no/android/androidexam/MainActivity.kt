package no.android.androidexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import okhttp3.OkHttpClient
import com.androidnetworking.error.ANError

import org.json.JSONArray

import com.androidnetworking.interfaces.JSONArrayRequestListener








class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun switchFragment(v: View) {
        Toast.makeText(
            this,
            "Activity switchFragment. Tag" + v.getTag().toString(),
            Toast.LENGTH_SHORT
        ).show()



        fragmentManager = supportFragmentManager

        if(Integer.parseInt(v.tag.toString()) == 1) {
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    Fragment1(),
                    "Fragment1"
                )
                .commit()
        } else if(Integer.parseInt(v.tag.toString()) == 2){
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    Fragment2(),
                    "Fragment2"
                )
                .commit()
        } else{
            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_main,
                    Fragment3(),
                    "Fragment3"
                )
                .commit()
        }
    }
}