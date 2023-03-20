1. NavHostFragment - 탐색 구성요소가 교환되는 목적지 컨테이너 역
2. NavController - 탐색 구성요소의 내부요소, 실제로 탐색작업을 함. 진행중인 작업을 보여주는 안내자와 같음 탐색할때, 목적지로 이동할때 작동함
3. NavigationView - NavHostFragment내에 존재하지 않음, 탐색 구성요소의 일부 아님, 구성요소 이전에 존재, 탐색 창 내에 있는 메뉴와 관련있음. 메뉴 항목
  과 탐색 창을 선택하면 애플리케이션내 다른 목적이로 이동. 탐색 구성요소의 일부가 아니라 항목과 상호작용 하는것
4. NavigationUI - NavHostFragment의 화면 밖에 위치한 콘텐츠 업데이트, 화면 밖의 현재 위치 정보가 변경되면 작

# quiz
1. Fragment 사용시 장점 
  - Fragment를 사용하면 탭 바와 같은 깔끔 인터페이스 패턴 사용 가능
  - Activity 내에서 여러 Fragment를 사용하면 여러 화면 크기에 걸쳐 적응형 레이아웃을 사용 가능
  - 동일한 Fragment를 여러 Activity에 걸쳐 재사용할 수 있습니다.

2. Fragment수명 주기에서 ViewCreated()에서 수행하는 작업
  - Fragment의 프로퍼티에 View 개체 바인딩
  - Recycler View의 어댑터와 같이 각각의 View에 프로퍼티 설 가능
  
3. Fragment수명 주기에 CreateView()에서 수행하는 작업
  - 레이아웃 Inflate

4. 앱의 Fragment기반 Navigation이 앱의 업 버튼에 응답하도록 하려면 호스트 활동에서 onSupportNavigateUp 메소드를 재정의
