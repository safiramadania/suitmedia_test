package com.example.suitmediatest.ui.viewmodel

import androidx.lifecycle.ViewModel
import java.util.Locale

class MainViewModel : ViewModel() {

    fun isPalindrome(text: String): Boolean {
        val normalized = text
            .replace(" ", "")
            .lowercase(Locale.getDefault())

        val reversed = normalized.reversed()
        return normalized == reversed
    }

}
