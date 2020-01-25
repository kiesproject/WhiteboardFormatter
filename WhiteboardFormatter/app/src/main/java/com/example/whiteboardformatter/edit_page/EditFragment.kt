package com.example.whiteboardformatter.edit_page

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.whiteboardformatter.data.model.TextForEdit
import com.example.whiteboardformatter.data.model.TextForPreview
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import com.example.whiteboardformatter.util.getViewModelFactory
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlin.math.max
import kotlin.math.min


class EditFragment : Fragment(), View.OnTouchListener {
    private val viewModel: EditViewModel by viewModels { getViewModelFactory() }
    private lateinit var fragmentEditBinding: FragmentEditBinding

    private lateinit var touchTextView: TextView
    private var touchFlg = -1

    private val textViewArrayList = ArrayList<TextView>()
    private val textForPreviewArrayList = ArrayList<TextForPreview>()
    private lateinit var scaleDetector: ScaleGestureDetector
    private var scaleFactor = 1f
    private var oldX: Int = 0
    private var oldY: Int = 0

    companion object {
        private const val TAG = "EditFragment"
        private const val NOTING_SELECTED = -1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentEditBinding = FragmentEditBinding.inflate(inflater, container, false).apply {
            viewModel = this@EditFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }
        return fragmentEditBinding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //仮置きデータ
        val textForEditArray = arrayOf(
            TextForEdit("Hello", 100, 100),
            TextForEdit("HelloWorld", 500, 500)
        )

        textForEditArray.forEach {
            textViewArrayList.add(setText(view, it.text, it.x, it.y).also { view ->
                view.setOnTouchListener(this)
            })
        }

        scaleDetector = ScaleGestureDetector(context, scaleListener)

        fragmentEditBinding.editConfirmFab.setOnClickListener {

            textViewArrayList.forEach {
                it.apply {
                    textForPreviewArrayList.add(
                        TextForPreview(
                            text = text.toString(),
                            x = x.toInt(),
                            y = y.toInt(),
                            height = height,
                            width = width,
                            scaleX = scaleX,
                            scaleY = scaleY
                        )
                    )
                }
            }
            navigateToSaveFragment(textForPreviewArrayList.toTypedArray())
        }
    }


    private val scaleListener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

        override fun onScale(detector: ScaleGestureDetector): Boolean {
            scaleFactor *= detector.scaleFactor

            // Don't let the object get too small or too large.
            scaleFactor = max(0.1f, min(scaleFactor, 5.0f))

            touchTextView.scaleX = scaleFactor
            touchTextView.scaleY = scaleFactor

            return true
        }
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        scaleDetector.onTouchEvent(event)

        if (touchFlg == view.id || touchFlg == NOTING_SELECTED) {
            val newX = event.rawX.toInt()
            val newY = event.rawY.toInt()

            when (event.action) {

                MotionEvent.ACTION_DOWN -> {
                    touchTextView = view as TextView
                    touchFlg = touchTextView.id

                }

                MotionEvent.ACTION_MOVE -> {
                    touchTextView.performClick()
                    val textX = touchTextView.left + (newX - oldX)
                    val textY = touchTextView.top + (newY - oldY)
                    val textWidth = textX + touchTextView.width
                    val textHeight = textY + touchTextView.height
                    view.layout(textX, textY, textWidth, textHeight)

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

        return false
    }

    private fun setText(
        view: View,
        text: String,
        textX: Int,
        textY: Int
    ): TextView {

        lateinit var globalLayoutListener: OnGlobalLayoutListener

        val textView = TextView(view.context).also {
            it.id = View.generateViewId()
            it.text = text
            it.textSize = 50F
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


    private fun navigateToSaveFragment(texts: Array<TextForPreview>) {
        val action = EditFragmentDirections
            .actionEditFragmentToSaveFragment(/*texts*/)
        findNavController().navigate(action)
    }
}
