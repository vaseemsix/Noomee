package com.unknown.numee.parent.template

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.unknown.numee.R

class TemplateFragment : Fragment(), ViewContract.View {

    companion object {

        @JvmStatic
        fun newInstance(): Fragment {
            val templateFragment = TemplateFragment()
            templateFragment.arguments = Bundle()
            return templateFragment
        }
    }

    private lateinit var presenter: ViewContract.Listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()
        presenter.onCreate()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_template, container, false)
    }

    private fun initPresenter() {
        val templateModel = TemplateModel(context!!)
        val templatePresenter = TemplatePresenter(templateModel)
        templatePresenter.setView(this)
        templateModel.presenter = templatePresenter
        presenter = templatePresenter
    }
}
