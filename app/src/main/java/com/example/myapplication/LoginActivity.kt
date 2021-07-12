package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private val ID = "myid123123"
    private val PW = "mypw123123"
    private val LOGIN_ID_SAVE_CB = "login_id_save_cb"
    private val LOGIN_AUTO_CB = "login_auto_cb"
    private val AUTO_LOGIN = "auto_login"
    private val LOGIN_ID = "login_id"
    private val LOGIN_PW = "login_pw"

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("LoginActivity", "onCreate()")
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //sharedPreferences에 저장되어 있으면 읽어와서 셋팅해줌.
        //id 저장 checkbox, id edittext
        if (SharedPreferenceManager.getBoolValue(this, LOGIN_ID_SAVE_CB) == true) {
            binding.loginSaveIdCb.isChecked = true
            binding.loginIdEt.setText(SharedPreferenceManager.getStrValue(this, LOGIN_ID))
        }
        //pw저장 checkbox
        if (SharedPreferenceManager.getBoolValue(this, LOGIN_AUTO_CB) == true) {
            binding.loginAutoCb.isChecked = true
            binding.loginPwEt.setText(SharedPreferenceManager.getStrValue(this, LOGIN_PW))
        }

        //아이디저장 cb onclick
        binding.loginSaveIdCb.setOnClickListener {
            SharedPreferenceManager.putBoolValue(this, LOGIN_ID_SAVE_CB, binding.loginSaveIdCb.isChecked)
        }

        //자동로그인 cb onclick
        binding.loginAutoCb.setOnClickListener {
            SharedPreferenceManager.putBoolValue(this, LOGIN_AUTO_CB, binding.loginAutoCb.isChecked)
        }

        //login 버튼 onClick
        binding.loginBtn.setOnClickListener {
            val getId = binding.loginIdEt.text.toString()
            val getPw = binding.loginPwEt.text.toString()

            //id와 pw가 일치해야 id, 비밀번호 저장 및 이동
            if (getId == ID && getPw == PW) {
                if (binding.loginSaveIdCb.isChecked) {
                    SharedPreferenceManager.putStrValue(this, LOGIN_ID, getId)
                }
                if (binding.loginAutoCb.isChecked) {
                    SharedPreferenceManager.putStrValue(this, LOGIN_PW, getPw)
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "아이디 또는 비밀번호가 틀리셨습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("LoginActivity", "onStart()")
        //자동로그인 check상태면 아이디 비밀번호
        val getId = binding.loginIdEt.text.toString()
        val getPw = binding.loginPwEt.text.toString()
        if (SharedPreferenceManager.getBoolValue(this, LOGIN_AUTO_CB) == true) {
            if (getId == ID && getPw == PW) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("LoginActivity", "onResume()")
    }
}