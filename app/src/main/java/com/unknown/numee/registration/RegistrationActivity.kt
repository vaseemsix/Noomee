package com.unknown.numee.registration

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TextInputLayout
import android.widget.Button
import com.unknown.numee.MainActivity
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.util.widget.SpinnerView

class RegistrationActivity : BaseActivity(), ViewContract.View {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, RegistrationActivity::class.java)
            context.startActivity(intent)
        }
    }

    private lateinit var childName: TextInputLayout
    private lateinit var childAge: TextInputLayout
    private lateinit var childDiagnose: SpinnerView
    private lateinit var saveBtn: Button

    private lateinit var presenter: ViewContract.Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        initReferences()
        initPresenter()

        presenter.onCreate()
    }

    override fun setDiagnoses(items: Array<String>) {
        childDiagnose.setItems(items)
    }

    override fun startMainActivity() {
        finish()
        MainActivity.startActivity(this)
    }

    private fun initReferences() {
        childName = findViewById(R.id.activity_registration__edit_child_name)
        childAge = findViewById(R.id.activity_registration__edit_child_age)
        childDiagnose = findViewById(R.id.activity_registration__edit_diagnose)
        saveBtn = findViewById(R.id.activity_registration__btn_save)

        childDiagnose.setOnItemClickListener(object : SpinnerView.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                presenter.onDiagnoseSelected(position)
            }
        })
        saveBtn.setOnClickListener { presenter.onSaveClicked() }
    }

    private fun initPresenter() {
        val model = RegistrationModel(this)
        val registrationPresenter = RegistrationPresenter(model)
        registrationPresenter.setView(this)
        model.presenter = registrationPresenter
        presenter = registrationPresenter
    }
}
