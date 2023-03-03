package com.example.thebeatles

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity()
{
  companion object
  {
    private var instance: MainActivity? = null
    fun getInstance(): MainActivity
    {
      return instance!!
    }
  }

  override fun onCreate(savedInstanceState: Bundle?)
  {
    super.onCreate(savedInstanceState)
    instance = this
    setContentView(R.layout.activity_main) // note: time permitting see if you can put this below the variables below

    val navController = Navigation.findNavController(this, R.id.navigation_controller)
    var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
    bottomNavigationView.setupWithNavController(navController)

  }
}