package com.upayments.starpayapp.state.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserBalanceModel : ViewModel() {

    private val _currentBalance = MutableStateFlow(13455)
    val currentBalance: StateFlow<Int> = _currentBalance

    fun setCurrentBalance(value: Int) {
        _currentBalance.value = value
    }

}