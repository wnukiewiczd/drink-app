package pl.poznan.put.cs.lab2_155858_barman.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DrinkViewModel : ViewModel() {
    private val _drinksList = MutableLiveData<List<Drink>>()
    val drinksList: LiveData<List<Drink>> = _drinksList

    fun setDrinks(drinks: List<Drink>) {
        _drinksList.value = drinks
    }

    fun getDrinkById(id: String): Drink? {
        return _drinksList.value?.find { it.idDrink == id }
    }
}
