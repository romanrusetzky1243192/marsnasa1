package com.example.marsnasa1.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.marsnasa1.viewModel.Marsnasa1ViewModel
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.graphics.drawable.toBitmap
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.example.marsnasa1.R
import com.example.marsnasa1.saveimg.saveImageToGallery
import com.example.marsnasa1.ui.theme.LightBlue
import com.example.marsnasa1.ui.theme.LightGray
import com.example.marsnasa1.ui.theme.DarkGray
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Marsnasa1Screen(apiKey: String, sol: Int, date: String?, onNavigateToDetails: () -> Unit) {
    val viewModel: Marsnasa1ViewModel = viewModel()
    var loading by remember { mutableStateOf(true) }
    var newComment by remember { mutableStateOf("") }
    val likedStates = remember { mutableStateListOf<Boolean>() }
    val commentsList = remember { mutableStateListOf<MutableList<String>>() }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchPhotos(apiKey, "Curiosity", sol, date)
        loading = false
    }

    val photos = viewModel.photos.value
    val error = viewModel.error.collectAsState(initial = null).value

    if (likedStates.size != photos.size) {
        likedStates.clear()
        commentsList.clear()
        for (i in photos.indices) {
            likedStates.add(false)
            commentsList.add(mutableListOf())
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mars Rover Photos",
            style = MaterialTheme.typography.headlineMedium.copy(color = LightBlue, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        when {
            error != null -> {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Red),
                    modifier = Modifier.padding(16.dp)
                )
            }

            loading -> {
                Text(
                    text = "Загрузка фотографий...",
                    style = MaterialTheme.typography.bodyMedium.copy(color = LightBlue),
                    modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                HorizontalPager(
                    state = pagerState,
                    count = photos.size,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                ) { page ->
                    val photo = photos[page]

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                            .background(LightGray, shape = MaterialTheme.shapes.medium),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = photo.img_src,
                            contentDescription = photo.earth_date,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .padding(8.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        val context = LocalContext.current
                        Button(onClick = {
                            scope.launch {
                                try {
                                    val request = ImageRequest.Builder(context)
                                        .data(photo.img_src)
                                        .build()

                                    val result = (context.imageLoader.execute(request) as SuccessResult)
                                    val bitmap = result.drawable.toBitmap()

                                    if (bitmap != null) {
                                        Log.d("MarsRoverScreen", "Изображение загружено успешно, начинается сохранение...")

                                        val fileName = "MarsRover_${System.currentTimeMillis()}"
                                        saveImageToGallery(context, bitmap, fileName)

                                        Log.d("MarsRoverScreen", "Изображение успешно сохранено в галерею под именем: $fileName")
                                        Toast.makeText(context, "Фотография успешно сохранена в галерее!", Toast.LENGTH_LONG).show()
                                    } else {
                                        Log.e("MarsRoverScreen", "Не удалось загрузить изображение: bitmap равен null")
                                    }
                                } catch (e: Exception) {
                                    Log.e("MarsRoverScreen", "Ошибка при загрузке изображения: ${e.message}")
                                    Toast.makeText(context, "Ошибка при загрузке изображения: ${e.message}", Toast.LENGTH_LONG).show()
                                }
                            }
                        }, colors = ButtonDefaults.buttonColors(containerColor = LightBlue)) {
                            Text("Сохранить в галерею", color = Color.White)
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        IconButton(onClick = {
                            likedStates[page] = !likedStates[page]
                        }) {
                            Icon(
                                painter = painterResource(id = if (likedStates[page]) R.drawable.favorite_red else R.drawable.favorite_black),
                                contentDescription = "Like",
                                tint = if (likedStates[page]) Color.Red else Color.Gray
                            )
                        }

                        CommentSection(page, commentsList, newComment, { newComment = it }, {
                            if (newComment.isNotBlank()) {
                                commentsList[page].add(":User     $newComment")
                                newComment = ""
                            }
                        })
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                HorizontalPagerIndicator(
                    pagerState = pagerState,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
        }
    }
}
