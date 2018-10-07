package com.unknown.numee.parent.template

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.unknown.numee.R
import com.unknown.numee.base.BaseActivity
import com.unknown.numee.main.MainActivity


class TemplateActivity : BaseActivity(), ViewContract.View {

    companion object {

        fun startActivity(context: Context) {
            val intent = Intent(context, TemplateActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)

        initPresenter()
    }

    private lateinit var presenter: ViewContract.Listener
    private lateinit var templatesRecyclerView: RecyclerView
    private lateinit var templateAdapter: TemplateAdapter

    override fun initViews() {
        templatesRecyclerView = findViewById(R.id.templatesRecyclerView)
        templatesRecyclerView.layoutManager = LinearLayoutManager(this)

        templateAdapter = TemplateAdapter(object : TemplateAdapter.OnClickListener {
            override fun onItemClicked(item: String) {
                presenter.onTemplateItemClicked(item)
            }
        })

        templatesRecyclerView.adapter = templateAdapter
    }

    private fun initPresenter() {
        val templateModel = TemplateModel(this)
        val templatePresenter = TemplatePresenter(templateModel)
        templatePresenter.setView(this)
        templateModel.presenter = templatePresenter
        presenter = templatePresenter
        presenter.onCreate()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun updateTemplatesList(templateNamesList: List<String>) {
        templateAdapter.setItemList(templateNamesList)
        templateAdapter.notifyDataSetChanged()
    }

    override fun startMainActivity() {
        finish()
        MainActivity.startActivity(this)
    }
}