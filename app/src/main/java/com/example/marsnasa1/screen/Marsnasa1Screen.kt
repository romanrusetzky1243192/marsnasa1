package com.example.marsnasa1.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.marsnasa1.viewModel.Marsnasa1ViewModel
import androidx.compose.ui.res.painterResource
import com.example.marsnasa1.R
import com.example.marsnasa1.ui.theme.Purple40
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Marsnasa1Screen(apiKey: String, sol: Int, date: String?) {
    val viewModel: Marsnasa1ViewModel = viewModel()
    var loading by remember { mutableStateOf(true) }
    var newComment by remember { mutableStateOf("") }
    val likedStates = remember { mutableStateListOf<Boolean>() }
    val commentsList = remember { mutableStateListOf<MutableList<String>>() }
    val pagerState = rememberPagerState()

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

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "Mars Rover Photos",
            style = MaterialTheme.typography.headlineSmall.copy(color = Purple40),
            modifier = Modifier.padding(16.dp)
        )

        when {
            error != null -> {
                Text(
                    text = error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }

            loading -> {
                Text(
                    text = "Загрузка фотографий...",
                    style = MaterialTheme.typography.bodyMedium,
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
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = photo.img_src,
                            contentDescription = photo.earth_date,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                        )

                        IconButton(onClick = {
                            likedStates[page] = !likedStates[page]
                        }) {
                            Icon(
                                painter = painterResource(id = if (likedStates[page]) R.drawable.ic_liked else R.drawable.ic_like),
                                contentDescription = "Like",
                                tint = if (likedStates[page]) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
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
