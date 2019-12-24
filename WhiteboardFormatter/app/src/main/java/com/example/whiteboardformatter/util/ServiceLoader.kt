//package com.example.whiteboardformatter.util
//
//import android.content.Context
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.data.HealthDatabase
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.data.repository.DishRepository
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.data.repository.FoodStuffRepository
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.Job
//
//object ServiceLoader {
//    private var database : HealthDatabase? = null
//    var foodStuffRepository:FoodStuffRepository? = null
//    var dishRepository:DishRepository?=null
//
//    fun provideFoodStuffRepository(context: Context):FoodStuffRepository{
//        synchronized(this) {
//            return foodStuffRepository
//                ?: createFoodStuffRepository(
//                    context
//                )
//        }
//    }
//
//    fun provideDishRepository(context:Context):DishRepository{
//        synchronized(this){
//            return dishRepository
//                ?: createDishRepository(
//                    context
//                )
//        }
//    }
//
//    private fun createFoodStuffRepository(context:Context):FoodStuffRepository{
//        val database = database
//            ?: createDatabase(
//                context
//            )
//        val result = FoodStuffRepository(database.foodStuffDao())
//        foodStuffRepository = result
//        return result
//    }
//
//    private fun createDishRepository(context:Context):DishRepository{
//        val database = database
//            ?: createDatabase(
//                context
//            )
//        val result = DishRepository(database.dishDao())
//        dishRepository = result
//        return result
//    }
//
//    private fun createDatabase(context:Context):HealthDatabase{
//        val result = HealthDatabase.getDatabase(context)
//        database =result
//        return result
//    }
//}