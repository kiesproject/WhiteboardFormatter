package com.example.whiteboardformatter.edit_page

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment(), View.OnTouchListener {

    private lateinit var fragmentEditBinding: FragmentEditBinding
    private lateinit var globalLayoutListener: OnGlobalLayoutListener

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

        val textView1 = setText(view, "Hello", 100F,100, 100)
        val textView2 = setText(view, "World", 150F,500, 500)

        textView1.setOnTouchListener(this)
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        // x,y 位置取得
        val newDx = event.rawX.toInt()
        val newDy = event.rawY.toInt()
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                // ACTION_MOVEでの位置
                // performCheckを入れろと警告が出るので
                v.performClick()
                val dx = image_view!!.left + (newDx - preDx)
                val dy = image_view!!.top + (newDy - preDy)
                val imgW = dx + image_view!!.width
                val imgH = dy + image_view!!.height
                // 画像の位置を設定する
                image_view!!.layout(dx, dy, imgW, imgH)
                val str = "dx=$dx\ndy=$dy"
                textView!!.text = str
                Log.d("onTouch", "ACTION_MOVE: dx=$dx, dy=$dy")
            }
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_UP -> {
            }
            else -> {
            }
        }
        // タッチした位置を古い位置とする
        preDx = newDx
        preDy = newDy
        return true
    }

    private fun setText(view: View, text: String, textSize: Float, textLeft: Int, textTop: Int) : TextView{
        val textView = TextView(view.context).also {
            it.id = View.generateViewId()
            it.text = text
            it.textSize = textSize
            edit_parent_layout.addView(it)
        }

        globalLayoutListener = OnGlobalLayoutListener {
            textView.layout(textLeft, textTop, textLeft + textView.width, textTop + textView.height)
            textView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }
        textView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

        return textView
    }

    companion object {
        private const val TAG = "EditFragment"
    }
}
