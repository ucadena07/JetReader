package com.example.jetreader.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.example.jetreader.data.Resource
import com.example.jetreader.model.Item
import com.example.jetreader.navigation.ReaderScreens
import com.example.jetreader.screens.search.BookList
import com.example.jetreader.screens.search.SearchForm
import dagger.hilt.android.lifecycle.HiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetailsScreen(navController: NavHostController, bookId: String, viewModel: DetailsViewModel= hiltViewModel()) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Details",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false
        ) {
            navController.popBackStack()
        }
    }, floatingActionButton = {

    }) { it ->
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {


            Surface(modifier = Modifier
                .padding(3.dp)
                .fillMaxSize()) {
                Column(modifier = Modifier.padding(top = 12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top) {
                    val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()){
                        value = viewModel.getBookInfo(bookId)
                    }.value
                    val data = bookInfo.data
                    if(data == null){
                        LinearProgressIndicator()
                    }else{
                        Text(text = "Book des: ${data?.volumeInfo?.title}")
                    }

                }
            }

        }

    }
}