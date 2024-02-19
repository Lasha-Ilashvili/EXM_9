package com.example.exm_9.presentation.event.home

sealed class HomeEvent {
    data object ResetErrorMessage : HomeEvent()
}