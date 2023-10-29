package com.myapp.thanso.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<B : ViewBinding, D>(viewBinding: B) :
    RecyclerView.ViewHolder(viewBinding.root) {
    abstract fun bindData(data: D)
}
