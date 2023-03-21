##ViewModel에 데이터 저장
```
1. UI컨트롤러(프래그먼트,액티비티) - 활동 및 프래그먼트는 뷰와 데이터를 화면에 그리고 사용자 이벤트에 응답합니다.
2. ViewModel - ViewModel은 UI에 필요한 모든 데이터를 보유하고 처리합니다. 뷰 계층 구조(예: 뷰 결합 객체)에 액세스하거나 활동 또는 프래그먼트의 참조를 보유해서는 안 됩니다.
```
![ViewModel수명주기](https://developer.android.com/static/codelabs/basic-android-kotlin-training-viewmodel/img/91227008b74bf4bb_856.png?hl=ko)

##ViewModel과 함께 LiveData 사용하기
```
- LiveData의 특성
1. 데이터를 보유, 모든 유형의 데이터를 사용할수 있는 래퍼(wrapper)
2. 관찰 가능, 객체에서 보유한 데이터가 변경되면 관찰자에게 알림 제공
3. 수명주기 인식, LiveData에 관찰자 연결시, LifecycleOwner(일반적으로 액티비티, 프래그먼트)와 연결
4.  STARTED 또는 RESUMED 같은 활성 수명 주기 상태인 관찰자만 업데이트


UI를 업데이트 하기 위해 updateNextWordOnScreen()사용, LiveData사용 시 여러 위치에서 따로 메서드 호출 필요 없이 관찰자에서 한번만 호출
```

1. 플러그인에 id 'kotlin-kapt'
    buildFeatures {
        dataBinding true
    }
추가

2. binding = DataBindingUtil.inflate(inflater,R.layout.game_fragment, container, false)
3. 각각의 프로퍼티를 다음과같이 변경
```kotlin    
    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount : LiveData<Int>
        get() = _currentWordCount
```
4. 프래그먼트에 추가 
    데이터 결합하여 사용할 LiveData바인딩 binding.gameViewModel = viewModel

5. 다음과 같이 레이아웃 코드 추가 및 스트링값 수정
```
    <data>
        <variable
            name="gameViewModel"
            type="com.example.android.unscramble.ui.game.GameViewModel" />
        <variable
            name="maxNoOfWord"
            type="int" />
    </data>

   android:text="@{@string/word_count(gameViewModel.currentWordCount, maxNoOfWord)}"
   android:text="@{@string/score(gameViewModel.score)}"
```
