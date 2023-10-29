package com.myapp.thanso.model

import android.net.Uri
import androidx.recyclerview.widget.DiffUtil
import com.myapp.thanso.util.DiffAble

data class FilePdf(
    val fileName: String,
    val createAt: String,
    val uri: Uri
): DiffAble {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FilePdf>() {
            override fun areItemsTheSame(oldItem: FilePdf, newItem: FilePdf): Boolean {
                return oldItem.fileName == newItem.fileName
            }

            override fun areContentsTheSame(oldItem: FilePdf, newItem: FilePdf): Boolean {
                return oldItem == newItem
            }

        }
    }
}
