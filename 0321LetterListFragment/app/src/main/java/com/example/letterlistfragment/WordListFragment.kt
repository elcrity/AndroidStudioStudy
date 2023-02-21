package com.example.letterlistfragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wordsapp.R
import com.example.wordsapp.WordAdapter
import com.example.wordsapp.databinding.FragmentLetterListBinding

class WordListFragment : Fragment() {
    companion object {
        const val LETTER = "letter"
        const val SEARCH_PREFIX = "https://www.google.com/search?q="
    }

    private var _binding : FragmentLetterListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetterListBinding.inflate(inflater, container,false)
        return binding.root
    }


    /*
        val letterId = intent?.extras?.getString(WordListFragment.LETTER).toString()

        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = WordAdapter(letterId, this)

        // Adds a [DividerItemDecoration] between items
        recyclerView.addItemDecoration(
        DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        title = getString(R.string.detail_prefix) + " " + letterId
    */

    //onViewCreated() 함수는 해당 Fragment 또는 Activity가 화면에 표시되기 직전에 호출
    //이 함수 내에서 view와 savedInstanceState 파라미터를 받아와서 뷰 초기화 작업을 수행
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        //가져온 rcyclerView에 LinearLayoutManager객체를 생성하여 할당, 아이템을 세로, 가로로 스크롤 할수 있도록 해주는 레이아웃 매니저
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        //가져온 recyclerView에 WordAdapter 객체를 생성하여 어댑터로 할당.
        // 이 어댑터는 알파벳 문자열 하나(LETTER)를 인자로 받아서 해당 알파벳으로 시작하는 단어들을 RecyclerView에 보여주는 역할을 합니다.
        recyclerView.adapter = WordAdapter(activity?.intent?.extras?.getString(LETTER).toString(), requireContext())
        //가져온 recyclerView에 DividerItemDecoration 객체를 생성하여 추가합니다.
        // DividerItemDecoration은 RecyclerView의 각 아이템 사이에 구분선을 추가하는 데 사용됩니다.
        // 이 구분선은 수직 방향으로 설정됩니다.
        recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding
    }
}