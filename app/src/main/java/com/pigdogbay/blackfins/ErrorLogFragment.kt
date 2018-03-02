package com.pigdogbay.blackfins


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pigdogbay.blackfins.model.Injector
import com.pigdogbay.blackfins.presenters.ErrorLogPresenter
import com.pigdogbay.blackfins.presenters.IErrorLogView
import kotlinx.android.synthetic.main.fragment_error_log.*

class ErrorLogFragment : Fragment(), IErrorLogView {
    companion object {
        const val TAG = "error log"
    }

    private lateinit var presenter: ErrorLogPresenter
    private lateinit var adapter: ListItemRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_error_log, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = ErrorLogPresenter(Injector.liveDataLog)
        presenter.view = this
        adapter = ListItemRecyclerViewAdapter()
        adapter.log = presenter.createData()
        list.adapter = adapter
        list.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
    }

    override val getErrorIconId = R.drawable.ic_warning_black_24dp
}
