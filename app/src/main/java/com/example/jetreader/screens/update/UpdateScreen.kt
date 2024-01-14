package com.example.jetreader.screens.update

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.jetreader.components.FABContent
import com.example.jetreader.components.ReaderAppBar
import com.example.jetreader.data.DataOrException
import com.example.jetreader.model.MBook
import com.example.jetreader.navigation.ReaderScreens
import com.example.jetreader.screens.home.HomeContent
import com.example.jetreader.screens.home.HomeScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreen(navController: NavHostController, id: String?,viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        ReaderAppBar(title = "Update Book", showProfile = false, navController = navController,icon = Icons.Default.ArrowBack){
            navController.popBackStack()
        }
    }) {
        val bookInfo = produceState<DataOrException<List<MBook>,
                Boolean,
                Exception>>(initialValue = DataOrException(data = emptyList(),
            true, Exception(""))){
            value = viewModel.data.value
        }.value
        Log.d("INFO", "BookUpdateScreen: ${viewModel.data.value.data.toString()}")
        Surface(modifier = Modifier
            .padding(it)
            .fillMaxSize()) {
            Column(modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if(bookInfo.loading == true){
                    LinearProgressIndicator()
                    bookInfo.loading = false;
                }else{

                        Text(text = viewModel.data.value.data?.get(0)?.title!!)

                }

            }


        }
    }
}