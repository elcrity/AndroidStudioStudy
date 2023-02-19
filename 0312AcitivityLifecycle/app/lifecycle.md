[screensh](https://developer.android.com/static/codelabs/basic-android-kotlin-training-activity-lifecycle/img/c259ab6beca0ca88.png?hl=ko)
1. onPause(), onResume() - 포커스만 변경
2. onStop(), onStart() or onRestart() - 화면 표시 변경
3. onCreated,Destroy 단일 활동 인스턴스 기간에 한번만 호출
   1. onCreated() - 첫 초기화, 레이아웃 확장 후 설정, 변수 초기화
   2. onDestroy() - 리소스 삭제될수 있음을 인식하고 메모리 정리 시작
4. 화면 회전시 모든 라이프사이클을 호출하며 활동 종료 후 다시 만듬
   - 활동은 기본값으로 시작
5. onSaveInstanceState() - 활동이 소멸되면 필요할수 있는 데이터를 저장하는데 사용되는 콜백
6. 