package com.example.jetreader.screens.update

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetreader.components.FABContent
import com.example.jetreader.components.InputField
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

                    Surface(modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth(), shape = CircleShape, shadowElevation = 4.dp) {
                        ShowBookUpdate(bookInfo = viewModel.data.value, id)
                    }
                    ShowSimpleForm(book = viewModel.data.value.data?.first{book ->
                        book.id   == id
                    }, navController)
                }

            }


        }
    }
}

@Composable
fun ShowSimpleForm(book: MBook?, navController: NavController) {
    val noteText = remember{
        mutableStateOf("")
    }
   SimpleForm(defaultValue = if(book?.notes.toString().isNotEmpty()) book?.notes.toString() else "No thoughts available"){note->

        noteText.value = note
   }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SimpleForm(
    modifier: Modifier = Modifier,
    loading: Boolean = false,
    defaultValue:String = "Great Book!",
    onSearch: (String) ->Unit
){
    Column {
        val textFieldValue = rememberSaveable {
            mutableStateOf(defaultValue)
        }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(textFieldValue.value){
            textFieldValue.value.trim().isNotEmpty()
        }

        InputField(valueState = textFieldValue, labelId = "Enter you thoughts", enabled = true,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onSearch(textFieldValue.value.trim())
                keyboardController?.hide()
            },
            modifier = Modifier.fillMaxWidth().height(140.dp).padding(3.dp).background(Color.White,
                CircleShape).padding(horizontal = 20.dp, vertical = 12.dp)
        )
    }
}


@Composable
fun ShowBookUpdate(bookInfo: DataOrException<List<MBook>, Boolean, Exception>, bookId: String?) {
    Log.d("ShowBookUpdate", bookId.toString())
    Log.d("ShowBookUpdate", bookInfo.data.toString())
    Row {
        Spacer(modifier = Modifier.width(43.dp))
        Column (modifier = Modifier.padding(4.dp), verticalArrangement = Arrangement.Center){
            CardListItem(book = bookInfo.data!!.first(){
                it -> it.id .toString() == bookId.toString()
            }, onPressDetails = {})
        }
    }
}

@Composable
fun CardListItem(book: MBook, onPressDetails: () -> Unit) {
    Card(modifier = Modifier
        .padding(
            start = 4.dp,
            end = 4.dp,
            top = 4.dp,
            bottom = 8.dp
        )
        .clip(RoundedCornerShape(20.dp))
        .clickable { }, elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.background(Color.White)) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(book.photoUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "book image",
                modifier = Modifier
                    .height(100.dp)
                    .width(120.dp)
                    .padding(end = 4.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 120.dp, topEnd = 20.dp, bottomEnd = 0.dp, bottomStart = 0.dp
                        )
                    )
            )

            Column {
                Text(text = book.title.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .width(120.dp),
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis)

                Text(text = book.authors.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 8.dp,
                        end = 8.dp,
                        top = 2.dp,
                        bottom = 0.dp))

                Text(text = book.publishedDate.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(start = 8.dp,
                        end = 8.dp,
                        top = 0.dp,
                        bottom = 8.dp))

            }
        }


    }
}
