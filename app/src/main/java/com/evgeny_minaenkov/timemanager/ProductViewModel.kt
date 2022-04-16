package com.evgeny_minaenkov.timemanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel : ViewModel() {
    private val _sku: MutableLiveData<String> = MutableLiveData("Артикул: 801336659")
    val sku: LiveData<String> = _sku

    private val _title: MutableLiveData<String> =
        MutableLiveData("Дрель аккумуляторная Dexter, 18B, Li-ion, 2 Ач")
    val title: LiveData<String> = _title

    private val _itemsInCart: MutableLiveData<Int> = MutableLiveData(0)
    val itemsInCart: LiveData<Int> = _itemsInCart

    private val _availableCount: MutableLiveData<Int> = MutableLiveData(9999)
    val availableCount: LiveData<Int> = _availableCount

    fun addToCart(){
        var count = _itemsInCart.value
        if (count!= null) {
            count += 1
            _itemsInCart.postValue(count)
        }
    }
}