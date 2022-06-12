package com.iyke.aloud.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.iyke.aloud.PdfViewActivity
import com.iyke.aloud.adapter.PdfListAdapter
import com.iyke.aloud.databinding.FragmentMswordBinding
import com.iyke.aloud.model.PdfFile
import com.iyke.aloud.viewmodel.FragMswordViewModel
import com.iyke.aloud.viewmodel.FragMswordViewModel.Companion.REQUEST_PERMISSIONS
import com.iyke.aloud.viewmodel.FragMswordViewModel.Companion.permission

class FragmentMsWord : Fragment() {
    private var _binding: FragmentMswordBinding? = null
    private val binding get() = _binding!!
    private val viewModel by lazy {ViewModelProvider(this).get(FragMswordViewModel::class.java)}
    private lateinit var rvAdapter: PdfListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMswordBinding.inflate(inflater, container, false)
        val view = binding.root
        _binding!!.recyclerview.layoutManager = LinearLayoutManager(context)
        usingLiveData()
        return view
    }

    override fun onStart() {
        super.onStart()
        viewModel.isPermissionAvailable(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permission = true
               viewModel.getPdfFiles()

            } else {
                Toast.makeText(context, "Please allow the permission", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }
    private fun usingLiveData(){

        rvAdapter = PdfListAdapter(PdfListAdapter.OnClickListener {
            val intent = Intent(context, PdfViewActivity::class.java)
            intent.putExtra("position", it.files.name)
            startActivity(intent)
        })

        viewModel.getPdfFiles()!!.observe(viewLifecycleOwner, Observer {
            rvAdapter.submitList(it)
            binding.recyclerview.adapter = rvAdapter
        })
    }
}