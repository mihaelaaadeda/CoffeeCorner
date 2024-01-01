package com.example.coffeecorner.firestore

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.example.coffeecorner.activities.Login
import com.example.coffeecorner.activities.Register
import com.example.coffeecorner.models.User
import com.example.coffeecorner.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions


//a class for storing any data for firestore database
class FirestoreCls {

    //access the firestore instance
    private val mFireStore = FirebaseFirestore.getInstance()





    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        // A variable to assign the currentUserId if it is not null or else it will be blank.
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        return currentUserID
    }

     //function to make an entry of the registered user in the database.
    fun registerUser(activity: Register, userInfo: User) {

        //"users" = collection name, if collection is already created then it will not create the same one again.
        mFireStore.collection(Constants.USERS)
            // Document ID for users fields. Here the document it is the User ID.
            .document(userInfo.id)
            // Here the userInfo are Field and the SetOption is set to merge. It is for if we wants to merge later on instead of replacing the fields.
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                // Here call a function of base activity for transferring the result to it.
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering the user.",
                    e
                )
            }
    }



    //function to get the user details from the database

    fun getUserDetails(activity: Activity, onComplete: (User)->Unit) {

        // pass the collection name from which we need to get data.
        mFireStore.collection(Constants.USERS) //inside of collection we have documents
            .document(getCurrentUserID()) //inside of documents we have fields
            .get()
            .addOnSuccessListener { document -> //go ahead if successful, we get the entire document here
                Log.i(activity.javaClass.simpleName, document.toString()) //documented will be printed out in the log

                val user = document.toObject(User::class.java)!!// this is the user object

                val sharedPreferences=
                    activity.getSharedPreferences(
                        Constants.COFFECORNER_PREFERENCES,
                        Context.MODE_PRIVATE //make sure these shared preferences are available only inside our application
                    )

                //now can use tis sharedPref to store data in it, so we need an editor
                //like a little storage on our device
                val editor: SharedPreferences.Editor=sharedPreferences.edit()
                editor.putString( //accepts key value pair (here we put a string inside of our shared pref)
                    Constants.LOGGED_IN_USERNAME, //key
                    "${user.firstName} ${user.lastName}" //value
                )
                //make sure we apply what we have edited
                editor.apply()


                when (activity) {

                    is Login -> { //when the activity is of type login

                        activity.userLoggedInSuccess(user)
                    }
                }

            }
            .addOnFailureListener { e ->
                // Hide the progress dialog if there is any error. And print the error in log.
                when (activity) {
                    is Login -> {
                        activity.hideProgressDialog()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )
            }
    }

    fun logoutUser(activity: Activity) {
        // Clear any local data related to the user
        val sharedPreferences = activity.getSharedPreferences(Constants.COFFECORNER_PREFERENCES, Context.MODE_PRIVATE)
        sharedPreferences.edit().clear().apply()

        // Sign out from Firebase Authentication
        FirebaseAuth.getInstance().signOut()

        // Redirect to the login screen or main activity
        val intent = Intent(activity, Login::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
    



}