package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.ViewModel

//viewModel의 서브클래스 GameViewModel
/*
* GameFragment에서 사용하기 위해 public으로 변경할순 없음 - 데이터를 다른 클래스가 수정해서는 안됨, 위험
* ViewModel내부에서는 데이터를 수정해야하기때문에 private 및 var이여야 함
* 이 동작을 위해 지원 속성 기능 사용

* 지원속성 예시
* private var _count = 0
*
* val count: Int
   get() = _count
* ViewModel내부에선 _count속성이 private라도 접근 가능
* 외부에선 count는 공개 속성이며 UI 컨트롤러와 같은 다른 클래스에서 액세스할 수 있습니다.
* get() 메서드만 재정의되므로, 이 속성은 변경할 수 없으며 읽기 전용입니다.
* 외부 클래스가 이 속성에 액세스하면 _count의 값을 반환하며, 이 값은 수정할 수 없습니다

* */
class GameViewModel : ViewModel() {
    private var _score = 0
    val score : Int
        get() = _score
    private var _currentWordCount = 0
    val currentWordCount : Int
        get() = _currentWordCount
    private lateinit var _currentScrambledWord :String
    private var wordsList : MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    val currentScrambledWord: String
        get() = _currentScrambledWord

    init{
        Log.d("GameFragment", "GameViewModelCreated!")
        getNextWord()
    }

    private fun getNextWord(){
        currentWord = allWordsList.random()
        val temp = currentWord.toCharArray()
            temp.shuffle()

        while(String(temp).equals(currentWord,false)) {
            temp.shuffle()
        }
        Log.d("GameFragment", "2")
        if(wordsList.contains(currentWord)){
            getNextWord()
        }else{
            _currentScrambledWord = String(temp)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun isUserWordCorrect(word : String) : Boolean{
        if(word.equals(currentWord, true)){
            increaseScore()
            return true
        }else
            return false
    }

    fun nextWord():Boolean{
        return if(currentWordCount < MAX_NO_OF_WORDS){
            getNextWord()
            true
        }else false
    }

    private fun increaseScore(){
        _score += SCORE_INCREASE
    }

    fun reinitializeDate(){
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }
    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModelCleared!")
    }
}