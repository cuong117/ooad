package com.myapp.thanso.screen.createReport

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.itextpdf.io.font.PdfEncodings
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Text
import com.myapp.thanso.R
import com.myapp.thanso.databinding.CreateReportFragmentBinding
import com.myapp.thanso.util.PersonalIndexHelper
import java.io.File
import java.io.IOException
import java.util.Calendar

@SuppressLint("ClickableViewAccessibility")
class CreateReportFragment : Fragment() {
    private val binding by lazy { CreateReportFragmentBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.createBut.setOnClickListener { onCreateButtonClick() }
        binding.createLayout.setOnClickListener { onHideSoftKeyBoard() }
        setTextChangeListener(binding.firstName)
        setTextChangeListener(binding.lastName)
        setTextChangeListener(binding.birthDay)
        binding.birthDay.editText?.showSoftInputOnFocus = false
        binding.birthDay.editText?.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    showDatePickerDialog()
                }
            }
        binding.birthDay.editText?.setOnClickListener {
            showDatePickerDialog()
        }
        binding.birthDay.setEndIconOnClickListener {
            showDatePickerDialog()
        }
        return binding.root
    }

    private fun onCreateButtonClick() {
        val validateFirstName = validate(binding.firstName)
        val validateLastName = validate(binding.lastName)
        val validateBirthDay = validate(binding.birthDay, checkNumber = false)
        if (validateFirstName && validateLastName && validateBirthDay) {
            createReport(
                firstName = binding.firstName.editText?.text.toString(),
                lastName = binding.lastName.editText?.text.toString(),
                birthDay = binding.birthDay.editText?.text.toString(),
            )
        }
    }

    private fun onHideSoftKeyBoard() {
        val inputMng: InputMethodManager =
            context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMng.hideSoftInputFromWindow(activity?.currentFocus?.windowToken, 0)
    }

    private fun validate(textInput: TextInputLayout, checkNumber: Boolean = true): Boolean {
        if (textInput.editText?.text?.isNotBlank() == false) {
            textInput.error = context?.getString(R.string.not_empty_field)
            return false
        } else if (checkNumber && textInput.editText?.text?.contains("[0-9]".toRegex()) == true) {
            textInput.error = context?.getString(R.string.only_text_feild)
            return false
        }
        return true
    }

    private fun setTextChangeListener(textInput: TextInputLayout) {
        textInput.editText?.doOnTextChanged { text, _, _, _ ->
            if (text?.isNotBlank() == true) {
                textInput.error = null
                textInput.isErrorEnabled = false
            }
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val onDataset =
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthDay.editText?.setText(
                    getString(
                        R.string.date_format_ddmmyyyy,
                        dayOfMonth.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                )
            }
        context?.let {
            val datePicker = DatePickerDialog(
                it,
                R.style.CalendarDialog,
                onDataset,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
    }

    private fun createReport(firstName: String, lastName: String, birthDay: String) {
        val directory = context?.getDir("report", Context.MODE_APPEND)
        var file = File(directory, "$firstName $lastName.pdf")
        var index = 0
        while (file.exists()) {
            file = File(directory, "$firstName $lastName ($index).pdf")
            index++
        }
        val text = PersonalIndexHelper(firstName, lastName, birthDay).create()
        val pdfWriter = PdfWriter(file)
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)
        var font: PdfFont? = null
        context?.assets?.apply {
            try {
                val inputStream = open(FONT)
                val bytes = inputStream.readBytes()
                font = PdfFontFactory.createFont(
                    bytes,
                    PdfEncodings.IDENTITY_H
                )
            } catch (e: IOException) {
                Toast.makeText(context, "Không thể tạo file", Toast.LENGTH_SHORT).show()
                return
            }
        }
        if (font == null) {
            return
        }
        val textView = Text(text.toString()).setFont(font).setBold()
        val paragraph = Paragraph()
        paragraph.add(textView)
        document.add(paragraph)
        document.close()
        Toast.makeText(context, "Tạo thành công!", Toast.LENGTH_SHORT).show()
        activity?.onBackPressed()
        Log.v("tag111", "dir: ${file.absolutePath}")
    }

    companion object {
        const val A4_WIDTH = 595
        const val A4_HEIGHT = 842
        const val HORIZONTAL_SPACING = 20f
        const val VERTICAL_SPACING = 25f
        const val TEXT_SIZE = 16f
        const val FONT1 = "font/productsans_black.ttf"
        const val FONT = "font/Arial.ttf"
    }
}
