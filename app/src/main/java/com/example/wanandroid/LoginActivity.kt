package com.example.wanandroid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏？
        setContentView(R.layout.activity_login)
        setSupportActionBar(logintoolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logintoolbar,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.more -> Toast.makeText(this, "You clicked HELP", Toast.LENGTH_SHORT).show()
            R.id.register ->{
                Toast.makeText(this, "You clicked register", Toast.LENGTH_SHORT).show()
                val intent=Intent(this,RegisterActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
}