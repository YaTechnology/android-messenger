package com.github.ndex.messenger.demo_module.presentation.login

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.github.ndex.messenger.demo_module.App
import com.github.ndex.messenger.demo_module.R
import com.github.ndex.messenger.demo_module.presentation.chatlist.startChatListActivity

class LoginActivity : AppCompatActivity() {
    private val loginEdit by lazy { findViewById<EditText>(R.id.login_edit_text) }
    private val startAuthButton by lazy { findViewById<Button>(R.id.start_auth_button) }
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val appComponent = (application as App).appComponent
        val factory = LoginViewModelFactory(appComponent)
        loginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

        initView()

        subscribe()

        loginViewModel.onCreated()
    }

    private fun initView() {
        startAuthButton.setOnClickListener({
            loginViewModel.onStartAuth(loginEdit.text.toString())
        })
    }

    private fun subscribe() {
        loginViewModel.showLoginScreen.observe(this, Observer {
            if (it == null || !it) {
                finish()
                startChatListActivity()
            }
        })
    }
}
