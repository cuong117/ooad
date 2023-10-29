package com.myapp.thanso.screen.listReport.adapter

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.myapp.thanso.base.BaseAdapter
import com.myapp.thanso.base.BaseViewHolder
import com.myapp.thanso.databinding.ItemReportBinding
import com.myapp.thanso.model.FilePdf

class ListReportAdapter :
    BaseAdapter<FilePdf>(diffUtil = FilePdf.DIFF_CALLBACK) {

    private var onItemClick: ((Uri) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListReportViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ListReportViewHolder(
            viewBinding = ItemReportBinding.inflate(layoutInflater, parent, false)
        )
    }

    fun setOnClick(onItemClick: (Uri) -> Unit) {
        this.onItemClick = onItemClick
    }

    inner class ListReportViewHolder(private val viewBinding: ItemReportBinding) :
        BaseViewHolder<ViewBinding, FilePdf>(viewBinding) {
        private var data : FilePdf? = null
        init {
            viewBinding.root.setOnClickListener {
                Log.v("tag111", "onclick $data")
                data?.let {
                    onItemClick?.invoke(it.uri)
                }
            }
        }

        override fun bindData(data: FilePdf) {
            viewBinding.name.text = data.fileName
            viewBinding.dateCreate.text = data.createAt
            this.data = data
        }
    }
}
