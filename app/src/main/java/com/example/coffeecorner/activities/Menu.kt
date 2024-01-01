package com.example.coffeecorner.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import com.example.coffeecorner.R
import com.example.coffeecorner.firestore.FirestoreCls
import com.example.coffeecorner.models.User
import com.example.coffeecorner.utils.Constants
import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.messaging.FirebaseMessaging

class MenuActivity : Base() {

    private lateinit var logOut: ImageView
    private lateinit var userDetails: ImageView
    private lateinit var homeIcon: ImageView
    private lateinit var cart: ImageView
    private lateinit var cakes: ImageView
    private lateinit var drinks: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        // setupActionBar()
        // Initialize views
        logOut = findViewById(R.id.logoutIcon)
        userDetails = findViewById(R.id.profileIcon)
        homeIcon = findViewById(R.id.homeIcon)
        cart = findViewById(R.id.cartIcon)
        cakes = findViewById(R.id.cakes)
        drinks = findViewById(R.id.drinksDirect)


        // Set click listeners
//        userDetails.setOnClickListener {
//            startActivity(Intent(this, UserProfileDetails::class.java))
//        }


//        var user: User=User()
//        userDetails.setOnClickListener {
//            val intent = Intent(this, UserProfileDetails::class.java)
//            intent.putExtra(Constants.USER_DETAILS, user) // Assuming 'user' is your User object
//            startActivity(intent)
//        }

        userDetails.setOnClickListener {
            FirestoreCls().getUserDetails(this) { user ->
                val intent = Intent(this, UserProfileDetails::class.java)
                intent.putExtra(Constants.USER_DETAILS, user)
                startActivity(intent)
            }

        }
            cakes.setOnClickListener {
                startActivity(Intent(this, Cakes::class.java))
                val intent = Intent(this, Cakes::class.java)
                startActivity(intent)
            }

            drinks.setOnClickListener {
                startActivity(Intent(this, Drinks::class.java))
                val intent = Intent(this, Drinks::class.java)
                startActivity(intent)
            }

            cart.setOnClickListener {
                startActivity(Intent(this, Cart::class.java))
                val intent = Intent(this, Cart::class.java)
                startActivity(intent)
            }


//            finish() // Close the current activity


        logOut.setOnClickListener {
            FirestoreCls().logoutUser(this)
        }


        // Add a listener for homeIcon if needed
            homeIcon.setOnClickListener {
                startActivity(Intent(this, Cart::class.java))
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


    }
        private fun setupActionBar() {
            val toolbar: Toolbar = findViewById(R.id.menu_toolbar)
            setSupportActionBar(toolbar)

            val actionBar = supportActionBar
            if (actionBar != null) {
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.baseline_keyboard_double_arrow_left_24)

            }
            toolbar.setNavigationOnClickListener {
                // Navigate back to the MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }


    }
