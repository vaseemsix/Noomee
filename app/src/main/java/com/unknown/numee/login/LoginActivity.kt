package com.unknown.numee.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.unknown.numee.MainActivity
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.registration.RegistrationActivity


class LoginActivity : BaseActivity(), ViewContract.View {

    companion object {

        private const val RC_SIGN_IN = 123

        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var providers = listOf(
            AuthUI.IdpConfig.EmailBuilder().build()
    )

    private lateinit var signInWithEmailBtn: Button

    private lateinit var presenter: ViewContract.Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initReferences()
        initPresenter()

        presenter.onCreate()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                user?.let {
                    presenter.onSignInSuccess(it)
                }
            } else {
                if (response != null) {
                    presenter.onSignInError(response.error?.message.orEmpty())
                } // otherwise user has canceled
            }
        }
    }

    private fun initReferences() {
        signInWithEmailBtn = findViewById(R.id.activity_login__btn_sign_in_email)
        signInWithEmailBtn.setOnClickListener {
            presenter.onSignInWithEmailClicked()
        }
    }

    private fun initPresenter() {
        val model = LoginModel(this)
        val loginPresenter = LoginPresenter(model)
        loginPresenter.setView(this)
        model.presenter = loginPresenter
        presenter = loginPresenter
    }

    override fun startSignInActivity() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)
    }

    override fun startRegistrationActivity() {
        finish()
        RegistrationActivity.startActivity(this)
    }

    override fun startMainActivity() {
        finish()
        MainActivity.startActivity(this)
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
