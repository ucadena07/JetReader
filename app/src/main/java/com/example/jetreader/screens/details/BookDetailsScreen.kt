package com.example.jetreader.screens.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
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
                        Text(text = "Loading..")
                    }else{
                      ShowBookDetails(bookInfo,navController)
                    }

                }
            }

        }

    }
}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Card(modifier = Modifier.padding(34.dp), shape = CircleShape, elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(bookData?.imageLinks?.thumbnail)
                .crossfade(true)
                .build(),
            contentDescription = "book image",
            modifier = Modifier
                .height(90.dp)
                .width(90.dp)
                .padding(end = 1.dp)
        )
    }
    Text(text = bookData?.title.toString(),
        style = MaterialTheme.typography.titleLarge,
        overflow = TextOverflow.Ellipsis)
    Text(text = "Authors: ${bookData?.authors.toString()}",style = MaterialTheme.typography.titleSmall)
    Text(text = "Page Count: ${bookData?.pageCount.toString()}",style = MaterialTheme.typography.titleSmall)
    Text(text = "Categories: ${bookData?.categories.toString()}",style = MaterialTheme.typography.titleSmall)
    Text(text = "Published: ${bookData?.publishedDate.toString()}",style = MaterialTheme.typography.titleSmall)
    Spacer(modifier = Modifier.height(5.dp))

}