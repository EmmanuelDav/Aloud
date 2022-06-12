package com.iyke.aloud.viewmodel

import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Environment
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.iyke.aloud.model.PdfFile
import java.io.File


class FragMswordViewModel(application: Application) : AndroidViewModel(application) {

    var dir: File = File(Environment.getExternalStorageDirectory().toString())

    companion object{
         var REQUEST_PERMISSIONS = 1
         var permission = false
    }
    private var pdfArrayList: MutableLiveData<ArrayList<PdfFile>>? = null


    fun getPdfFiles(): MutableLiveData<ArrayList<PdfFile>>? {
        if (pdfArrayList == null) {
            pdfArrayList = MutableLiveData<ArrayList<PdfFile>>()
        }
        getPdfList(dir)
        return pdfArrayList
    }

    private fun getPdfList(dir: File) {
        val fileList: ArrayList<PdfFile> = ArrayList()

        val listFile = dir.listFiles()
        if (listFile != null && listFile.isNotEmpty()) {
            for (i in listFile.indices) {
                if (listFile[i].isDirectory) {
                    getPdfList(listFile[i])
                } else {
                    var booleanpdf = false
                    if (listFile[i].name.endsWith(".pdf")) {
                        for (j in fileList.indices) {
                            if (fileList[j].files.name == listFile[i].name) {
                                booleanpdf = true
                            } else {
                            }
                        }
                        if (booleanpdf) {
                            booleanpdf = false
                        } else {
                            fileList.add(PdfFile(listFile[i]))
                        }
                    }
                }
            }
        }
        pdfArrayList!!.value = fileList
    }

     fun isPermissionAvailable(fragment : Fragment) {
        if (ContextCompat.checkSelfPermission(
                fragment.requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    fragment.requireActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    fragment.requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            permission = true
            getPdfFiles()
        }
    }
}