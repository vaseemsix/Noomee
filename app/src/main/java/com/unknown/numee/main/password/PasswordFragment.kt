package com.unknown.numee.main.password

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.unknown.numee.R
import com.unknown.numee.switcher.SwitcherActivity
import com.unknown.numee.util.widget.ButtonView


class PasswordFragment : Fragment(), ViewContract.View {

    companion object {

        fun newInstance(): PasswordFragment {
            val passwordFragment = PasswordFragment()
            return passwordFragment
        }
    }

    private lateinit var passwordEditView: EditText
    private lateinit var submitBtnView: ButtonView

    private lateinit var presenter: ViewContract.Listener

    private val authenticator = FirebaseAuth.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passwordEditView = view.findViewById(R.id.fragment_password__edt_pass)
        submitBtnView = view.findViewById(R.id.fragment_password__btn_submit)

        submitBtnView.setOnClickListener {
            presenter.onSubmitClicked(passwordEditView.text.toString())
        }

        initPresenter()
        presenter.onCreate()
    }

    override fun authenticate(email: String, password: String) {
        activity?.let {
            authenticator.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(it, { task ->
                        if (task.isSuccessful) {
                            presenter.onAuthenticateSuccess()
                        } else {
                            presenter.onAuthenticateError()
                        }
                    })
        }
    }

    override fun finish() {
        activity?.finish()
    }

    override fun showMessage() {
        Toast.makeText(activity, R.string.wrong_password, Toast.LENGTH_SHORT).show()
    }

    override fun startSwitcherActivity() {
        context?.let {
            SwitcherActivity.startActivity(it)
        }
    }

    private fun initPresenter() {
        val passwordModel = PasswordModel(activity!!)
        val passwordPresenter = PasswordPresenter(passwordModel)
        passwordPresenter.setView(this)
        passwordModel.presenter = passwordPresenter
        presenter = passwordPresenter
    }
}