package com.example.android.unscramble.ui.game

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    private val _score = MutableLiveData(0)
    val score: LiveData<Int>
        get() = _score

    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int>
        get() = _currentWordCount

    //LiveData,MutableLiveData 객체의 값은 동일하게 유지되고 저장된 데이터만 변경되기때문에 변수 유형을 val로 변경
    private val _currentScrambledWord = MutableLiveData<String>()
    private val wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    //LiveData객체 내에 있는 데이터에 액세스 하기 위해 value속성 사용 -> getNextWord()
    val currentScrambledWord: LiveData<String>
        get() = _currentScrambledWord

    init {
        Log.d("GameFragment", "GameViewModelCreated!")
        getNextWord()
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val temp = currentWord.toCharArray()
        temp.shuffle()

        while (String(temp).equals(currentWord, false)) {
            temp.shuffle()
        }
        Log.d("GameFragment", "2")
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord.value = String(temp)
            _currentWordCount.value = _currentWordCount.value?.inc()
            wordsList.add(currentWord)
        }
    }

    fun isUserWordCorrect(word: String): Boolean {
        if (word.equals(currentWord, true)) {
            increaseScore()
            return true
        } else
            return false
    }

    fun nextWord(): Boolean {
        return if (currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    private fun increaseScore() {
        _score.value = (_score.value)?.plus(SCORE_INCREASE)
    }

    fun reinitializeDate() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }
}