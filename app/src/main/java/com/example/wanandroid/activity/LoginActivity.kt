package com.example.wanandroid.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.wanandroid.R

import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class LoginActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏？
        setContentView(R.layout.activity_login)
//        setSupportActionBar(logintoolbar)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.logintoolbar,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.more -> Toast.makeText(this, " HELP", Toast.LENGTH_SHORT).show()
            R.id.register ->{
                val intent=Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
        return true
    }
//    override fun onClick(v:View?){
//        when (v?.id) {
//            R.id.more -> {
//                AlertDialog.Builder(this).apply {
//                    setTitle("帮助")
//                    setMessage("登录、注册")
//                    setCancelable(false)
//                    setPositiveButton("确定") { dialog, which ->
//                    }
//                    setNegativeButton("返回") { dialog, which ->
//                    }
//                    show()
//                }
//            }
//            R.id.login ->{
//                    val account = accountEdit.text.toString()
//                    if (account==""){
//                        Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show()
//                    }
//                    val password =passwordEdit.text.toString()
//                    if(password==""){
//                        Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show()
//                    }
//
//                    val client= OkHttpClient()
//                    val requestBody = FormBody.Builder()
//                        .add("username", account)
//                        .add("password", password)
//                        .build()
//                    val request = Request.Builder()
//                        .url("https://www.wanandroid.com/user/login")
//                        .post(requestBody)
//                        .build()
//                    val response = client.newCall(request).execute()
//
//                    val responseData = response.body?.string()
//                    if (responseData != null) {
//                        try{
//                            //val json =JSONObject(responseData)
//                            val jsonArray = JSONArray(responseData)
//                            val jsonObject= jsonArray.getJSONObject(0)
//                            val errorCode = jsonObject.getString("errorCode")
//                            val errorMessage =jsonObject.getString("errorMessage")
//                            if(errorCode=="-1"){
//                                Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
//                            }else if (errorCode=="0"){//登录成功
//
//                                val intent=Intent()
//                                intent.putExtra("name",jsonObject.getString("nickname"))
////存储收藏文章
////                                val array=ArrayList<Int>()
////                                jsonObject.getJSONArray()
//                                intent.putExtra("collectIds",jsonObject.getString("collectIds"))
//                                //TODO: Cookie
//                                finish()
//                                //直接登录
//
//
//
//                            }
//                        }catch (e:Exception){
//                            e.printStackTrace()
//                        }
//                    }
//
//
//            }
//        }
//
//    }


}