package com.example.whiteboardformatter.edit_page

import android.annotation.SuppressLint
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.whiteboardformatter.data.model.TextForEdit
import com.example.whiteboardformatter.data.model.TextForPreview
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import com.example.whiteboardformatter.util.getViewModelFactory
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlin.math.max
import kotlin.math.min


class EditFragment : Fragment(),
    View.OnTouchListener/*, ViewTreeObserver.OnWindowFocusChangeListener*/ {
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

    private val naturalTextWidthArrayList = ArrayList<Int>()

    private val args: EditFragmentArgs by navArgs()


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

//        仮置きデータ
        val textForEditArray = arrayOf(
            TextForEdit("H", 0, 0),
            TextForEdit("E", 0, 0),
            TextForEdit("l", 0, 0),
            TextForEdit("HelloWorld", 0, 0)
        )

//        val textForEditArray = args.textForEdit

        val textSize = getTextSize(textForEditArray)
        Log.d(TAG, "返り値 $textSize")

        textForEditArray.forEach {
            textViewArrayList.add(setText(view, it.text, textSize, it.x, it.y).also { view ->
                view.setOnTouchListener(this)
            })
        }

        if(naturalTextWidthArrayList.isEmpty()){
            Log.d(TAG, "Empty")
        }else{
            naturalTextWidthArrayList.forEach {
                Log.d(TAG, "width is $it")
            }
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

    private fun getTextSize(texts: Array<TextForEdit>): Float{
        val size = Rect()
        activity!!.window.decorView.getWindowVisibleDisplayFrame(size)
        size.width()
        Log.d(TAG, "画面横 : ${size.width()} / 画面縦 : ${size.height()}")
        //最も文字数の多い行を探す。
        val maxLength = texts.maxBy{ it.text.length}!!.text.length
        Log.d(TAG, "文字数 : $maxLength")
        //使用可能な領域(ScreenSizeの80%)/文字数 = 1文字当たりのpixel(textSize)
        val textSizeByWidth = (size.width() * 0.8 / maxLength)
        //行の数
        val numberOfTexts = texts.size
        Log.d(TAG, "行数 : $numberOfTexts")
        //使用可能な領域 / 文字数*1.3(行の間隔) = 1文字当たりのpixel(縦方向)
        val textSizeByHeight = size.height() * 0.8 /(numberOfTexts*1.3)
        Log.d(TAG, "sizeWidth : $textSizeByWidth / sizeHeight : $textSizeByHeight")
        Log.d(TAG, "小さい方 : ${min(textSizeByWidth,textSizeByHeight)}")

        return min(textSizeByWidth,textSizeByHeight).toFloat()
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
        textSize: Float,
        textX: Int,
        textY: Int
    ): TextView {

        lateinit var globalLayoutListener: OnGlobalLayoutListener

        val textView = TextView(view.context).also {
            it.id = View.generateViewId()
            it.text = text
            it.textSize = textSize
            edit_parent_layout.addView(it)
        }


        globalLayoutListener = OnGlobalLayoutListener {
            Log.d(TAG, "${textView.text} : ${textView.width}")
            naturalTextWidthArrayList.add(textView.width)
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
            .actionEditFragmentToSaveFragment(texts)
        findNavController().navigate(action)
    }
}
