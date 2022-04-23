package no.android.androidexam

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import no.android.androidexam.fragments.Fragment1
import no.android.androidexam.fragments.Fragment2
import no.android.androidexam.fragments.Fragment3


class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentManager = supportFragmentManager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_main,
                Fragment1(),
                "Fragment1"
            )
            .commit()
    }

    fun switchFragment(v: View) {
        fragmentManager = supportFragmentManager

        Toast.makeText(
            this,
            "Activity switchFragment. Tag" + v.tag.toString(),
            Toast.LENGTH_SHORT
        ).show()

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