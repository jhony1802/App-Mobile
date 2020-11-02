package com.example.helloworld

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_admin_main.*

class AdminMainActivity : AppCompatActivity() {

    lateinit var toggle: ActionBarDrawerToggle
    lateinit var tb: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open_drawer, R.string.close_drawer)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        tb = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> Toast.makeText(
                    applicationContext,
                    "Clicou em Home", Toast.LENGTH_SHORT
                ).show()
                R.id.nav_cardapio -> Toast.makeText(
                    applicationContext,
                    "Clicou em Cardápio", Toast.LENGTH_SHORT
                ).show()
                R.id.nav_refAgend -> Toast.makeText(
                    applicationContext,
                    "Clicou em RA", Toast.LENGTH_SHORT
                ).show()
                R.id.nav_notifics -> Toast.makeText(
                    applicationContext,
                    "Clicou em Notificações", Toast.LENGTH_SHORT
                ).show()
                R.id.nav_configs -> Toast.makeText(
                    applicationContext,
                    "Clicou em Configs", Toast.LENGTH_SHORT
                ).show()
                R.id.nav_perfil -> Toast.makeText(
                    applicationContext,
                    "Clicou em Perfil", Toast.LENGTH_SHORT
                ).show()
                R.id.nav_logout -> Toast.makeText(
                    applicationContext,
                    "Clicou em Logout", Toast.LENGTH_SHORT
                ).show()
                R.id.nav_help -> Toast.makeText(
                    applicationContext,
                    "Clicou em Help", Toast.LENGTH_SHORT
                ).show()
                R.id.nav_rate -> Toast.makeText(
                    applicationContext,
                    "Clicou em Rate", Toast.LENGTH_SHORT
                ).show()
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}