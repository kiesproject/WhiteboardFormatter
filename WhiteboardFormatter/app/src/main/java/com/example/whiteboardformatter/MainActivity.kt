package com.example.whiteboardformatter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val navController: NavController = findNavController(R.id.nav_host_fragment)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.editFragment) {
                toolbar.visibility = View.GONE
            } else {
                toolbar.visibility = View.VISIBLE
            }
            setupActionBarWithNavController(navController)
        }

    }
}
