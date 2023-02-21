
1. Navigation Graph (탐색 그래프)
- 탐색 그래프는 앱의 화면 간 전환을 정의하는 XML 파일
- 탐색 그래프는 각 화면을 Fragment 또는 Activity로 표현하고, 이들 간의 이동 경로와 이동 방법 등을 정의
- 탐색 그래프를 사용하면 앱의 전반적인 탐색 로직을 단일 파일로 표현할 수 있으며, 가시성과 관리성이 높아짐

2. NavHost (탐색 호스트)
- NavHost는 Navigation Graph에 정의된 Fragment 또는 Activity를 표시하는 데 사용되는 빈 컨테이너
- NavHost는 Navigation Graph에 정의된 각 목적지(화면)를 표시하고, 해당 목적지로 이동하는 데 필요한 애니메이션 및 트랜지션을 처리
- NavHost는 XML 또는 코드로 정의될 수 있으며, Fragment 또는 Activity에 배치

3. NavController (탐색 컨트롤러)
- NavController는 사용자 입력 및 앱 논리에 따라 탐색 그래프에서 다음으로 이동할 목적지를 관리하는 데 사용됨
- NavController는 NavHost에 의해 호스팅되며, 앱의 상태에 따라 Navigation Graph의 다른 목적지로 이동할 수 있습니다.
- NavController는 목적지 간의 이동 경로 및 방법을 결정하고, 목적지에 대한 인수(Arguments)를 전달하며, 애니메이션 및 트랜지션을 처리합니다.
