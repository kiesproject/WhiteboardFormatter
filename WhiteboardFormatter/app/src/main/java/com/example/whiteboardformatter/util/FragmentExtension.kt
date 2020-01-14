package com.example.whiteboardformatter.util

import androidx.fragment.app.Fragment
import com.example.whiteboardformatter.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository =
        ServiceLoader.provideRepository(requireContext())
    return ViewModelFactory(repository)
}