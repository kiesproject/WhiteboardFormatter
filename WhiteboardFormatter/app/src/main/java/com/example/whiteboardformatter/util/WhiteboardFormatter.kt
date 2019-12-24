package com.example.whiteboardformatter.util

//import android.app.Application
//import android.content.Context
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.data.repository.FoodStuffRepository
//import java.lang.RuntimeException
//
//class HealthManagementApp : Application() {
//    val repository: FoodStuffRepository
//        get() = ServiceLoader.provideFoodStuffRepository(
//            this
//        )
//    override fun onCreate() {
//        super.onCreate()
//        MyContext.onCreateAppcalition(this)
//    }
//}
//
//class MyContext(application :Context){
//    var applicationContext = application
//    companion object{
//        private lateinit var instance:MyContext
//        fun onCreateAppcalition(applicationContext:Context){
//            instance = MyContext(applicationContext)
//        }
//        fun getContext():Context{
//            if(instance.applicationContext==null)
//                throw RuntimeException()
//            return instance.applicationContext
//        }
//        fun getString(resId:Int):String{
//            return instance.applicationContext.getString(resId)
//        }
//
//    }
//}