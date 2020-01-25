package com.example.whiteboardformatter.list_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.whiteboardformatter.databinding.FragmentListBinding
import com.example.whiteboardformatter.util.getViewModelFactory

class ListFragment : Fragment(){
    private val viewModel : ListViewModel by viewModels { getViewModelFactory() }
    private lateinit var fragmentListBinding:FragmentListBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentListBinding = FragmentListBinding.inflate(inflater,container,false).apply {
            viewModel = this@ListFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return fragmentListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun navigateToEditFragment(){

    }
}