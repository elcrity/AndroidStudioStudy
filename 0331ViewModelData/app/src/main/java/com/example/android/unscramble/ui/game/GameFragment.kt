/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

package com.example.android.unscramble.ui.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.android.unscramble.R
import com.example.android.unscramble.databinding.GameFragmentBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * Fragment where the game is played, contains the game logic.
 */
class GameFragment : Fragment() {

    // Binding object instance with access to the views in the game_fragment.xml layout
    private lateinit var binding: GameFragmentBinding
    private val viewModel : GameViewModel by viewModels() //view모델로 속성 위임
    /*
    코틀린은 var속성일때는 자동으로 getter, setter함수가 생성
    val의 경우 기본적으로 getter함수만 생성, 속성 위임을 사용하면 getter-setter의 책임을 다른 클래스에 넘길수 있음
    이 클래스는 getter,setter 함수를 제공하고 변경사항 처리, 대리 속성은 by절 및 대리자 클래스 인스턴스를 사용하여 정리
    var <property-name> : <property-type> by <delegate-class>()
    */
    /*
    다음과 같이 생성자를 초기화하는 경우 private val viewModel = GameViewModel() 기기 구성 변경시 참조 상태 손실(기기 회전시 액티비티 소멸 후 재생성)
    속성 위임 방식을 사용시 대리자 클래스 viewModels에 의해 내부적으로 처리, 첫 접근시 자동으로 viewModel객체를 만들어 구성 변경중에도 유지하며 요청시 반환
    즉 속성 위임 방식을 사용하면 구성 변경시에도 내부적으로 대리자 클래스에 의해 자동으로 객체가 생성되어 구성 변경시에도 값을 유지하고 반환
    */
    // Create a ViewModel the first time the fragment is created.
    // If the fragment is re-created, it receives the same GameViewModel instance created by the
    // first fragment


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        // Inflate the layout XML file and return a binding object instance
        binding = GameFragmentBinding.inflate(inflater, container, false)
        Log.d("GameFragment", "GameFragment created/re-created!")
        Log.d("GameFragment", "Word: ${viewModel.currentScrambledWord} " +
                "Score: ${viewModel.score} WordCount: ${viewModel.currentWordCount}")
        return binding.root
    }

    fun showFinalScoreDialog(){
        MaterialAlertDialogBuilder(requireContext())
            //Context는 애플리케이션이나 활동, 프래그먼트의 컨텍스트나 현재 상태를 나타냅니다.
            //활동, 프래그먼트, 애플리케이션과 관련된 정보를 포함하고 있으며 일반적으로 리소스, 데이터베이스, 기타 시스템 서비스에 액세스하는 데 사용
            .setTitle(getString(R.string.congratulations))
            .setMessage(getString(R.string.you_scored,viewModel.score))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.exit)){ _, _ ->
                exitGame()
            }
            .setPositiveButton(getString((R.string.play_again))){_,_ ->
                restartGame()
            }
            .show()
        // setNegativeButton() 메서드는 두 매개변수, 즉 String과 함수 DialogInterface.OnClickListener()(람다로 표현 가능)를 사용합니다.
        // 전달되는 마지막 인수가 함수(exitGame())이면 괄호 바깥에 람다 표현식을 배치할 수 있습니다. 이를 후행 람다 구문이라고 합니다

    }

    override fun onDetach() {
        super.onDetach()
        Log.d("GameFragment", "GameFragment destroyed!")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup a click listener for the Submit and Skip buttons.
        binding.submit.setOnClickListener { onSubmitWord() }
        binding.skip.setOnClickListener { onSkipWord() }
        // Update the UI
        updateNextWordOnScreen()
        binding.score.text = getString(R.string.score, 0)
        binding.wordCount.text = getString(
                R.string.word_count, 0, MAX_NO_OF_WORDS)
    }

    /*
    * Checks the user's word, and updates the score accordingly.
    * Displays the next scrambled word.
    */
    private fun onSubmitWord() {
        val playerWord = binding.textInputEditText.text.toString()

        if(viewModel.isUserWordCorrect(playerWord)) {
            setErrorTextField(false)
            if (viewModel.nextWord()) {
                updateNextWordOnScreen()
            } else {
                showFinalScoreDialog()
            }
        }else{
            setErrorTextField(true)
        }
    }

    /*
     * Skips the current word without changing the score.
     * Increases the word count.
     */
    private fun onSkipWord() {
        if(viewModel.nextWord()){
            setErrorTextField(false)
            updateNextWordOnScreen()
        }else{
            showFinalScoreDialog()
        }
    }

    /*
     * Gets a random word for the list of words and shuffles the letters in it.
     */
    private fun getNextScrambledWord(): String {
        val tempWord = allWordsList.random().toCharArray()
        tempWord.shuffle()
        return String(tempWord)
    }

    /*
     * Re-initializes the data in the ViewModel and updates the views with the new data, to
     * restart the game.
     */
    private fun restartGame() {
        viewModel.reinitializeDate()
        setErrorTextField(false)
        updateNextWordOnScreen()
    }

    /*
     * Exits the game.
     */
    private fun exitGame() {
        activity?.finish()
    }

    /*
    * Sets and resets the text field error status.
    */
    private fun setErrorTextField(error: Boolean) {
        if (error) {
            binding.textField.isErrorEnabled = true
            binding.textField.error = getString(R.string.try_again)
        } else {
            binding.textField.isErrorEnabled = false
            binding.textInputEditText.text = null
        }
    }

    /*
     * Displays the next scrambled word on screen.
     */
    private fun updateNextWordOnScreen() {
        binding.textViewUnscrambledWord.text = viewModel.currentScrambledWord
    }
}
