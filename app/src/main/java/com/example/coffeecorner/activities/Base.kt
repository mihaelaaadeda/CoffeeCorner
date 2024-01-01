package com.example.coffeecorner.activities

import android.app.Dialog
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.coffeecorner.R
import com.google.android.material.snackbar.Snackbar


open class Base : AppCompatActivity() {

    private lateinit var progressDialog: Dialog


    fun showErrorSnackBar(message: String, errorMessage: Boolean) { //displays a message, the boolean says if it is error or not
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        if (errorMessage) {
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorSnackBarError
                )
            )
        }else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this,
                    R.color.colorSnackBarSuccess
                )
            )
        }
        snackBar.show()
    }


    fun showProgressDialog(text: String) {
        progressDialog = Dialog(this) //initialize it

        //set the content view
        progressDialog.setContentView(R.layout.dialog_progress)

        val tvProgressText: TextView=progressDialog.findViewById(R.id.tv_progress_text)
        tvProgressText.text = text //can change the text dinamically here

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        //Start the dialog and display it on screen.
        progressDialog.show()
    }

    fun hideProgressDialog() {
        progressDialog.dismiss() //as it cannot be canceled by the user manually
                                 //removes the dialog from the screen once we hideProcessDialog
    }



}