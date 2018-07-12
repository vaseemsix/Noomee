package com.unknown.numee.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.unknown.numee.MainActivity
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.registration.RegistrationActivity


class LoginActivity : BaseActivity(), ViewContract.View {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var signUpWithEmailBtn: Button
    private lateinit var signInWithEmailBtn: Button
    private lateinit var emailTextInput: TextInputLayout
    private lateinit var nameTextInput: TextInputLayout
    private lateinit var passwordTextInput: TextInputLayout

    private lateinit var presenter: ViewContract.Listener

    private val authenticator = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initReferences()
        initPresenter()

        presenter.onCreate()
    }

    private fun initReferences() {
        emailTextInput = findViewById(R.id.activity_login__edit_email)
        nameTextInput = findViewById(R.id.activity_login__edit_name)
        passwordTextInput = findViewById(R.id.activity_login__edit_password)
        signUpWithEmailBtn = findViewById(R.id.activity_login__btn_sign_up_email)
        signInWithEmailBtn = findViewById(R.id.activity_login__btn_sign_in_email)
        signUpWithEmailBtn.setOnClickListener { presenter.onSignUpWithEmailClicked() }
        signInWithEmailBtn.setOnClickListener { presenter.onSignInWithEmailClicked() }
    }

    private fun initPresenter() {
        val model = LoginModel(this)
        val loginPresenter = LoginPresenter(model)
        loginPresenter.setView(this)
        model.presenter = loginPresenter
        presenter = loginPresenter
    }

    override fun hasUser(): Boolean {
        return authenticator.currentUser != null
    }

    override fun getEmail(): String {
        return emailTextInput.editText?.text.toString()
    }

    override fun getName(): String {
        return nameTextInput.editText?.text.toString()
    }

    override fun getPassword(): String {
        return passwordTextInput.editText?.text.toString()
    }

    override fun showSignInView() {
        emailTextInput.visibility = View.VISIBLE
        nameTextInput.visibility = View.GONE
        passwordTextInput.visibility = View.VISIBLE
        signUpWithEmailBtn.visibility = View.GONE
        signInWithEmailBtn.visibility = View.VISIBLE
    }

    override fun showSignUpView() {
        emailTextInput.visibility = View.VISIBLE
        nameTextInput.visibility = View.VISIBLE
        passwordTextInput.visibility = View.VISIBLE
        signUpWithEmailBtn.visibility = View.VISIBLE
        signInWithEmailBtn.visibility = View.GONE
    }

    override fun startSignUpActivity(email: String, password: String) {
        authenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, { task ->
                    if (task.isSuccessful) {
                        val user = authenticator.currentUser
                        user?.let {
                            presenter.onSignUpSuccess(it)
                        }
                    } else {
                        presenter.onSignInError(task.exception?.message.orEmpty())
                    }
                })
    }

    override fun startSignInActivity(email: String, password: String) {
        authenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, { task ->
                    if (task.isSuccessful) {
                        val user = authenticator.currentUser
                        user?.let {
                            presenter.onSignInSuccess(it)
                        }
                    } else {
                        presenter.onSignInError(task.exception?.message.orEmpty())
                    }
                })
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
