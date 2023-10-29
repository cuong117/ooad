package com.myapp.thanso.base

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import java.util.concurrent.Executors

abstract class BaseAdapter<T>(diffUtil: DiffUtil.ItemCallback<T>) :
    ListAdapter<T, BaseViewHolder<in ViewBinding, T>>(
        AsyncDifferConfig.Builder(
            diffUtil
        ).setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
            .build()
    ) {

    override fun onBindViewHolder(holder: BaseViewHolder<in ViewBinding, T>, position: Int) {
        holder.bindData(getItem(position))
    }
}
