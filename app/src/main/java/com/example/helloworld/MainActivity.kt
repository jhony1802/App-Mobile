package com.example.helloworld

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloworld.model.EatObject
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
//private const val PERMISSION_REQUEST = 10

private val TAG = "MainActivity"
private var btn_go: Button? = null
private var eatList: List<EatObject> = ArrayList()
private val mData: FirebaseFirestore = FirebaseFirestore.getInstance()
private val listAdapter: com.example.helloworld.fragment.ListAdapter =
    com.example.helloworld.fragment.ListAdapter(eatList)
// private var butt: View=findViewById(R.id.floatingActionButton)

class MainActivity : AppCompatActivity() {
    //menuLateral
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var tool: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()
        //replaceFragment(EatFragment())
        recycler_eat.layoutManager = LinearLayoutManager(this)
        recycler_eat.adapter = listAdapter
        //   butt.setOnClickListener{floatClick()}

        //menu lateral
        tool = findViewById(R.id.tbar)
        setSupportActionBar(tbar)

        toggle = ActionBarDrawerToggle(this, drawerlayout, R.string.open_drawer, R.string.close_drawer)
        drawerlayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navigView.setNavigationItemSelectedListener {
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
            //MenuLateral-Fim
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadData() {
        mData.collection("eats").addSnapshotListener { value, e ->
            if (e != null) {
                Log.w(TAG, "not sucess to listen")
                return@addSnapshotListener
            }
            eatList = value!!.toObjects(EatObject::class.java)
            listAdapter.list = eatList
            listAdapter.notifyDataSetChanged()
            println(eatList[0])
            Log.d(TAG, "current eat is: $eatList")
        }
    }
}
