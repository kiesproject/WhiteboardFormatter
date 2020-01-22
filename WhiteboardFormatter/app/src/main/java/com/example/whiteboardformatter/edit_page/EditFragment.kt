package com.example.whiteboardformatter.edit_page

import android.annotation.SuppressLint
import android.util.Log
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import com.example.whiteboardformatter.save_page.SaveViewModel
import com.example.whiteboardformatter.util.getViewModelFactory
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlin.math.max
import kotlin.math.min


class EditFragment : Fragment(), View.OnTouchListener {
    private val viewModel : EditViewModel by viewModels { getViewModelFactory() }
    private lateinit var fragmentEditBinding: FragmentEditBinding

    private lateinit var globalLayoutListener: OnGlobalLayoutListener

    private lateinit var textView1:TextView
    private lateinit var textView2:TextView

    private var touchFlg = -1

    private var oldX: Int = 0
    private var oldY: Int = 0

    private lateinit var touchTextView: TextView

    private var scaleFactor = 1f
    private lateinit var scaleDetector: ScaleGestureDetector

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEditBinding = FragmentEditBinding.inflate(inflater,container, false).apply {
            viewModel = this@EditFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return fragmentEditBinding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView1 = setText(view, "Hello", 100F, 100, 100)

        textView2 = setText(view, "World", 150F, 500, 500)

        textView1.setOnTouchListener(this)
        textView2.setOnTouchListener(this)

        scaleDetector = ScaleGestureDetector(context, scaleListener)
    }

//    private fun navigateToSaveFragment(){
//
//    }

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
            scaleFactor = max(0.1f, min(scaleFactor, 5.0f))

            touchTextView.scaleX = scaleFactor
            touchTextView.scaleY = scaleFactor
            Log.d(TAG, touchTextView.scaleX.toString())

            return true
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)

//        touchTextView = view as TextView

        val newX = event.rawX.toInt()
        val newY = event.rawY.toInt()

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                if(touchFlg == NOTING_SELECTED){
                    touchTextView = view as TextView
                    touchFlg = touchTextView.id
                }
            }

            MotionEvent.ACTION_MOVE -> {
                if(touchFlg == touchTextView.id){
                    touchTextView.performClick()
                    val textX = touchTextView.left + (newX - oldX)
                    val textY = touchTextView.top + (newY - oldY)
                    val textWidth = textX + touchTextView.width
                    val textHeight = textY + touchTextView.height
                    view.layout(textX, textY, textWidth, textHeight)
                }
            }

            MotionEvent.ACTION_UP -> {
                touchFlg = NOTING_SELECTED
            }
            else -> {
            }
        }

        oldX = newX
        oldY = newY
        return true
    }

    private fun setText(
        view: View,
        text: String,
        textSize: Float,
        textX: Int,
        textY: Int
    ): TextView {


        val textView = TextView(view.context).also {
            it.id = View.generateViewId()
            it.text = text
            it.textSize = textSize
            edit_parent_layout.addView(it)
        }

        globalLayoutListener = OnGlobalLayoutListener {
            textView.layout(textX, textY, textX + textView.width, textY + textView.height)
            Log.d(TAG, textView.width.toString())
            textView.viewTreeObserver.removeOnGlobalLayoutListener(globalLayoutListener)
        }
        textView.viewTreeObserver.addOnGlobalLayoutListener(globalLayoutListener)

        val constraintSet = ConstraintSet()

        constraintSet.apply {
            clone(edit_parent_layout)

            constrainHeight(
                textView.id,
                ConstraintSet.WRAP_CONTENT
            )

            constrainDefaultWidth(
                textView.id,
                ConstraintSet.WRAP_CONTENT
            )

            connect(
                textView.id,
                ConstraintSet.BOTTOM,
                ConstraintSet.PARENT_ID,
                ConstraintSet.BOTTOM,
                0
            )

            connect(
                textView.id,
                ConstraintSet.LEFT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.LEFT,
                0
            )

            connect(
                textView.id,
                ConstraintSet.RIGHT,
                ConstraintSet.PARENT_ID,
                ConstraintSet.RIGHT,
                0
            )

            connect(
                textView.id,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                0
            )

            applyTo(edit_parent_layout)
        }

        return textView
    }

    companion object {
        private const val TAG = "EditFragment"
        private const val NOTING_SELECTED = -1
    }
}
