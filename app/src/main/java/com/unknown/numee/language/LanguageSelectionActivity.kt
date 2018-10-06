package com.unknown.numee.language

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.login.LoginActivity
import com.unknown.numee.util.Preferences
import com.unknown.numee.util.widget.SpinnerView

class LanguageSelectionActivity : BaseActivity(), ViewContract.View {

    private lateinit var languageSpinnerView: SpinnerView

    private lateinit var presenter: ViewContract.Listener

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, LanguageSelectionActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)

        Preferences.initialize(this)

        initLayout()
        initPresenter()
    }

    private fun initLayout() {
        languageSpinnerView = findViewById(R.id.activity_language_selection__select_language)
    }

    private fun initPresenter() {
        val model = LanguageSelectionModel(this)
        val intPresenter = LanguageSelectionPresenter(model)
        intPresenter.setView(this)
        presenter = intPresenter
        presenter.onCreate()
    }

    override fun setLanguages(items: Array<String>) {
        languageSpinnerView.setItems(items)
    }

    override fun startLoginActivity() {
        finish()
        LoginActivity.startActivity(this@LanguageSelectionActivity)
    }

    override fun setupLanguageSelectListener() {
        languageSpinnerView.setOnItemClickListener(object : SpinnerView.OnItemClickListener {
            override fun onItemClicked(position: Int) {
                presenter.onLanguageSelected(position)
            }
        })
    }

    override fun onSaveClicked(view: View) {
        presenter.checkIfLanguageAvailable()
        startLoginActivity()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
