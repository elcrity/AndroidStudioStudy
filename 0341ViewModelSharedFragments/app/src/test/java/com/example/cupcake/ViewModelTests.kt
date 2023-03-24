package com.example.cupcake

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.cupcake.model.OrderViewModel
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class ViewModelTests {
    //UI는 기본 스레드에서 실행 LiveData는 기본 스레드에 엑세스가 불가능 하므로 기본 스레드를 호출 하면 안된다고 명시
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        //setQuantity()가 호출될때 quantity객체가 업데이트 되었는지 확인, 미확인시 변경된 변수를 확인하지 않아 오류
        viewModel.quantity.observeForever {}
        viewModel.setQuantity(12)
        assertEquals(12, viewModel.quantity.value)
    }

    @Test
    fun price_twelve_cupcakes(){
        val viewModel = OrderViewModel()
        viewModel.price.observeForever {}
        viewModel.setQuantity(12)
        assertEquals("₩27", viewModel.price.value)
    }
}