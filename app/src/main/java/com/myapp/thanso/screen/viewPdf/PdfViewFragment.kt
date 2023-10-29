package com.myapp.thanso.screen.viewPdf

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myapp.thanso.databinding.PdfViewFragmentBinding

class PdfViewFragment(
    private val uri: Uri
): Fragment() {
    private val binding by lazy { PdfViewFragmentBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.pdfView.fromUri(uri).load()
    }
}
