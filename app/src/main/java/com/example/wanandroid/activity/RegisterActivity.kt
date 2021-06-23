package com.example.wanandroid.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import com.example.wanandroid.R
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//隐藏状态栏

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
                try{
                    val jsonArray = JSONArray(responseData)
                    val jsonObject= jsonArray.getJSONObject(0)
                    val errorCode = jsonObject.getString("errorCode")
                    val errorMessage =jsonObject.getString("errorMessage")
                    if(errorCode=="-1"){
                        Toast.makeText(this,errorMessage,Toast.LENGTH_SHORT).show()
                    }else if (errorCode=="0"){//注册成功
                        //直接登录 TODO
                        





                    }
                }catch (e:Exception){
                    e.printStackTrace()
                }
            }

        }
    }




}
