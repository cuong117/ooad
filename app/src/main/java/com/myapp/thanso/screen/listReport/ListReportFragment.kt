package com.myapp.thanso.screen.listReport

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.myapp.thanso.MainActivity
import com.myapp.thanso.R
import com.myapp.thanso.databinding.ListReportFragmentBinding
import com.myapp.thanso.model.FilePdf
import com.myapp.thanso.screen.createReport.CreateReportFragment
import com.myapp.thanso.screen.listReport.adapter.ListReportAdapter
import com.myapp.thanso.screen.viewPdf.PdfViewFragment
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class ListReportFragment: Fragment() {
    private val binding by lazy {
        ListReportFragmentBinding.inflate(layoutInflater)
    }

    private val adapter by lazy { ListReportAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) : View {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbar)
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        setHasOptionsMenu(true)
        binding.refresh.setOnRefreshListener {
            getListFile()
            binding.refresh.isRefreshing = false
        }
        adapter.setOnClick(::onItemClick)
        getListFile()
        binding.listReport.adapter = adapter
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_report_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.addItem) {
            (activity as? MainActivity)?.addFragment(CreateReportFragment())
        }
        return true
    }

    private fun getListFile() {
        val directory = context?.getDir("report", Context.MODE_PRIVATE)
        val files = directory?.listFiles()
        val data = mutableListOf<FilePdf>()
        val dateFormat = SimpleDateFormat("dd/MM/yyyy")
        files?.forEach { file ->
            data.add(
                FilePdf(
                    fileName = file.name,
                    createAt = dateFormat.format(Date(file.lastModified())),
                    uri = file.toUri()
                )
            )
        }
        Log.v("tag111", "data: $data")
        Log.v("tag111", "data1: $files")
        adapter.submitList(data)
    }

    private fun onItemClick(uri: Uri) {
        Log.v("tag111", "data: $uri")
        (activity as? MainActivity)?.addFragment(PdfViewFragment(uri), addToBackStack = true)
    }
}
