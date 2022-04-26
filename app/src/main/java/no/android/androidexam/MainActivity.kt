package no.android.androidexam

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import no.android.androidexam.fragments.Fragment1
import no.android.androidexam.fragments.Fragment2
import no.android.androidexam.fragments.Fragment3


class MainActivity : AppCompatActivity() {
    private lateinit var fragmentManager: FragmentManager
    private  var fragment1 =  Fragment1()
    private  var fragment2 =  Fragment2()
    private  var fragment3 =  Fragment3()


    override fun onCreate(savedInstanceState: Bundle?) {
        fragmentManager = supportFragmentManager
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_main,
                fragment1,
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
                .addToBackStack(null)
                .replace(
                    R.id.fragment_main,
                    fragment1,
                    "Fragment1"
                )
                .commit()
        } else if(Integer.parseInt(v.tag.toString()) == 2){
            fragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.fragment_main,
                    fragment2,
                    "Fragment2"
                )
                .commit()
        } else{
            fragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .replace(
                    R.id.fragment_main,
                    fragment3,
                    "Fragment3"
                )
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        Toast.makeText(this, "Activity onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(this, "Activity onResume", Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        Toast.makeText(this, "Activity onPause", Toast.LENGTH_SHORT).show()
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "Activity onStop", Toast.LENGTH_SHORT).show()
    }

    override fun onRestart() {
        super.onRestart()
        Toast.makeText(this, "Activity onRestart", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Activity onDestroy", Toast.LENGTH_SHORT).show()
    }
}