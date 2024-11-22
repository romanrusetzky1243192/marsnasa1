package com.example.marsnasa1.screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import com.example.marsnasa1.ui.theme.Purple40
import com.example.marsnasa1.ui.theme.PurpleGrey40
import com.example.marsnasa1.ui.theme.PurpleGrey80


@Composable
fun CommentSection(
    page: Int,
    commentsList: MutableList<MutableList<String>>,
    newComment: String,
    onNewCommentChanged: (String) -> Unit,
    onSendClick: () -> Unit
) {
    TextField(
        value = newComment,
        onValueChange = onNewCommentChanged,
        label = { Text("Добавить комментарий") },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Purple40,
            unfocusedIndicatorColor = PurpleGrey40,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent
        )
    )
    Spacer(modifier = Modifier.height(8.dp))
    Button(onClick = onSendClick) {
        Text("Отправить")
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column {
        commentsList[page].reversed().forEach { comment ->
            Text(
                text = comment,
                style = MaterialTheme.typography.bodyMedium.copy(color = PurpleGrey40),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(
                        PurpleGrey80,
                        RoundedCornerShape(4.dp)
                    )
                    .padding(8.dp)
            )
        }
    }
}