package com.example.coffeecorner.utils

object Constants {


    //use this to get the user collection from the database
    const val  USERS: String ="users" //allows us to use this throughout all the application
    //use these preferences in order to store the data in the device
    const val COFFECORNER_PREFERENCES: String="CoffeeCornerPref"  //we store the as key-value  pair
    //(key is the location where we want to store (String="CoffeeCornerPref") and the value can be String, Int...
    const val LOGGED_IN_USERNAME: String="logged_in_username"
    const val USER_DETAILS:String="user_details"


}