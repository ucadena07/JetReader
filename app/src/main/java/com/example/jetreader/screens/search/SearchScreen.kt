package com.example.jetreader.screens.search

import android.renderscript.ScriptGroup.Input
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jetreader.components.FABContent
import com.example.jetreader.components.InputField
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
    }) { it ->
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            SearchForm(modifier = Modifier.fillMaxWidth().padding(16.dp)){ search ->
                Log.d("FB",search)
            }

        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(modifier: Modifier = Modifier,
               loading:Boolean = false,
               hint: String = "Search",
               onSearch:(String) -> Unit = {}){
    Column {
        val searchQueryState = rememberSaveable{
            mutableStateOf("")
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value){
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(
            valueState = searchQueryState,
            labelId = "Search",
            enabled = true,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onSearch(searchQueryState.value.trim())
                searchQueryState.value = ""
                keyboardController?.hide()
            }
        )

    }

}