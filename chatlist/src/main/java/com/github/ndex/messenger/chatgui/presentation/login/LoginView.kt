package com.github.ndex.messenger.chatgui.presentation.login

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.FragmentActivity
import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.github.ndex.messenger.chatgui.di.AppComponentHolder
import com.github.ndex.messenger.demo_module.R

class LoginView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val loginEdit by lazy { findViewById<EditText>(R.id.login_edit_text) }
    private val startAuthButton by lazy { findViewById<Button>(R.id.start_auth_button) }
    private val loginViewModel: LoginViewModel

    init {
        View.inflate(context, R.layout.activity_login, this)
        val activity = context as FragmentActivity

        val appComponent = AppComponentHolder.provideAppComponent(context.applicationContext)
        val factory = LoginViewModelFactory(appComponent)
        loginViewModel = ViewModelProviders.of(activity, factory).get(LoginViewModel::class.java)

        initView()
    }

    private fun initView() {
        startAuthButton.setOnClickListener({
            loginViewModel.onStartAuth(loginEdit.text.toString())
        })
    }
}
