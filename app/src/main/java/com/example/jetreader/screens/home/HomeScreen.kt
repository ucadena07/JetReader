package com.example.jetreader.screens.home
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetreader.components.FABContent
import com.example.jetreader.components.ReaderAppBar
import com.example.jetreader.components.TitleSection
import com.example.jetreader.model.MBook
import com.example.jetreader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
   Scaffold(topBar = {
                     ReaderAppBar(title = "A.Reader", showProfile = true, navController = navController)
   }, floatingActionButton = {
        FABContent{
            
        }
   }) {
    Surface(modifier = Modifier
        .padding(it)
        .fillMaxSize()) {
        HomeContent(navController = navController)
    }
   }
}





@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController){

}



@Composable
fun HomeContent(navController: NavController){
    val currentUser = if(!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
        FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)
    }else{
        "N/A"
    }
    Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n " + " activity right now")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "profile", modifier = Modifier
                    .clickable {
                        navController.navigate(ReaderScreens.StatsScreen.name)
                    }
                    .size(45.dp), tint = MaterialTheme.colorScheme.secondary)
                Text(text = currentUser!!, modifier = Modifier.padding(3.dp), color = Color.Red, fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Clip)
                Divider()
            }
        }
    }
}

@Preview
@Composable
fun ListCard(book: MBook = MBook(id = "Afdf",title = "Running", authors = "rico cadena","hello world"),
             onPressDetails: (String) -> Unit  = {}){

    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10
    Card(shape = RoundedCornerShape(29.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .clickable {
                onPressDetails.invoke(book.title!!)
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
        Column(modifier = Modifier.width(screenWidth.dp - (spacing * 2).dp),
            horizontalAlignment = Alignment.Start) {
            Row(horizontalArrangement = Arrangement.Center) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data("")
                        .crossfade(true)
                        .build(),
                    contentDescription = "book image",
                    modifier = Modifier
                        .height(140.dp)
                        .width(100.dp)
                        .padding(4.dp)
                )
                Spacer(modifier = Modifier.width(50.dp))
                Column() {

                }
            }
        }

    }
}

