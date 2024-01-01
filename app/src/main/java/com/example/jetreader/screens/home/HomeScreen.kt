package com.example.jetreader.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.jetreader.R
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
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(onClick = { onTap()},
        shape = RoundedCornerShape(50.dp),
        containerColor = Color(0xFF92CBDF)) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add a Book", tint = MaterialTheme.colorScheme.onSecondary)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReaderAppBar(
    title: String,
    showProfile: Boolean,
    navController: NavController
){
    TopAppBar(
        title = { 
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if(showProfile){
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "icon", modifier = Modifier
                            .clip(
                                RoundedCornerShape(12.dp)
                            )
                            .scale(0.6f))
                    }
                    Text(
                        text = title,
                        color = Color.Red.copy(0.9f),
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
                    )
                    Spacer(modifier = Modifier.width(150.dp))
                }
        },
        actions = {
                  IconButton(onClick = { FirebaseAuth.getInstance().signOut().run {
                      navController.navigate(ReaderScreens.LoginScreen.name)
                  } }) {
                      Icon(imageVector = Icons.Default.Logout, contentDescription = "logout", tint = Color(0xFF92CBDF))
                  }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent)
    )
}

@Composable
fun ReadingRightNowArea(books: List<MBook>, navController: NavController){

}

@Composable
fun TitleSection(modifier: Modifier = Modifier, label:String){
    Surface(modifier = modifier.padding(start = 5.dp,top = 1.dp)) {
        Column {
                Text(text = label,
                    fontSize = 19.sp,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Left)
        }
    }
}

@Composable
fun HomeContent(navController: NavController){
    Column(modifier = Modifier.padding(2.dp), verticalArrangement = Arrangement.SpaceEvenly) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n " + " activity right now")
        }
    }
}
