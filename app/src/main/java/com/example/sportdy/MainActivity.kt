package com.example.sportdy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.sportdy.Chat.ChatFragment
import com.example.sportdy.Community.CommunityFragment
import com.example.sportdy.Friend.FriendFragment
import com.example.sportdy.Game.GameFragment
import com.example.sportdy.Game.GameViewModel
import com.example.sportdy.Settings.SettingsFragment
import com.example.sportdy.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        drawerLayout = binding.drawerLayout
        setSupportActionBar(binding.toolbar)

        var navView = binding.navView
        navView.setNavigationItemSelectedListener(this)
                

        var toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.nav_drawer_open,
            R.string.nav_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.mainHostFragment, GameFragment()).commit()
            navView.setCheckedItem(R.id.gameFragment)
        }

        drawerLayout = binding.drawerLayout
//
//        val navController = this.findNavController(R.id.mainHostFragment)
//
//        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
//        NavigationUI.setupWithNavController(binding.navView,navController)

//        val actionBarDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close)

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment = when(item.itemId) {
            R.id.gameFragment ->
                GameFragment()
            R.id.chatFragment ->
                ChatFragment()
            R.id.friendFragment ->
                FriendFragment()
            R.id.communityFragment ->
                CommunityFragment()
            R.id.settingsFragment ->
                SettingsFragment()
            R.id.aboutUsFragment ->
                AboutUsFragment()
            R.id.helpFragment ->
                HelpFragment()
            else ->
                GameFragment()
        }

        supportFragmentManager.beginTransaction().replace(R.id.mainHostFragment, fragment).commit()
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

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
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }

}
