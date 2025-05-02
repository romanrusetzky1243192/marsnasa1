package com.example.marsnasa1.navigate

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.marsnasa1.R
import com.example.marsnasa1.screen.Marsnasa1Screen
import com.example.marsnasa1.screen.Marsnasa1SearchScreen
import com.example.marsnasa1.ui.theme.DarkDarkGray
import com.example.marsnasa1.ui.theme.DarkGray
import com.example.marsnasa1.ui.theme.LightGray
import kotlinx.coroutines.launch

@Composable
fun AppNavGraph(apiKey: String) {

    val navController = rememberNavController()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    val items = listOf(
        NavItem("Mars rover photos", "mars"),
        NavItem("Поиск", "search") ,
        NavItem("Избранное", "photo")
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {  },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.LightGray )
                    }
                },
                backgroundColor = DarkDarkGray
            )
        },
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkDarkGray)
                    .padding(top = 24.dp)
            )  {
                Icon(
                    painter  = painterResource(R.drawable.mars_planet_space_icon),
                    contentDescription = "Planet Icon",
                    tint = Color.LightGray,
                    modifier = Modifier.size(128.dp).padding(16.dp)
                )

//                Text(
//                    "Меню",
//                    color = Color.White,
//                    fontSize = 20.sp,
//                    fontWeight = FontWeight.Bold,
//                    modifier = Modifier.padding(16.dp)
//                )
                items.forEach { item ->
                    Text(
                        text = item.title,
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                coroutineScope.launch {
                                    scaffoldState.drawerState.close()
                                    navController.navigate(item.route) {
                                        popUpTo("mars") { inclusive = false }
                                        launchSingleTop = true
                                    }
                                }
                            }
                            .padding(horizontal = 16.dp, vertical = 20.dp)
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "mars",
            modifier = Modifier.padding(padding)
        ) {
            composable("mars") {
                Marsnasa1Screen(apiKey, sol = 1122, date = "2024-10-01"){
                    navController.navigate("search")
                }
            }
            composable("search") {
                Marsnasa1SearchScreen(apiKey){
                    navController.navigate("photo")
                }
            }

            composable("photo") {
                Marsnasa1Screen(apiKey, sol = 1100, date = "2023-10-01"){
                    navController.navigate("search")
                }
            }
        }
    }

}

data class NavItem(val title: String, val route: String)