package com.example.coffeecorner.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.coffeecorner.R
import com.example.coffeecorner.firestore.FirestoreCls
import com.example.coffeecorner.models.User
import com.example.coffeecorner.utils.Button
import com.example.coffeecorner.utils.EditText
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser



class Register : Base() {

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var confirmPassword: EditText
    private lateinit var terms: CheckBox


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
//initialize the views
        firstName=findViewById(R.id.et_first_name)
        lastName=findViewById(R.id.et_last_name)
        email=findViewById(R.id.et_email)
        password=findViewById(R.id.et_password)
        confirmPassword=findViewById(R.id.et_confirm_password)
        terms=findViewById(R.id.cb_terms_and_condition)

        //call the setUpActionBar
        setupActionBar()

        val btnRegister =findViewById<Button>(R.id.btn_register)
        btnRegister.setOnClickListener{
            registerUser()
        }

        val tvLogin = findViewById<Button>(R.id.tv_login)
        tvLogin?.setOnClickListener {

            //will create an intent which will send us to the Login activity
           // val intent = Intent(this, Login::class.java)
            //startActivity(intent)

            //not creating an extra layer, basically closing the app
            onBackPressed()
        }




    }

    private fun setupActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_register_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_keyboard_double_arrow_left_24)

        }
        toolbar.setNavigationOnClickListener{ onBackPressed()}
    }





    //user data validation
    private fun validateRegisterDetails(): Boolean {
        return when {
            TextUtils.isEmpty(firstName.text.toString().trim { it <= ' ' }) || firstName.length() <=3 -> { //trim empty spaces
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
            }

            TextUtils.isEmpty(lastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }

            TextUtils.isEmpty(email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }

            TextUtils.isEmpty(password.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }

            TextUtils.isEmpty(confirmPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }

            password.text.toString().trim { it <= ' ' } != confirmPassword.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !terms.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
                // showErrorSnackBar("Thanks for registering.", false)
                true //only when its true register user then go to the next step
            }
        }
    }

//function for registering the user with email and password using firebase
    private fun registerUser() {

        if (validateRegisterDetails()) {

            showProgressDialog(resources.getString(R.string.wait)) //or we can add text manually

            val email: String = email.text.toString().trim { it <= ' ' }
            val password: String = password.text.toString().trim { it <= ' ' }

            // Create an instance and create a register a user with email and password.
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password) //from the ones declared above
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->


                        // If the registration is successfully done
                        if (task.isSuccessful) {

                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            val user= User(
                                firebaseUser.uid,
                                firstName.text.toString().trim{it <= ' '},
                                lastName.text.toString().trim{it <= ' '},
                                //email = email.text.toString().trim { it <= ' ' }

                                // email.toString().trim(){it<=' '}
                            )

                            //now we want to store these details in the cloud
                            FirestoreCls().registerUser(this, user )

//                            showErrorSnackBar(
//                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
//                                false
//                            )

                           // FirebaseAuth.getInstance().signOut()
                            //finish() //close the register activity and we are going to be send back to the login activity
                        } else {
                            hideProgressDialog()
                            // If the registering is not successful then show error message.
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }

                    })
        }
    }



    //create a function to display once the user registered successfully
    fun userRegistrationSuccess(){

        //hide the progress dialog
        hideProgressDialog()

        Toast.makeText(
            this,
            resources.getString(R.string.register_success),
            Toast.LENGTH_SHORT
        ).show()

        FirebaseAuth.getInstance().signOut()
        // Close the Register Screen
        finish()

    }


        }



