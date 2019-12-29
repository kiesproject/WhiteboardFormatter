package com.example.whiteboardformatter.edit_page

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditViewModel() : ViewModel(){

    var title = MutableLiveData<String>()
    var memo = MutableLiveData<String>()
    private var _fabCheckable = MutableLiveData<Boolean>(false)
    val fabCheckable: LiveData<Boolean> = _fabCheckable

    fun validation(){

    }

    fun onFabClicked(){
        _fabCheckable.value?.let {

        }
    }
}