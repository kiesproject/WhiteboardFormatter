package com.example.whiteboardformatter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.whiteboardformatter.data.repository.Repository
import com.example.whiteboardformatter.edit_page.EditViewModel
import com.example.whiteboardformatter.list_page.ListViewModel
import com.example.whiteboardformatter.save_page.SaveViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ViewModelFactory (
    private val repository: Repository
):ViewModelProvider.NewInstanceFactory(){

    override fun <T: ViewModel> create(modelClass: Class<T>)=
        with(modelClass){
            when{
                isAssignableFrom(ListViewModel::class.java)->
                    ListViewModel(repository)
                isAssignableFrom(EditViewModel::class.java)->
                    EditViewModel(repository)
                isAssignableFrom(SaveViewModel::class.java)->
                    SaveViewModel(repository)
                else->
                    throw IllegalArgumentException("Unknown ViewModelclass ${modelClass}")
            }
        }  as T
}