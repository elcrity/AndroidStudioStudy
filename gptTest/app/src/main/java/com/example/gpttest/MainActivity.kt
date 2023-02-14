package com.example.gpttest

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

data class Card(val suit: String, val rank: String) {
    fun getValue(): Int {
        return when (rank) {
            "Ace" -> 11
            "King", "Queen", "Jack" -> 10
            else -> rank.toInt()
        }
    }
}

class Deck {
    val cards = mutableListOf<Card>()

    init {
        val suits = arrayOf("Spades", "Hearts", "Diamonds", "Clubs")
        val ranks = arrayOf("Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King")

        for (suit in suits) {
            for (rank in ranks) {
                cards.add(Card(suit, rank))
            }
        }

        cards.shuffle()
    }

    fun drawCard(): Card {
        return cards.removeAt(0)
    }
}

class Blackjack {
    private var deck = Deck()
    private var playerCards = mutableListOf<Card>()
    private var dealerCards = mutableListOf<Card>()

    init {
        playerCards.add(deck.drawCard())
        dealerCards.add(deck.drawCard())
        playerCards.add(deck.drawCard())
        dealerCards.add(deck.drawCard())
    }

    fun playerTurn(): Boolean {
        while (getHandValue(playerCards) < 21) {
            // Add logic here to prompt the player to hit or stay
        }
        return getHandValue(playerCards) <= 21
    }

    fun dealerTurn(): Boolean {
        while (getHandValue(dealerCards) < 17) {
            dealerCards.add(deck.drawCard())
        }
        return getHandValue(dealerCards) <= 21
    }

    private fun getHandValue(hand: List<Card>): Int {
        var handValue = 0
        for (card in hand) {
            handValue += card.getValue()
        }
        return handValue
    }

    fun determineWinner(): String {
        val playerValue = getHandValue(playerCards)
        val dealerValue = getHandValue(dealerCards)
        return when {
            playerValue > 21 -> "Dealer Wins"
            dealerValue > 21 -> "Player Wins"
            playerValue > dealerValue -> "Player Wins"
            dealerValue > playerValue -> "Dealer Wins"
            else -> "Tie"
        }
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var resultTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resultTextView = findViewById(R.id.result_text_view)

        Blackjack()
    }
}
