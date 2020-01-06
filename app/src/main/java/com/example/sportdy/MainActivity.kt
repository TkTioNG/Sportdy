package com.example.sportdy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.sportdy.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.sportdy.Game.GameFragment


class MainActivity : AppCompatActivity(), DrawerLocker {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        var navView = findViewById<NavigationView>(R.id.navView)
//        navView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        // Show hamburger
        toggle.setDrawerIndicatorEnabled(true)

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar!!.setTitle(R.string.game)

        navController = this.findNavController(R.id.mainHostFragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.gameFragment,
                R.id.chatFragment,
                R.id.friendFragment,
                R.id.communityFragment,
                R.id.settingsFragment,
                R.id.aboutUsFragment,
                R.id.helpFragment
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
//
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView,navController)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun setDrawerEnabled(enabled: Boolean) {
        val lockMode = if (enabled)
            DrawerLayout.LOCK_MODE_UNLOCKED
        else
            DrawerLayout.LOCK_MODE_LOCKED_CLOSED

        //toggle.isDrawerIndicatorEnabled = enabled
        supportActionBar!!.setDefaultDisplayHomeAsUpEnabled(!enabled)
        supportActionBar!!.setDisplayHomeAsUpEnabled(!enabled)
        drawerLayout.setDrawerLockMode(lockMode)
    }

//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        val fragment = when(item.itemId) {
//            R.id.gameFragment ->
//                GameFragment()
//            R.id.chatFragment ->
//                ChatFragment()
//            R.id.friendFragment ->
//                FriendFragment()
//            R.id.communityFragment ->
//                CommunityFragment()
//            R.id.settingsFragment ->
//                SettingsFragment()
//            R.id.aboutUsFragment ->
//                AboutUsFragment()
//            R.id.helpFragment ->
//                HelpFragment()
//            else ->
//                GameFragment()
//        }
//
//        supportFragmentManager.beginTransaction().replace(R.id.mainHostFragment, fragment).commit()
//        drawerLayout.closeDrawer(GravityCompat.START)
//        return true
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = this.findNavController(R.id.mainHostFragment)
//        return NavigationUI.navigateUp(navController, drawerLayout)
//    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater = menuInflater
//        inflater.inflate(R.menu.navdrawer_menu, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val navController = this.findNavController(R.id.mainHostFragment)
//
//        //You must regain the power of swipe for the drawer.
//        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//        // Remove back button
//        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(false);
//        // Show hamburger
//        toggle.setDrawerIndicatorEnabled(true);
//        // Remove the/any drawer toggle listener
//        toggle.setToolbarNavigationClickListener(null);
//
//        navController.navigate(item.itemId)
//
//        return true
//    }
}

public interface DrawerLocker {
    fun setDrawerEnabled(enabled: Boolean)
}
