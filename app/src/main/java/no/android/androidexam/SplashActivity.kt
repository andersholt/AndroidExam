package no.android.androidexam

import android.app.Activity
import android.os.Bundle


class SplashActivity : Activity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}