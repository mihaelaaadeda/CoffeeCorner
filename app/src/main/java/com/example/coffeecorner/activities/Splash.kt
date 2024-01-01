package com.example.coffeecorner.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import com.example.coffeecorner.R

class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)




        // Handle full-screen mode appropriately based on the Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            @Suppress("DEPRECATION")
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }


        // Use a Handler with the main Looper
        Handler(Looper.getMainLooper()).postDelayed(
            {
                //start with the splash activity, the main activity, which will finish
                startActivity(Intent(this@Splash, Login::class.java))
                finish() // Call this when my activity is done and should be closed.
            },
            3000 // the delay time
        )

        //initialize the TextView
        //val textAppName = findViewById<TextView>(R.id.text_app_name)
        //this typeface is a style for the text
        //val typeface: Typeface = Typeface.createFromAsset(assets, "Montserrat-Bold.ttf")
        //textAppName.typeface=typeface





    }
}