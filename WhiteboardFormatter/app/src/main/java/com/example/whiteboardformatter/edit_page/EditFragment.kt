package com.example.whiteboardformatter.edit_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment() {

    private lateinit var fragmentEditBinding: FragmentEditBinding

    private lateinit var mGlobalLayoutListener: OnGlobalLayoutListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEditBinding = FragmentEditBinding.inflate(inflater, container, false)

        return fragmentEditBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TextView instance
        val textView = TextView(view.context)

        // Generate TextView ID
        val viewId = View.generateViewId()
        textView.id = viewId

        val str = "Hello World!"
        textView.text = str

        edit_parent_layout.addView(textView)

        mGlobalLayoutListener = OnGlobalLayoutListener {
            val lp: ViewGroup.LayoutParams = textView.layoutParams
            val mlp = lp as MarginLayoutParams
            mlp.setMargins(mlp.leftMargin, mlp.topMargin, mlp.rightMargin, mlp.bottomMargin)
            //マージンを設定
            textView.layoutParams = mlp

            textView.viewTreeObserver.removeOnGlobalLayoutListener(mGlobalLayoutListener)
        }
        textView.viewTreeObserver.addOnGlobalLayoutListener(mGlobalLayoutListener)
    }

    fun setText(text: String){

    }

}
