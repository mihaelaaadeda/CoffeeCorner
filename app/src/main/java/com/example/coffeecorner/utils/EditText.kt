package com.example.coffeecorner.utils

import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText

// this class  extends AppCompatEditText and applies a custom font
class EditText (context: Context, attrs: AttributeSet):AppCompatEditText(context, attrs){

    init {
        applyFont()
    }
    private fun applyFont(){
        //used to get the file from the assets folder and set it to the title textView
        val typeface: Typeface =
            Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }

}