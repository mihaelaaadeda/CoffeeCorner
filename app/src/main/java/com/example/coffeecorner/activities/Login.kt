package com.example.coffeecorner.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import com.example.coffeecorner.R
import com.example.coffeecorner.firestore.FirestoreCls
import com.example.coffeecorner.models.User
import com.example.coffeecorner.utils.Constants
import com.example.coffeecorner.utils.EditText
import com.example.coffeecorner.utils.TextViewBold
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


private lateinit var email: EditText
private lateinit var password: EditText
private lateinit var btnLogin: Button
private lateinit var register: TextViewBold
private lateinit var forgotPass: TextView



class Login : Base(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

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

        //in order the button to work add a click listener
        btnLogin=findViewById(R.id.btn_login)
        btnLogin.setOnClickListener(this)

        register=findViewById(R.id.tv_register)
        register.setOnClickListener(this)

        forgotPass=findViewById(R.id.tv_forgot_password)
        forgotPass.setOnClickListener(this)



//initialize the views

        email=findViewById(R.id.et_email)
        password=findViewById(R.id.et_password)


//  val tvRegister = findViewById<TextViewBold>(R.id.tv_register)
    }

        // In Login screen the clickable components are Login Button, ForgotPassword text and Register Text.
        override fun onClick(v: View?) {
            if (v != null) { //check if the view is not null
                when (v.id) {

                    R.id.tv_forgot_password -> {
                        val intent = Intent(this, ForgotPassword::class.java)
                        startActivity(intent)
                    }

                    R.id.btn_login -> { //validate the details and log the user in using the firebase

                        logInRegisteredUser()

                    }

                    R.id.tv_register -> { //if user clicks register btn then creates an intent which send the user to the Register activity
                        val intent = Intent(this, Register::class.java)
                        startActivity(intent)
                    }
                }
            }
        }


    //validate the login details
    private fun validateLoginDetails(): Boolean {
        return when {
            TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            else -> {

                true
            }
        }
    }



    private fun logInRegisteredUser(){

        if(validateLoginDetails()){

            //show the dialog progress
            showProgressDialog(resources.getString(R.string.wait))

            //trim the space from the editText

            val email: String = email.text.toString().trim { it <= ' ' }
            val password: String = password.text.toString().trim { it <= ' ' }

            //login the user using firebase
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener{ //add the complete listener which then returns a task
                    task ->


                    if(task.isSuccessful){ //send user to main activity
                       FirestoreCls().getUserDetails(this){user -> userLoggedInSuccess(user)}

                    }else{
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.message.toString(), true)
                    }
                }

        }
    }


    fun userLoggedInSuccess(user: User) {

        // Hide the progress dialog.
        hideProgressDialog()

        // print the user details
        Log.i("First Name: ", user.firstName)
        Log.i("Last Name: ", user.lastName)
        Log.i("Email: ", user.email)

        // Redirect the user to Main after log in.
//        startActivity(Intent(this, MainActivity::class.java))
//        finish()

//        if (user.profileCompleted == 0) {
//            //if the user profile is incomplete then launch the UserProfileActivity.
//            val intent = Intent(this, UserProfileDetails::class.java)
//            //pass the user details to the user profile screen.
//            intent.putExtra(Constants.USER_DETAILS, user)
//
//            startActivity(intent)
//        } else {
            // Redirect the user to Main Screen after log in.
            startActivity(Intent(this, MainActivity::class.java))

       finish()




    }

}







