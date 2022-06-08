package com.iyke.aloud.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iyke.aloud.R
import com.iyke.aloud.databinding.FragmentMswordBinding

class FragmentMsWord : Fragment() {

    private var _binding: FragmentMswordBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMswordBinding.inflate(inflater, container, false)
        val view = binding.root
        return view    }
}