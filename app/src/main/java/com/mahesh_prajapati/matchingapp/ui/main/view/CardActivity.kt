package com.mahesh_prajapati.matchingapp.ui.main.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mahesh_prajapati.matchingapp.coroutines.R


class CardActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}