package com.example.whiteboardformatter.list_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.whiteboardformatter.databinding.FragmentListBinding

class ListFragment : Fragment(){
    private lateinit var fragmentListBinding: FragmentListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentListBinding = FragmentListBinding.inflate(inflater, container, false)
        return fragmentListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}