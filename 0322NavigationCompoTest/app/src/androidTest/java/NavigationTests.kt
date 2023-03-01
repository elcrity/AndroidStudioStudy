package com.example.wordsapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Assert.assertEquals
import org.junit.Test

class NavigationTests {

    @Test
    fun navigate_to_words_nav_component() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        //탐색 구성요소는 프래그먼트를 사용하여 UI 구동
        //여기서는 LetterListFragment를 실행하도록 지정.
        //UI 구성요소가 사용할 테마를 알 수 있도록 앱의 테마를 전달해야 하며 그러지 않으면 테스트가 다운될 수 있습니다.
        val letterListScenario = launchFragmentInContainer<LetterListFragment>(themeResId =
        R.style.Theme_Words)

        //마지막으로, 탐색 컨트롤러가 프래그먼트 실행에 사용하려는 탐색 그래프를 명시적으로 선언해야 합니다.
        letterListScenario.onFragment { fragment ->

            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //탐색을 실행하는 이벤트 트리거
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)
    }
}
//launchFragmentInContainer() 메서드를 사용할 경우
//이동하고 있을 수 있는 다른 프래그먼트나 활동을 컨테이너가 인식하지 못하므로 실제 탐색이 불가능
//컨테이너에서 실행하도록 지정된 프래그먼트만 인식
//기기 또는 에뮬레이터에서 이 테스트를 실행하면 실제 탐색이 표시되지않음
//현재 대상에 관한 훨씬 더 직접적인 어설션이 가능
//UI 구성요소를 찾는 대신, 현재 탐색 컨트롤러의 대상이 예상한 프래그먼트의 ID인지 확인