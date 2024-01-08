package com.example.jetreader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetreader.screens.SplashScreen
import com.example.jetreader.screens.details.BookDetailsScreen
import com.example.jetreader.screens.home.HomeScreen
import com.example.jetreader.screens.login.LoginScreen
import com.example.jetreader.screens.search.SearchScreen
import com.example.jetreader.screens.search.SearchScreenViewModelV2
import com.example.jetreader.screens.statsScreen.StatsScreen
import com.example.jetreader.screens.update.UpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name){
        composable(ReaderScreens.SplashScreen.name){
            SplashScreen(navController)
        }
        composable(ReaderScreens.LoginScreen.name){
            LoginScreen(navController)
        }
        composable(ReaderScreens.HomeScreen.name){
            HomeScreen(navController)
        }
        composable(ReaderScreens.BookDetailsScreen.name){
            BookDetailsScreen(navController)
        }
        composable(ReaderScreens.SearchScreen.name){
            val searchScreenViewModel = hiltViewModel<SearchScreenViewModelV2>()
            SearchScreen(navController,searchScreenViewModel)
        }
        composable(ReaderScreens.UpdateScreen.name){
            UpdateScreen(navController)
        }
        composable(ReaderScreens.StatsScreen.name){
            StatsScreen(navController)
        }
    }
}