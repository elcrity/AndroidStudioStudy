/*
 * Copyright (c) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.sports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.example.android.sports.databinding.FragmentSportsListBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */

class SportsListFragment : Fragment() {

    //뒤로 키 누름을 처리하는 맞춤 콜백 정의, 이 콜백이 handleOnBackPressed보다 우선 적용
    private val sportsViewModel: SportsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentSportsListBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSportsListBinding.bind(view)
        //FragmentActivity의 기본 클래스에서는 OnBackPressedDispatcher를 사용하여
        //뒤로 버튼의 동작을 제어할 수 있음
        val slidingPaneLayout = binding.slidingPaneLayout

        //안쪽이나 바깥쪽으로 스와이프 하지 못하도록 잠금
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED

        //디스패처 OnBackPressedDispatcher를 사용하여 콜백을 등록
        //addCallBack() 메서드를 통애 콜백을 추가 이 메서드는 LifecycleOwner를 사용, 두번째 매개변수로 콜백 클래스 사용
        requireActivity().onBackPressedDispatcher.addCallback(
            //requireActivity()는 프래그먼트에서 액티비티를 가져오는 메서드
            viewLifecycleOwner,
            //LifecycleOwner가 Lifecycle.State.STARTED일 때만 OnBackPressedCallback이 추가
            //액티비티 또는 프래그먼트는 연결된 LifecycleOwner 가 제거될때 등록된 콜백도 제거
            //전체 기간이 짧은 프래그먼트 또는 기타 수명 주기 소유자에 사용하기 적합
            SportsListOnBackPressedCallback(slidingPaneLayout)
        )

        // Initialize the adapter and set it to the RecyclerView.
        val adapter = SportsAdapter {
            // Update the user selected sport as the current sport in the shared viewmodel
            // This will automatically update the dual pane content
            sportsViewModel.updateCurrentSport(it)

            // 이 코드로 사용시 태블릿의 넓은 화면에서도 리스트를 누르면 세부정보 화면으로 이동함
            // val action = SportsListFragmentDirections.actionSportsListFragmentToNewsFragment()
            // this.findNavController().navigate(action)

            // Navigate to the details screen
            // 이 코드를 사용시 화면 자체가 2번째 창으로 변경되는것이 아닌 2번째 창의 내용만 알맞은 내용으로 변경
            binding.slidingPaneLayout.openPane()
        }
        binding.recyclerView.adapter = adapter
        adapter.submitList(sportsViewModel.sportsData)
    }
}

class SportsListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout

    //OnBackPressedCallback 클래스는 onBackPressed 콜백을 처리
    //생성자는 초기 사용 설정 상태를 나타내는 부울 값을 사용
): OnBackPressedCallback(slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen), SlidingPaneLayout.PanelSlideListener {
    //두번째 창이 슬라이드 가능하고 단일 창이 표시되는 경우만 isSlideable이 true, 즉 두번째 창, 콘텐츠 창이 완전히 열리는 경우 isOpen이 true
    init {
        //세부정보 창 슬라이드 이벤트 관련 알림을 받는 리스너 클래스,
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        //뒤로가기 누르면 단일 창 종료
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {
    }

    override fun onPanelOpened(panel: View) {//세부 정보 창 열려있을때 콜백을 사용 설정
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {//세부 정보 창 닫히면 콜백 미사용
        isEnabled = false
    }
}








