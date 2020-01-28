package com.example.whiteboardformatter.list_page

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.whiteboardformatter.data.model.TextForEdit
import com.example.whiteboardformatter.databinding.FragmentListBinding
import com.example.whiteboardformatter.util.EventObserver
import com.example.whiteboardformatter.util.getViewModelFactory

class ListFragment : Fragment(){
    private val viewModel : ListViewModel by viewModels { getViewModelFactory() }
    private lateinit var fragmentListBinding:FragmentListBinding

    companion object {
        const val CAMERA_REQUEST_CODE = 1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentListBinding = FragmentListBinding.inflate(inflater,container,false).apply {
            viewModel = this@ListFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return fragmentListBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentListBinding.listMoveToCameraFab.setOnClickListener {
            takePicture()
        }

        //読み込み成功
        viewModel.readText.observe(viewLifecycleOwner, Observer {
            if(it != null){
                if(it.isNotEmpty()){
                    navigateToEditFragment(it)
                }
            }
        })

        //読み込みに失敗したとき
        viewModel.failedToastEvent.observe(viewLifecycleOwner,EventObserver{
            if(it){
                Toast.makeText(view.context,"テキストの読み込みに失敗しました",Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.extras?.get("data")?.let {
                viewModel.getTextByFirebase(it as Bitmap)
            }
        }
    }
    private fun takePicture() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
        }

        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    private fun navigateToEditFragment(texts : Array<TextForEdit>){
        val action = ListFragmentDirections
            .actionListFragmentToEditFragment(texts)
        findNavController().navigate(action)

    }
}