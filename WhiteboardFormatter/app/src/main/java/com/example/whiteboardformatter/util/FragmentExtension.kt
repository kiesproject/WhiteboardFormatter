//package com.example.whiteboardformatter.util
//
//import androidx.fragment.app.Fragment
//import com.amebaownd.pikohan_nwiatori.healthmanagementapp.ViewModelFactory
//
//fun Fragment.getViewModelFactory(): ViewModelFactory {
////    val app = requireContext().applicationContext
////    val health = app as HealthManagementApp
////    val foodStuffRepository = health.foodStuffRepository
//    val foodStuffRepository =
//        ServiceLoader.provideFoodStuffRepository(requireContext())
//    val dishRepository=
//        ServiceLoader.provideDishRepository(requireContext())
//    return ViewModelFactory(foodStuffRepository,dishRepository)
//}