//package com.example.whiteboardformatter
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.ViewModelProvider
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.data.repository.DishRepository
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.data.repository.FoodStuffRepository
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.dish.DishesViewModel
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.dish.addEditDish.AddEditDishViewModel
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.dish.detailDish.DetailDishViewModel
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.foodstaff.FoodStuffViewModel
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.foodstaff.addEditFoodStuff.AddEditFoodStuffViewModel
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.foodstaff.detailFoodStuff.DetailFoodStuffViewModel
//import java.lang.IllegalArgumentException
//
//@Suppress("UNCHECKED_CAST")
//class ViewModelFactory (
//    private val foodStuffRepository: FoodStuffRepository,
//    private val dishRepository: DishRepository
//):ViewModelProvider.NewInstanceFactory(){
//
//    override fun <T: ViewModel> create(modelClass: Class<T>)=
//        with(modelClass){
//            when{
//                isAssignableFrom(FoodStuffViewModel::class.java)->
//                    FoodStuffViewModel(foodStuffRepository)
//                isAssignableFrom(AddEditFoodStuffViewModel::class.java)->
//                    AddEditFoodStuffViewModel(foodStuffRepository)
//                isAssignableFrom(DetailFoodStuffViewModel::class.java)->
//                    DetailFoodStuffViewModel(foodStuffRepository)
//                isAssignableFrom(DishesViewModel::class.java)->
//                    DishesViewModel(dishRepository)
//                isAssignableFrom(AddEditDishViewModel::class.java)->
//                    AddEditDishViewModel(dishRepository)
//                isAssignableFrom(DetailDishViewModel::class.java)->
//                    DetailDishViewModel(dishRepository)
//                else->
//                    throw IllegalArgumentException("Unknown ViewModelclass ${modelClass}")
//            }
//        }  as T
//}