package com.example.whiteboardformatter.save_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.whiteboardformatter.databinding.FragmentSaveBinding
import com.example.whiteboardformatter.util.getViewModelFactory
import io.noties.markwon.Markwon

class SaveFragment :Fragment(){
    private val viewModel:SaveViewModel by viewModels { getViewModelFactory() }
    private lateinit var fragmentSaveBinding: FragmentSaveBinding

    private val args : SaveFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentSaveBinding = FragmentSaveBinding.inflate(inflater,container,false).apply {
            viewModel = this@SaveFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return fragmentSaveBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val markwon = Markwon.create(view.context)
        setMdText(markwon)

        //仮置きデータ
//        val textArray = arrayOf(
//            TextForPreview("最初のテキスト",1000,0,100,200,2.2f,2.2f)
//        )

        val textArray = args.textForPreview

        viewModel.start(textArray)

    }
    private fun setMdText(markwon:Markwon){
        viewModel.previewMdText.observe(this.viewLifecycleOwner, Observer {
//            val testMd = "## aaaaaa\nbbbbbbb"
            val markdown = markwon.toMarkdown(it)
            markwon.setParsedMarkdown(fragmentSaveBinding.saveMdPreviewTextview,markdown)
        })
    }
}