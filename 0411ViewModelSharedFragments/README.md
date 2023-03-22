1. kotlin에서는 var에서 자동으로 getter, setter생성 속성 위임 사용시 책임을 다른 클래스에 넘기기 가능
   private val sharedViewModel: OrderViewModel by activityViewModels()
---
2. 범위 함수 적용
```kotlin

   clark.apply {
   firstName = "Clark"
   lastName = "James"
   age = 18
}
   // 다음 코드와 동일
   
   clark.firstName = "Clark"
   clark.lastName = "James"
   clark.age = 18
```
---
3. 탐색 그래프 
```kotlin
// 라벨 기반으로 앱 바에 제목 표시, 최상위 대상에 있지 않으면 항상 이전 버튼 표시
 val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        setupActionBarWithNavController(navController)
```
---
4.공유 ViewModel만들기
```kotlin

init{//기본 초기화 안해주면 val _quantity = MutableLiveData<Int>(0)으로 작성해야함
   resetOrder()
}

fun setQuantity(numberCupcakes: Int) {
   _quantity.value = numberCupcakes
   updatePrice()
}

fun resetOrder(){
   _quantity.value = 0
}
```
---
5. ViewModel을 사용하여 UI업데이트
   * StartFragment에서 공유 뷰 모델을 사용하려면 viewModels() 대리자 클래스 대신 activityViewModels()를 사용하여 OrderViewModel을 초기화합니다.
   * ViewModels()는 현재 프래그먼트로 범위가 지정된 ViewModel 인스턴스를 제공합니다. 따라서 인스턴스는 프래그먼트마다 다릅니다.
   * activityViewModels()는 현재 활동으로 범위가 지정된 ViewModel 인스턴스를 제공합니다. 따라서 인스턴스는 동일한 활동의 여러 프래그먼트 간에 동일하게 유지됩니다.
```kotlin
   val viewModelName : classNameViewModel by activityViewModel()//사용할 공유 모델 변수 이름, 제작한 공유 ViewModel이름


   fun orderCupcake(quantity: Int) {
      sharedViewModel.setQuantity(quantity)//사용 가능
      findNavController().navigate(R.id.action_startFragment_to_flavorFragment)
   }
```
---
6. 데이터 결합과 함께 ViewModel사용
```xml
   <variable
        name="viewModel"
        type="com.example.cupcake.model.classNameViewModel" />
    
```
레이아웃에서 공유 뷰 모델에 있는 변수 접근 가능

```kotlin
binding?.apply {
    viewModel = viewModelName//binding.viewModel = viewModelName
}
```
각 변수를 사용하는 프래그먼트 전체에 똑같이 적용

```xml
   <RadioButton
        android:onClick="@{() -> viewModel.setFlavor(@string/vanilla)}"
        android:checked="@{viewModel.flavor.equals(@string/vanilla)}"
        />
```
다음과 같이 적용한 뷰 모델에 있는 변수에 접근 가능, 이벤트 리스너 역시 리스너 결합으로 사용 가능

---


```kotlin
   private fun getPickupOptions(): List<String> {
         val options = mutableListOf<String>()
         val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())//날짜 형식을 ("요일 달 일"로 설정)
         val calendar = Calendar.getInstance()//인스턴스를 가져와 할당
         // Create a list of dates starting with the current date and the following 3 dates
         repeat(4) {
            options.add(formatter.format(calendar.time))
            //지정한 날짜 형식 추가 ,0번에 지정 형식 "월 01 01"추가
            calendar.add(Calendar.DATE, 1)
            //캘린더 1일씩 추가, 반복되며 options.add에서 화 01 02추가
         }
      return options
     }

     val dateOptions = getPickupOptions()
  //-> dateOption[0] ~ dateOption[3]
```
---
7. LiveData를 관찰하도록 수명 주기 소유자 설정
   LifecycleOwner는 Android 수명 주기를 보유한 클래스입니다.
   LiveData 관찰자는 수명 주기 소유자가 활성 상태(STARTED 또는 RESUMED)인 경우에만 앱 데이터의 변경사항을 관찰합니다.
   관찰 가능한 값이 변경되는 경우 결합된 UI 요소가 자동으로 업데이트됩니다.

8. 리스너 결합을 사용하여 클릭 리스너 설정
```xml
   <variable
            name="startFragment"
            type="com.example.cupcake.StartFragment" />

   <variable
            name="flavorFragment"
            type="com.example.cupcake.FlavorFragment" />

```
각각의 파일명에 맞는 프래그먼트 인스턴스 결합

```kotlin
    //startFragment.kt - 시작 프래그먼트
    binding?.startFragment = this

    ///flavorFragment
    binding?.apply {
        flavorFragment = this@FlavorFragment//프래그먼트 데이터 변수와 프래그먼트 인스턴스 결합 @FlavorFragment는 프래그먼트 인스턴스가 아닌 결합 인스턴스 참조시
    }
```

```xml
   <Button
        android:id="@+id/next_button"
        android:onClick="@{() -> flavorFragment.goToNextScreen()}"
        />
```
버튼 클릭 리스너를 레이아웃에 결합

```
데이터 결합과 함께 ViewModel사용과의 차이는 
공유 ViewModel과의 결합은 공유 ViewModel 변수를 사용하기 위해 Fragment를 ViewModel에 결합 binding.viewModel = viewModelName
,SharedViewModel&Fragment
리스너 결합은 Layout 이벤트 리스너를 사용하기 위해 각각의 Fragment를 해당되는 layout 연결
,Fragment&Layout
```


```kotlin
private val _price = MutableLiveData<Double>()
val price: LiveData<String> = Transformations.map(_price) {//_price형을 NumberFormat.getCurrencyInstance()형태로 변경후 다시 it(여기서는 Double)로 다시 변경
NumberFormat.getCurrencyInstance().format(it)
}
```
```xml
<radioButton
android:text="@{@string/subtotal_price(viewModel.price)}"
/>

<string name="subtotal_price">Subtotal %s</string>
```
sbutotal_price를 사용하고 %s에는 viewModel.price입력