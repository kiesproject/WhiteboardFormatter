package com.example.whiteboardformatter.save_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.whiteboardformatter.databinding.FragmentSaveBinding
import com.example.whiteboardformatter.util.getViewModelFactory

class SaveFragment :Fragment(){
    private val viewModel:SaveViewModel by viewModels { getViewModelFactory() }
    private lateinit var fragmentSaveBinding: FragmentSaveBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentSaveBinding = FragmentSaveBinding.inflate(inflater,container).apply {
            viewModel = this@SaveFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return fragmentSaveBinding.root
    }
}