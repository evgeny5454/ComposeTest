package com.evgeny_minaenkov.timemanager

import android.icu.text.CaseMap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

data class CharacteristicsModel(
    val title: String,
    val value: String
)

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

    private val _pickupStoresCount: MutableLiveData<Int> = MutableLiveData(10)
    val pickupStoresCount: LiveData<Int> = _pickupStoresCount

    private val _characteristics: MutableLiveData<List<CharacteristicsModel>> = MutableLiveData(
        listOf(
            CharacteristicsModel("Толщина (мм)", "12.5"),
            CharacteristicsModel("Вес, кг:", "8.8"),
            CharacteristicsModel("Марка", "KNAUF"),
            CharacteristicsModel("Страна производтель:", "Россия")
        )
    )
    val characteristics: LiveData<List<CharacteristicsModel>> = _characteristics

    fun addToCart() {
        var count = _itemsInCart.value
        if (count != null) {
            count += 1
            _itemsInCart.postValue(count)
        }
    }
}