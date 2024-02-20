package com.example.exm_9.presentation.screen.main

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.example.exm_9.presentation.event.home.ImageEvent
import com.example.exm_9.presentation.state.ImageState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _imageState = MutableStateFlow(ImageState())
    val imageState get() = _imageState.asStateFlow()

    fun onEvent(event: ImageEvent) {
        when (event) {
            is ImageEvent.SetImage -> setImage(event.bitmap)
        }
    }

    private fun setImage(bitmap: Bitmap?) {
        _imageState.update { currentState ->
            currentState.copy(data = bitmap)
        }
    }
}