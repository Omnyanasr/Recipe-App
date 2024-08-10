package com.example.recipeapp.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.recipeapp.R
import com.example.recipeapp.authintication.SharedPrefManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class RecipeActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        // Setup Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Setup Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_recipe) as NavHostFragment
        navController = navHostFragment.navController

        // Setup BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        // Setup ActionBar with NavController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.favoriteFragment,
                R.id.searchFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        // Handle BottomNavigationView item selection
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val destinationId = when (menuItem.itemId) {
                R.id.homeFragment -> R.id.homeFragment
                R.id.favoriteFragment -> R.id.favoriteFragment
                R.id.searchFragment -> R.id.searchFragment
                else -> return@setOnItemSelectedListener false
            }

            if (navController.currentDestination?.id != destinationId) {
                navController.navigate(destinationId)
            }

            true
        }
        // Update toolbar title on navigation
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val title = when (destination.id) {
                R.id.homeFragment -> "Home"
                R.id.favoriteFragment -> "Favorites"
                R.id.searchFragment -> "Search"
                else -> "Recipe App"
            }
            setToolbarTitle(title)
    }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                handleSignOut()
                true
            }
            R.id.action_about -> {
                navigateToAboutFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun handleSignOut() {
        AlertDialog.Builder(this)
            .setTitle("Sign Out")
            .setMessage("Are you sure you want to sign out?")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                val sharedPrefManager = SharedPrefManager.getInstance(this)
                sharedPrefManager.logout()
                startActivity(Intent(this, AuthActivity::class.java))
                finish()
            }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    private fun navigateToAboutFragment() {
        // Navigate to the AboutFragment
        navController.navigate(R.id.aboutFragment)
    }
    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
