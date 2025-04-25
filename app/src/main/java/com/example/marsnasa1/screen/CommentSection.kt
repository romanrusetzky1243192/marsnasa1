package com.example.marsnasa1.screen

import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import com.example.marsnasa1.ui.theme.DarkGray
import com.example.marsnasa1.ui.theme.LightBlue
import com.example.marsnasa1.ui.theme.LightGray

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
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = LightBlue,
            focusedContainerColor = LightGray,
            unfocusedContainerColor = Color.Transparent
        ),
        modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
    )

    Button(
        onClick = onSendClick,
        colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).padding(horizontal = 16.dp)
    ) {
        Text("Отправить", color = Color.White)
    }

    Spacer(modifier = Modifier.height(16.dp))

    Column {
        commentsList[page].reversed().forEach { comment ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .background(Color.White),
                shape = RoundedCornerShape(4.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = comment,
                    style = MaterialTheme.typography.bodyMedium.copy(color = DarkGray),
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
