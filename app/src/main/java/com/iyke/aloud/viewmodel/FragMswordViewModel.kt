package com.iyke.aloud.viewmodel

import android.app.Application
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.webkit.MimeTypeMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


class FragMswordViewModel(application: Application) : AndroidViewModel(application) {

     val context = getApplication<Application>().applicationContext

    var TAG = "FragMswordViewModel"
    private var pdfArrayList: MutableLiveData<List<String>>? = null


    fun getShoppingList(): MutableLiveData<List<String>>? {
        if (pdfArrayList == null) {
            pdfArrayList = MutableLiveData<List<String>>()
            getPdfList()
        }
        return pdfArrayList
    }


     private fun getPdfList(){
        val pdfList: ArrayList<String> = ArrayList()

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
                    val columnData: Int = cursor.getColumnIndex(MediaStore.Files.FileColumns.DATA)
                    val columnName: Int = cursor.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME)
                    do {
                        pdfList.add(cursor.getString(columnData))
                        Log.d(TAG, "getPdf: " + cursor.getString(columnData))
                        //you can get your pdf files
                    } while (cursor.moveToNext())
                }
            }
         pdfArrayList!!.value = pdfList
    }
}