package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.LetterAdapter
import com.example.wordsapp.R
import com.example.wordsapp.databinding.FragmentLetterListBinding

class LetterListFragment : Fragment() {
    //FragmentLetterListBinding참조는 viewBinding속성 사용이 설정될때 각 레이아웃 파일에 생성
    private var _binding: FragmentLetterListBinding? = null
    //null을 허용해야 하는 이유 - onCreateView()가 호출되기 전까지 레이아웃 확장이 불가해서
    //프로그래밍에서는 속성 이름 앞에 밑줄이 있는 것을 자주 볼 수 있음. 반적으로 속성에 직접 액세스하지 못하도록 하기 위함
    private val binding get() = _binding!!
    //get()은 이 속성이 get-only임을 나타냄, 값을 가져올수 있지만 할당되고 나면 다른것에 할당이 불가능 함
    private lateinit var recyclerView: RecyclerView
    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        // setHasOptionsMenu(true) 메서드를 호출하면, 해당 Fragment가 호스팅하는 Activity에서 옵션 메뉴를 생성
        // Fragment의 onCreateOptionsMenu() 메서드를 호출하게 됩니다.
        // onCreateOptionsMenu() 메서드에서는 해당 Fragment에서 사용할 옵션 메뉴를 인플레이트하고,
        // 옵션 메뉴의 클릭 이벤트를 처리하는 코드를 작성할 수 있습니다.
    }

    //프래그먼트에서는 onCreateView()에서 확장, 뷰를 확장하고 _binding값을 설정한 다음 루트뷰를 반환 -> onCreateView()를 구현
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //뷰를 확장, _binding값 설정
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        //루트뷰 반환
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //recyclerView속성값 설정후 chooseLayout()호출
        recyclerView = binding.recyclerView
        chooseLayout()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    //프래그먼트 사용시 유의할점 - onCreateOptionsMenu() 메서드와 약간의 미묘한 차이가 있음
    //Activity 클래스에는 menuInflater라는 전역 속성이 있지만 프래그먼트에는 이 속성이 없는 대신 onCreateOptionsMenu()로 전달
    //또한 프래그먼트와 함께 사용되는 onCreateOptionsMenu() 메서드에는 return 문이 필요하지 않음
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)
    }

    //프래그먼트는 Context가 아니라는 것입니다. this(프래그먼트 객체 참고)를 레이아웃 관리자의 컨텍스트로 전달할 수 없습니다.
    //그러나 프래그먼트는 대신 사용할 수 있는 context 속성을 제공합니다. 나머지 코드는 MainActivity와 동일합니다.
    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(context)
        } else {
            recyclerView.layoutManager = GridLayoutManager(context, 4)
        }
        recyclerView.adapter = LetterAdapter()
    }

    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null)
            return

        menuItem.icon =
            if (isLinearLayoutManager)
                ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_linear_layout)
    }

    /**
     * Initializes the [Menu] to be used with the current [Activity]
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)

                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}