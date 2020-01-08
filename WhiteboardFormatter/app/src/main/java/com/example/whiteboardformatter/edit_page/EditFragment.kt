package com.example.whiteboardformatter.edit_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import kotlinx.android.synthetic.main.fragment_edit.*


class EditFragment : Fragment(), View.OnTouchListener {

    private lateinit var fragmentEditBinding: FragmentEditBinding
    private lateinit var globalLayoutListener: OnGlobalLayoutListener

    private var oldX:Int = 0
    private var oldY:Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEditBinding = FragmentEditBinding.inflate(inflater, container, false)

        return fragmentEditBinding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView1 = setText(view, "Hello", 100F,100, 100)
        val textView2 = setText(view, "World", 150F,500, 500)

        textView1.setOnTouchListener(this)
        textView2.setOnTouchListener(this)
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        val newX = event.rawX.toInt()
        val newY = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                view.performClick()
                val textX = view.left + (newX - oldX)
                val textY = view.top + (newY - oldY)
                val textWidth = textX + view.width
                val textHeight = textY + view.height
                view.layout(textX, textY, textWidth, textHeight)
            }
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }

        oldX = newX
        oldY = newY
        return true
    }

    private fun setText(view: View, text: String, textSize: Float, textX: Int, textY: Int) : TextView{
        val textView = TextView(view.context).also {
            it.id = View.generateViewId()
            it.text = text
            it.textSize = textSize
            edit_parent_layout.addView(it)
        }

        globalLayoutListener = OnGlobalLayoutListener {
            textView.layout(textX, textY, textX + textView.width, textY + textView.height)
            textView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }
        textView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

        return textView
    }

    companion object {
        private const val TAG = "EditFragment"
    }
}
