package com.example.jetreader.screens.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.jetreader.components.FABContent
import com.example.jetreader.components.ReaderAppBar
import com.example.jetreader.navigation.ReaderScreens
import com.example.jetreader.screens.home.HomeContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Search Book", icon = Icons.Default.ArrowBack, navController = navController, showProfile = false){
            navController.navigate(ReaderScreens.HomeScreen.name)
        }
    }, floatingActionButton = {
        FABContent{

        }
    }) {
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {

        }
    }
}