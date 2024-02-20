package com.example.exm_9.presentation.event.home

import android.net.Uri

sealed class ImageEvent {
    data class SetImage(val uri: Uri?) : ImageEvent()
}