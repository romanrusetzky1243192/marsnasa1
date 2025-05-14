package com.example.marsnasa1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.example.marsnasa1.entity.FavoriteImage
import com.example.marsnasa1.ui.theme.DarkGray
import com.example.marsnasa1.ui.theme.LightBlue
import com.example.marsnasa1.viewModel.ImageViewModel

@Composable
fun FavoritesScreen() {
    val viewModel: ImageViewModel = viewModel()
    val favorites by viewModel.allFavorites.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
    ) {
        if (favorites.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkGray),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Нет избранных изображений",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = LightBlue,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
        } else {
            LazyColumn() {
                items(favorites) { favorite ->
                    FavoriteImageItem(favorite = favorite, viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavoriteImageItem(favorite: FavoriteImage, viewModel: ImageViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(16.dp)
        ) {
            GlideImage(
                model = favorite.imageUrl,
                contentDescription = "Избранное изображение",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp),
                contentScale = ContentScale.Fit
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = favorite.imageEarthDate,
                    modifier = Modifier.weight(1f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = LightBlue
                )

                IconButton(
                    onClick = { viewModel.removeFromFavorites(favorite.imageUrl) }
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Удалить из избранного",
                        tint = Color.Red
                    )
                }
            }
        }
    }
}
