package com.example.whiteboardformatter.list_page

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.whiteboardformatter.data.model.TextForEdit
import com.example.whiteboardformatter.data.repository.Repository
import com.example.whiteboardformatter.util.Event
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer

class ListViewModel(private val repository: Repository) : ViewModel() {

    private var _readText = MutableLiveData<Array<TextForEdit>>(null)
    val readText :LiveData<Array<TextForEdit>> = _readText

    private var _failedToastEvent = MutableLiveData<Event<Boolean>>(Event(false))
    val failedToastEvent : LiveData<Event<Boolean>> = _failedToastEvent

    fun getTextByFirebase(bitmap: Bitmap) {
        val image = FirebaseVisionImage.fromBitmap(bitmap)
        val detector = FirebaseVision.getInstance()
            .onDeviceTextRecognizer

        detector.processImage(image)
            .addOnSuccessListener {
                Log.d("Load Text", "SUCCESS")

                val result = mutableListOf<TextForEdit>()
                it.textBlocks.forEach { textBlock ->
                    textBlock.lines.forEach { line ->
                        line.cornerPoints?.let { points ->
                            result.add(TextForEdit(text = line.text,x = points[0].x,y = points[0].y))
                            Log.d(
                                "Line",
                                "( " + points[0].x.toString() + " , " + points[0].y.toString() + " )  " + line.text
                            )
                        }
                    }
                }
                _readText.value = result.toTypedArray()
            }
            .addOnFailureListener {
                _failedToastEvent.value = Event(true)
            }
    }

}