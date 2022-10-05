package com.social.social.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.social.social.R
import com.social.social.getSharedPrefBoolean
import com.social.social.getSharedPrefString
import com.social.social.services.AuthService
import kotlinx.android.synthetic.main.activity_splash_screen.*

const val SPLASH_SCREEN_DURATION = 900L

class SplashScreenActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val authenticated = AuthService.getAccessToken() != null

        val userInfoFilled = this.getSharedPrefBoolean("userInfoFilled")


        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(
                Intent(
                    this,
                    if (authenticated) (if(userInfoFilled) ConversationsActivity::class.java else UserInfoActivity::class.java) else AuthenticationActivity::class.java
                )
            )
            finish()
        }, SPLASH_SCREEN_DURATION)


    }
}