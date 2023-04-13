package com.example.android.marsphotos.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL =
    "https://android-kotlin-fun-mars-server.appspot.com"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    // 웹 서비스의 기본 URI 및 변환기 팩토리가 있어야 웹 서비스의 API 빌드 가능
    // 변환기는 웹 서비스에서 얻은 데이터로 해야 할 일을 Retrofit에 알림
    // Retrofit에서 웹 서비스의 JSON 응답을 가져와 String으로 반환
    .baseUrl(BASE_URL)
    // 기본 URL 추가
    .build()
    //Retrofit 객체를 생성

interface MarsApiService {
    @GET("photos")//@GET 주석을 사용하여 Retrofit에 GET 요청임을 알리고 엔드포인트 지정, 이 경우 엔드포인트는 photo
    //정지함수 suspend
    suspend fun getPhoto(): List<MarsPhoto>
}

object MarsApi{
    //싱글톤 객체, retrofit 객체에서 create()함수를 호출시 리소스가 많이 듦. Retrofit API 서비스 인스턴스가 한나만 필요하기 때문에
    //객체 선언으로 나머지 부분에 서비스 노출
    //MarsApiService 유형의 지연초기화 Retrofit 객체 속성 retrofitService 추가
    val retrofitService : MarsApiService by lazy {
        retrofit.create(MarsApiService::class.java)//MarsApiService 인터페이스와 retrofit.create 메서드를 사용하여 retrofitService 변수 초기화
    }
}