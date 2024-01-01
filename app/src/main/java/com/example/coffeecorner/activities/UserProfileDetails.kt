package com.example.coffeecorner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.coffeecorner.R
import com.example.coffeecorner.firestore.FirestoreCls
import com.example.coffeecorner.models.User
import com.example.coffeecorner.utils.Constants
import com.example.coffeecorner.utils.TextView



class UserProfileDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account_details)

        val firstNameDisplay: TextView = findViewById(R.id.firstNameDisplay)
        val lastNameDisplay: TextView = findViewById(R.id.lastNameDisplay)
        val emailDisplay: TextView = findViewById(R.id.emailDisplay)

        var userDetails: User=User()
        if(intent.hasExtra(Constants.USER_DETAILS)){ //check if that exists

            //get the user details from intent
            userDetails=intent.getParcelableExtra(Constants.USER_DETAILS)!!
        }


        firstNameDisplay.setText(userDetails.firstName)
        lastNameDisplay.setText(userDetails.lastName)
        emailDisplay.setText(userDetails.email)



    }
}




//        // Initialize your TextViews for displaying user details
//        val firstNameDisplay: TextView = findViewById(R.id.firstNameDisplay)
//        val lastNameDisplay: TextView = findViewById(R.id.lastNameDisplay)
//        val emailDisplay: TextView = findViewById(R.id.emailDisplay)
//
//        // Create an instance of FirestoreCls and call getUserDetails
//        FirestoreCls().getUserDetails(this) { user ->
//            // This block will be called when getUserDetails successfully fetches the user
//            // Update the TextViews with the fetched user details
//            firstNameDisplay.text = user.firstName
//            lastNameDisplay.text = user.lastName
//            emailDisplay.text = user.email
//        }
//
//      //  setupActionBar()
//    }


//    private fun setupActionBar() {
//        val toolbar: Toolbar = findViewById(R.id.toolbar_register_activity)
//        setSupportActionBar(toolbar)
//
//        val actionBar = supportActionBar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//            actionBar.setHomeAsUpIndicator(R.drawable.baseline_keyboard_double_arrow_left_24)
//
//        }
//        toolbar.setNavigationOnClickListener{ onBackPressed()}
//    }
//


