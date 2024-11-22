package com.example.marsnasa1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.marsnasa1.screen.Marsnasa1Screen
import com.example.marsnasa1.ui.theme.Marsnasa1Theme

class MainActivity : ComponentActivity() {
    private val apiKey: String ="fXiQuucR6dq0W7odYCaGpUPKCbfmghuD6EseRtxT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Log.d("MainActivity", "Setting content")
            Marsnasa1Screen(apiKey, sol = 1122, date = "2024-10-01")
        }
    }
}