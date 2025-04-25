package com.example.marsnasa1.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.marsnasa1.ui.theme.DarkGray
import com.example.marsnasa1.ui.theme.LightBlue
import com.example.marsnasa1.ui.theme.LightGray

@Composable
fun Marsnasa1SearchScreen( onNavigateToDetails: () -> Unit) {

    var solState by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
       Text(
            text = "Поиск",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = LightBlue,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(16.dp)
        )


        Text(
            text = "Введите sol",
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        TextField(
            value = solState,
            onValueChange = { solState = it },
            placeholder = { Text("Например, 1122") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = LightBlue,
                focusedContainerColor = LightGray,
                unfocusedContainerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { onNavigateToDetails() },
            colors = ButtonDefaults.buttonColors(containerColor = LightBlue),
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).padding(horizontal = 16.dp)
        ) {
            Text("Найти", color = Color.White)
        }


    }
}