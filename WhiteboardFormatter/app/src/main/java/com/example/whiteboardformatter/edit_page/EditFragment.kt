package com.example.whiteboardformatter.edit_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
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
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import kotlinx.android.synthetic.main.fragment_edit.*

class EditFragment : Fragment(), View.OnTouchListener {
    private val viewModel : EditViewModel by viewModels { getViewModelFactory() }
    private lateinit var fragmentEditBinding:FragmentEditBinding

    private lateinit var fragmentEditBinding: FragmentEditBinding
    private lateinit var globalLayoutListener: OnGlobalLayoutListener

    private lateinit var textView1:TextView

    private var oldX: Int = 0
    private var oldY: Int = 0

    private var mScaleFactor = 1f
    private lateinit var mScaleDetector:ScaleGestureDetector

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

        textView1 = setText(view, "Hello", 100F, 100, 100)

        val textView2 = setText(view, "World", 150F, 500, 500)

        textView1.setOnTouchListener(this)
        textView2.setOnTouchListener(this)

        mScaleDetector = ScaleGestureDetector(context, scaleListener)
    }

    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            mScaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
//            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f))

            textView1.setScaleX(mScaleFactor)
            textView1.setScaleY(mScaleFactor)

            return true
        }
    }

//    override fun onTouchEvent(ev: MotionEvent): Boolean {
//        // Let the ScaleGestureDetector inspect all events.
//        mScaleDetector.onTouchEvent(ev)
//        return true
//    }

//    fun onTouchEvent(event: MotionEvent?): Boolean { //re-route the Touch Events to the ScaleListener class
//        detector.onTouchEvent(event)
//        return super.onTouchEvent(event)
//    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        mScaleDetector.onTouchEvent(event)
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
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentEditBinding = FragmentEditBinding.inflate(inflater,container).apply {
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
