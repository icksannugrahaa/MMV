package com.test.dikshatek.mmv.ui.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.test.dikshatek.mmv.databinding.ActivitySplashScreenBinding
import com.test.dikshatek.mmv.ui.base.BaseActivity
import com.test.dikshatek.mmv.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override fun getActivityBinding(): ActivitySplashScreenBinding = ActivitySplashScreenBinding.inflate(layoutInflater)

    override fun setUp(savedInstanceState: Bundle?) {
//        runBlocking {
//            delay(3000)
//            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
//        }
        val handler = Handler()
        handler.postDelayed(Runnable { // Do something after 5s = 5000ms
            startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }
}