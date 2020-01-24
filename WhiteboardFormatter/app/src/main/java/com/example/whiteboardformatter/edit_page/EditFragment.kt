package com.example.whiteboardformatter.edit_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import com.example.whiteboardformatter.util.getViewModelFactory

class EditFragment:Fragment(){
    private val viewModel : EditViewModel by viewModels { getViewModelFactory() }
    private lateinit var fragmentEditBinding:FragmentEditBinding



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentEditBinding = FragmentEditBinding.inflate(inflater,container,false).apply {
            viewModel = this@EditFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return fragmentEditBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    private fun navigateToSaveFragment(){

    }

}
