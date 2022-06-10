package com.iyke.aloud.viewmodel

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iyke.aloud.model.PdfFile


class FragMswordViewModel(application: Application) : AndroidViewModel(application) {

    val context = getApplication<Application>().applicationContext
    private var REQUEST_PERMISSIONS = 1
    private var permission = false
    var TAG = "FragMswordViewModel"
    private var pdfArrayList: MutableLiveData<List<PdfFile>>? = null


    fun getPdfFiles(): MutableLiveData<List<PdfFile>>? {
        if (pdfArrayList == null) {
            pdfArrayList = MutableLiveData<List<PdfFile>>()
            getPdfList()
        }
        return pdfArrayList
    }


    private fun getPdfList() {
        val pdfList: ArrayList<PdfFile> = ArrayList()

        val projection = arrayOf(
            MediaStore.Files.FileColumns.DISPLAY_NAME,
            MediaStore.Files.FileColumns.DATE_ADDED,
            MediaStore.Files.FileColumns.DATA,
            MediaStore.Files.FileColumns.MIME_TYPE
        )
        val sortOrder = MediaStore.Files.FileColumns.DATE_ADDED + " DESC"
        val selection = MediaStore.Files.FileColumns.MIME_TYPE + " = ?"
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf")
        val selectionArgs = arrayOf(mimeType)
        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Files.getContentUri("external")
        }

        context.contentResolver.query(collection, projection, selection, selectionArgs, sortOrder)
            .use { cursor ->
                assert(cursor != null)
                if (cursor!!.moveToFirst()) {
                    val columnData: String =
                        cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA).toString()
                    val columnName: String =
                        cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME).toString()
                    val columnDate: String =
                        cursor.getColumnIndex(MediaStore.Files.FileColumns.DATE_ADDED).toString()
                    val columnSize: String =
                        cursor.getColumnIndex(MediaStore.Files.FileColumns.SIZE).toString()
                    do {
                        pdfList.add(PdfFile(columnName, columnSize, columnDate, "", columnData))
                        //you can get your pdf files
                    } while (cursor.moveToNext())
                }
            }
        pdfArrayList!!.value = pdfList
    }

    private fun isPermissionAvailable() {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    context as Activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
            } else {
                ActivityCompat.requestPermissions(
                    context, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS
                )
            }
        } else {
            permission = true
            getPdfFiles()
        }
    }
}