package com.example.visualizar.activities

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import com.example.visualizar.R


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

     var search: SearchView? = null



     override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_1)
        search = findViewById(R.id.svIndicador)
//        search.setOnQueryTextListener(this);
     }

     override fun onQueryTextSubmit(query: String?): Boolean {
         TODO("Not yet implemented")
     }

     override fun onQueryTextChange(newText: String?): Boolean {
         TODO("Not yet implemented")
     }
 }