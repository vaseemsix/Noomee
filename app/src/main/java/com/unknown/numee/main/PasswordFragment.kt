package com.unknown.numee.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.unknown.numee.R
import com.unknown.numee.switcher.SwitcherActivity
import com.unknown.numee.util.widget.ButtonView


class PasswordFragment : Fragment() {

    companion object {

        fun newInstance(): PasswordFragment {
            val passwordFragment = PasswordFragment()
            return passwordFragment
        }
    }

    private lateinit var passwordEditView: EditText
    private lateinit var submitBtnView: ButtonView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        passwordEditView = view.findViewById(R.id.fragment_password__edt_pass)
        submitBtnView = view.findViewById(R.id.fragment_password__btn_submit)

        submitBtnView.setOnClickListener {
            if (checkPassword()) {
                startSwitcherActivity()
                activity?.finish()
            } else {
                Toast.makeText(activity, R.string.wrong_password, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun checkPassword(): Boolean {
        val enteredPassword = passwordEditView.text.toString()

        val result = if (enteredPassword.isNotEmpty()) {
            true
        } else {
            false
        }

        return result
    }

    private fun startSwitcherActivity() {
        context?.let {
            SwitcherActivity.startActivity(it)
        }
    }
}