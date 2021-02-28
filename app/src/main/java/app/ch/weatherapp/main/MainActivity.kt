package app.ch.weatherapp.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import app.ch.weatherapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (supportFragmentManager.findFragmentById(R.id.nav_main_fragment) as NavHostFragment)
            .navController
            .let {
                setupActionBarWithNavController(it, AppBarConfiguration(it.graph))
            }
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_main_fragment).navigateUp() || super.onSupportNavigateUp()
    }
}
