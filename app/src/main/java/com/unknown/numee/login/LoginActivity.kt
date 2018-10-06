package com.unknown.numee.login

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.main.MainActivity
import com.unknown.numee.registration.RegistrationActivity
import com.unknown.numee.switcher.SwitcherActivity


class LoginActivity : BaseActivity(), ViewContract.View {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var signUpWithEmailBtn: Button
    private lateinit var signInWithEmailBtn: Button
    private lateinit var signInSwitch: TextView
    private lateinit var emailTextInput: TextInputLayout
    private lateinit var nameTextInput: TextInputLayout
    private lateinit var passwordTextInput: TextInputLayout

    private lateinit var presenter: ViewContract.Listener

    private var isSignInVisible = false
    private val authenticator = FirebaseAuth.getInstance()

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
        signInSwitch = findViewById(R.id.activity_login__txt_switch)
        signUpWithEmailBtn = findViewById(R.id.activity_login__btn_sign_up_email)
        signInWithEmailBtn = findViewById(R.id.activity_login__btn_sign_in_email)
        signUpWithEmailBtn.setOnClickListener { presenter.onSignUpWithEmailClicked() }
        signInWithEmailBtn.setOnClickListener { presenter.onSignInWithEmailClicked() }
        signInSwitch.setOnClickListener { presenter.onSwitchClicked() }
    }

    private fun initPresenter() {
        val model = LoginModel(this)
        val loginPresenter = LoginPresenter(model)
        loginPresenter.setView(this)
        model.presenter = loginPresenter
        presenter = loginPresenter
    }

    override fun isSignInVisible(): Boolean {
        return isSignInVisible
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

    override fun getResource(): Resources {
        return resources
    }

    override fun showSignInView() {
        emailTextInput.visibility = View.VISIBLE
        nameTextInput.visibility = View.GONE
        passwordTextInput.visibility = View.VISIBLE
        signUpWithEmailBtn.visibility = View.GONE
        signInWithEmailBtn.visibility = View.VISIBLE
        signInSwitch.setText(R.string.sign_up_with_email)
        isSignInVisible = true
    }

    override fun showSignUpView() {
        emailTextInput.visibility = View.VISIBLE
        nameTextInput.visibility = View.VISIBLE
        passwordTextInput.visibility = View.VISIBLE
        signUpWithEmailBtn.visibility = View.VISIBLE
        signInWithEmailBtn.visibility = View.GONE
        signInSwitch.setText(R.string.sign_in_with_email)
        isSignInVisible = false
    }

    override fun setLoadingVisibility(isVisible: Boolean) {
        emailTextInput.editText?.isEnabled = !isVisible
        nameTextInput.editText?.isEnabled = !isVisible
        passwordTextInput.editText?.isEnabled = !isVisible
    }

    override fun startSignUpActivity(email: String, password: String) {
        authenticator.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = authenticator.currentUser
                        user?.let {
                            presenter.onSignUpSuccess(it)
                        }
                    } else {
                        presenter.onSignInError(task.exception?.message.orEmpty())
                    }
                }
    }

    override fun startSignInActivity(email: String, password: String) {
        authenticator.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = authenticator.currentUser
                        user?.let {
                            presenter.onSignInSuccess(it)
                        }
                    } else {
                        presenter.onSignInError(task.exception?.message.orEmpty())
                    }
                }
    }

    override fun sendEmailVerification() {
        authenticator.currentUser!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        presenter.onEmailSentSuccess()
                    } else {
                        presenter.onEmailSentFail(task.exception)
                    }
                }
    }

    override fun startRegistrationActivity() {
        finish()
        RegistrationActivity.startActivity(this)
    }

    override fun startUserSwitcherActivity() {
        finish()
        SwitcherActivity.startActivity(this)
    }

    override fun startMainActivity() {
        finish()
        MainActivity.startActivity(this)
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
