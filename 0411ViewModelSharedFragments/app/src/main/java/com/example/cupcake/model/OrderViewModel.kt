package com.example.cupcake.model

import android.util.Log
import android.widget.RadioButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.cupcake.R
import com.example.cupcake.databinding.FragmentPickupBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.0
private const val SAME_DAY_PICKUP = 3.0

class OrderViewModel : ViewModel() {
    private val _quantity = MutableLiveData<Int>()
    val quantity: LiveData<Int> = _quantity

    private val _flavor = MutableLiveData<String>()
    val flavor: LiveData<String> = _flavor

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date


    private val _price = MutableLiveData<Double>()

    //Transformations.map()은 변환 함수, 소스 LiveDate 및 함수를 매개변수로 사용, LiveData 소스를 조작하고 관찰 할 수도 있는 업데이트된 값을 반환
    val price: LiveData<String> = Transformations.map(_price) {
        //NumberFormat.getCurrencyInstance()이용하여 가격을 현지 통화 형식으로 변경, format(it)은 다시 Double형태로 변경
        NumberFormat.getCurrencyInstance().format(it)
    }


    val dateOption = getPickUpOption()

    init {//기본 초기화 안해주면 val _quantity = MutableLiveData<Int>(0)으로 작성해야함
        resetOrder()
    }

    fun setQuantity(numberCupcakes: Int) {
        _quantity.value = numberCupcakes
        updatePrice()
    }

    fun setFlavor(desiredFlavor: String) {
        _flavor.value = desiredFlavor
    }

    fun setDate(pickDate: String) {
        _date.value = pickDate
        updatePrice()
    }

    fun hasNoFlavorSet(): Boolean {
        return _flavor.value.isNullOrEmpty()
    }

    fun getPickUpOption(): List<String> {
        //4가지 날짜 옵션을 넣을 프로퍼티
        val options = mutableListOf<String>()

        //SimpleDateFormat 이용하여 형식 지정 문자열 만듬
        // E는 요일, M은 달 d는 날짜
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())

        //Calendar인스턴스를 가져와 새 변수에
        val calendar = Calendar.getInstance()
        //현재 날짜 및 시간 포함
        repeat(4) {
            //날짜 형식 지정하여 날짜 목록에 추가
            options.add(formatter.format(calendar.time))
            //캘린더 1일 증가 후 추가시킴
            calendar.add(Calendar.DATE, 1)
        }
        Log.d("Date", "datemake")
        return options
    }

    fun resetOrder() {
        _quantity.value = 0
        _flavor.value = ""
        _date.value = dateOption[0]
        _price.value = 0.0
    }


    fun updatePrice() {
        var calculatedPrice = (_quantity.value ?: 0) * PRICE_PER_CUPCAKE
        if (dateOption[0] == _date.value) {
            calculatedPrice += SAME_DAY_PICKUP
        }
        _price.value = calculatedPrice
    }
}