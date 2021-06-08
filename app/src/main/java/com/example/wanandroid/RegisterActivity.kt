package com.example.wanandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register.setOnClickListener {
            val account = accountEdit.text.toString()
            if (account==""){
                Toast.makeText(this,"请输入账号",Toast.LENGTH_SHORT).show()
            }
            val password =passwordEdit.text.toString()
            if(password==""){
                Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show()
            }
            val repassword=repassword.text.toString()
            if(password!=repassword){
                Toast.makeText(this,"两次密码不相同",Toast.LENGTH_SHORT).show()
            }

            val client= OkHttpClient()
            val requestBody = FormBody.Builder()
                .add("username", account)
                .add("password", password)
                .add("repassword",repassword)
                .build()
            val request = Request.Builder()
                .url("https://www.wanandroid.com/user/register")
                .post(requestBody)
                .build()
            val response = client.newCall(request).execute()
            val responseData = response.body?.string()
            if (responseData != null) {
                parseJSONWithJSONObject(responseData)
            }

        }
    }


    private fun parseJSONWithJSONObject(jsonData: String) {
        try {
            val jsonArray = JSONArray(jsonData)
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.getString("id")
                Log.d("MainActivity", "id is $id")

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
