package com.example.hamburgerbar.ui.home

import android.text.method.ScrollingMovementMethod
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "@"
    }
    val text: LiveData<String> = _text

}