package com.example.coffeecorner.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.coffeecorner.R
import com.example.coffeecorner.utils.Button
import com.example.coffeecorner.utils.Constants
import com.example.coffeecorner.utils.TextViewBold

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBar()

        val sharedPreferences = getSharedPreferences(Constants.COFFECORNER_PREFERENCES, Context.MODE_PRIVATE)
        val user = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME, "")!! //get the string at this particular key
        val textMain: TextViewBold = findViewById(R.id.text_main)
        textMain.text = "Hello $user"

        val buttonGoToMenu: Button = findViewById(R.id.btnGoToMenu)
        buttonGoToMenu.setOnClickListener {
            // Start MenuActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.baseline_keyboard_double_arrow_left_24)
        }

        toolbar.setNavigationOnClickListener {
            // Explicitly start the Login activity
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
