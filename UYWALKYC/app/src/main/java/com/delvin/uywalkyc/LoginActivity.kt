package com.delvin.uywalkyc

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.delvin.uywalkyc.LoginSchema.LoginRequest
import com.delvin.uywalkyc.LoginSchema.LoginResponse
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    var urlbase = "http://192.168.18.8:8080/api/v1/auth/"
    private lateinit var txtEmail: EditText
    private lateinit var txtPassword: EditText
    private lateinit var btnSignIn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val txtRecover = findViewById<TextView>(R.id.txtCrearcuenta)
        txtRecover.setOnClickListener{
            val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
            startActivity(intent)
        }
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        txtEmail=findViewById(R.id.input_email)
        txtPassword=findViewById(R.id.input_password)
        btnSignIn=findViewById(R.id.btn_signup)

    }

    var retrofit = Retrofit.Builder()
        .baseUrl(urlbase)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service=retrofit.create(PostApiService::class.java)

    fun SignIn(view: View){
        val email = txtEmail.text.toString()
        val password = txtPassword.text.toString()

        lifecycleScope.launch {
            try {
                val response: Response<LoginResponse> = service.getUserLogin(LoginRequest(email, password))
                runOnUiThread {
                    if (response.isSuccessful){
                        val result: LoginResponse? = response.body()
                        if (result != null && result.idTipoUsuario == 1){
                            val intent = Intent(this@LoginActivity,MenuActivity::class.java)
                            startActivity(intent)

                            // Log the token and idTipoUsuario
                            Log.d("Token", result.token)
                            Log.d("idTipoUsuario", result.idTipoUsuario.toString())
                        }else{
                            Toast.makeText(this@LoginActivity,"Usuario incorrecto",Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity,"Error en la solicitud: ${response.code()}",Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e:Exception){
                e.printStackTrace()
                Toast.makeText(this@LoginActivity,"Error de conexion",Toast.LENGTH_SHORT).show()
            }
        }
    }




}
