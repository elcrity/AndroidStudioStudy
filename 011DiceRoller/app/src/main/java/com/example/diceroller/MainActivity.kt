    package com.example.diceroller

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

    class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rollButton : Button = findViewById(R.id.button)

        rollButton.setOnClickListener {
            rollDice()
        }

        rollDice()
    }


    private fun rollDice() {
        //6면체 주사위 생성
        val dice = Dice(6)
        //1~6까지 랜덤 숫자
        val diceRoll = dice.roll()
        val diceRoll2 = dice.roll()
        //diceImage id값
        val diceImage: ImageView = findViewById(R.id.imageView)
        val diceImage2: ImageView = findViewById(R.id.imageView2)
        //나온 숫자에 맞는 이미지 id 입력
        val drawableResource = when (diceRoll) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
        val drawableResource2 = when (diceRoll2) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }

        //imageView를 입력받은 이미지 id로 설정
        diceImage.setImageResource(drawableResource)
        diceImage2.setImageResource(drawableResource2)
        //내용 설명 업데이트
        diceImage.contentDescription = diceRoll.toString()
        diceImage2.contentDescription = diceRoll.toString()
    }
}

class Dice(private val numSides: Int){
    fun roll() : Int{
        return (1..numSides).random()
    }
}