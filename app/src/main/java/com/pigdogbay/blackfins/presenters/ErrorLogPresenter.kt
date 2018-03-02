package com.pigdogbay.blackfins.presenters

import com.pigdogbay.blackfins.model.ListItemData
import com.pigdogbay.blackfins.model.LiveDataLog
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by mark on 02/03/18.
 * MVP Pattern logic for displaying errors
 */
interface IErrorLogView {
    val getErrorIconId : Int
}
class ErrorLogPresenter(private val liveDataLog: LiveDataLog) {

    lateinit var view : IErrorLogView
    private val simpleDateFormat = SimpleDateFormat("HH:mm:ss     d-MMM-yyyy", Locale.UK)

    fun createData() : List<ListItemData>{
        return liveDataLog.getErrorLog()
                .map { it -> ListItemData(it.message,simpleDateFormat.format(it.date),view.getErrorIconId) }
                .reversed()
    }
}