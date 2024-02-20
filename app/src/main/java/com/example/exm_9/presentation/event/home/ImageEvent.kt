package com.example.exm_9.presentation.event.home

import android.graphics.Bitmap

sealed class ImageEvent {
    data class SetImage(val bitmap: Bitmap?) : ImageEvent()
}