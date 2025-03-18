package com.upayments.starpayapp.state.models

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object CardDataModel : ViewModel() {

    private val _cardPAN = MutableStateFlow("Obteniendo...")
    val cardPAN: StateFlow<String> = _cardPAN

    private val _cardExpiryDate = MutableStateFlow("...")
    val cardExpiryDate: StateFlow<String> = _cardExpiryDate

    private val _cardCVV = MutableStateFlow("...")
    val cardCVV: StateFlow<String> = _cardCVV

    private val _balance = MutableStateFlow(0)
    val balance: StateFlow<Int> = _balance

    fun setCardPAN(value: String) {
        _cardPAN.value = value
    }

    fun setCardExpiryDate(value: String) {
        _cardExpiryDate.value = value
    }

    fun setCardCVV(value: String) {
        _cardCVV.value = value
    }

    fun setBalance(value: Int) {
        _balance.value = value
    }

}