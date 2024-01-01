package com.example.coffeecorner.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.coffeecorner.R
import com.google.firebase.auth.FirebaseAuth


class ForgotPassword : Base() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        setupActionBar()
    }

    private fun setupActionBar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar_forgot_password_activity)
        setSupportActionBar(toolbar)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_keyboard_double_arrow_left_24)

        }
        toolbar.setNavigationOnClickListener{ onBackPressed()}


       val btnSubmit:Button = findViewById(R.id.btn_submit)

        val email:EditText=findViewById(R.id.et_email_forgot)



        btnSubmit.setOnClickListener{
            val email: String = email.text.toString().trim { it <= ' ' }
            if(email.isEmpty()){
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            }else{
                showProgressDialog(resources.getString(R.string.wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener {
                        task->
                        hideProgressDialog()
                        if(task.isSuccessful){
                            Toast.makeText(this, resources.getString(R.string.email_sent_success), Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
            }



        }
    }






}