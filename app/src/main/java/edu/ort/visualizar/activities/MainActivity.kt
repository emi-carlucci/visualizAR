package edu.ort.visualizar.activities

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import edu.ort.visualizar.R
import edu.ort.visualizar.utils.OCBUtils


class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    companion object {
        val ocbUtils = OCBUtils()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }
}