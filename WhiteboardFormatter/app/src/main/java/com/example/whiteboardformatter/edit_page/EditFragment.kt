package com.example.whiteboardformatter.edit_page

import android.R.layout
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.whiteboardformatter.databinding.FragmentEditBinding
import kotlinx.android.synthetic.main.fragment_edit.*


class EditFragment : Fragment() {

    private lateinit var fragmentEditBinding: FragmentEditBinding

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


        val constraintSet = ConstraintSet()
        constraintSet.clone(edit_parent_layout)

        // android:layout_width="wrap_content"
        constraintSet.constrainHeight(
            textView.id,
            ConstraintSet.WRAP_CONTENT
        )

        //android:layout_height="wrap_content"
        constraintSet.constrainWidth(
            textView.id,
            ConstraintSet.WRAP_CONTENT
        )

        // app:layout_constraintBottom_toBottomOf="parent"
        constraintSet.connect(
            textView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )

        // app:layout_constraintLeft_toLeftOf="parent"
        constraintSet.connect(
            textView.id,
            ConstraintSet.LEFT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.LEFT,
            0
        )

        // app:layout_constraintRight_toRightOf="parent"
        constraintSet.connect(
            textView.id,
            ConstraintSet.RIGHT,
            ConstraintSet.PARENT_ID,
            ConstraintSet.RIGHT,
            0
        )

        // app:layout_constraintTop_toTopOf="parent"
        constraintSet.connect(
            textView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0
        )

        constraintSet.applyTo(edit_parent_layout)

    }
}
