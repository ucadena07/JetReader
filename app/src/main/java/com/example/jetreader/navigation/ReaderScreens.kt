package com.example.jetreader.navigation

enum class ReaderScreens {
    SplashScreen,
    HomeScreen,
    BookDetailsScreen,
    LoginScreen,
    SearchScreen,
    UpdateScreen;

    companion object {
        fun fromRoute(route: String?) : ReaderScreens
         = when(route?.substringBefore("/")){
             SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            BookDetailsScreen.name -> BookDetailsScreen
            SearchScreen.name -> SearchScreen
            UpdateScreen.name -> UpdateScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route not found.")
         }
    }
}