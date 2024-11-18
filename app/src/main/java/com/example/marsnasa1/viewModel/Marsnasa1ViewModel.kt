package com.example.marsnasa1.viewModel

import android.provider.ContactsContract
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.example.marsnasa1.retrofit.Marsnasa1Retrofitinstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.example.marsnasa1.model.Marsnasa1Photo
import com.example.marsnasa1.model.Marsnasa1Response
import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response
import kotlinx.coroutines.flow.update
class Marsnasa1ViewModel {
    var photos = mutableStateOf<List<Marsnasa1Photo>>(emptyList())
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    fun fetchPhotos(apiKey: String, rover: String, sol: Int, date: String? = null) {
        Log.d("MarsRoverViewModel", "Запрос к API: sol=$sol, date=$date, apiKey=$apiKey")
        val call = Marsnasa1Retrofitinstance.api.getPhotos(rover, sol, date, apiKey)
        call.enqueue(object : Callback<Marsnasa1Response> {
            override fun onResponse(call: Call<Marsnasa1Response>, response: Response<Marsnasa1Response>) {
                if (response.isSuccessful) {
                    photos.value = response.body()?.photos ?: emptyList()
                    _error.update { null }
                    Log.d(
                        "MarsRoverViewModel",
                        "Успешный ответ: количество фотографий = ${photos.value.size}"
                    )
                } else {
                    _error.update { "Ошибка: ${response.code()} ${response.message()}" }
                    Log.e(
                        "MarsRoverViewModel",
                        "Ошибка ответа: ${response.code()} ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<Marsnasa1Response>, t: Throwable) {
                Log.e("MarsRoverViewModel", "Ошибка: ${t.message}")
                _error.update { "Ошибка: ${t.localizedMessage}" }
            }
        })
    }

}