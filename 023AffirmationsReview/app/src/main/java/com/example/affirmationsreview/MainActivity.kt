package com.example.affirmationsreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmationsreview.adapter.ItemAdapter
import com.example.affirmationsreview.data.Datasource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myDataset = Datasource().loadAffirmations()
        val recyclerView : RecyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        //this - 이 활동의 컨텍스트, 그리고 myDataset의 확인값을 받음
        //ItemAdapter객체를 recyclerView의 adapter속성에 할당
        recyclerView.adapter = ItemAdapter(this, myDataset)
        recyclerView.setHasFixedSize(true)//성능 개선용, 콘텐츠 변경해도 RecyclerView의 크기가 변경되지 않는 경우만 사용
    }
}