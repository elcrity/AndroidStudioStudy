package com.example.android.marsphotos

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.PhotoGridAdapter

@BindingAdapter("imageUrl")//뷰 항목에 imageUrl속성이 있는 경우 이 결합 어댑터 실행
fun bindImage(imgView: ImageView, imgUrl : String?) {
    imgUrl?.let{//let은 객체가 null이 아닌 경우에만 실행
        //toUri -> Uri객체로 변환
        //buildUpon().scheme("https") HTTPS스키마 사용
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri){
            placeholder(R.drawable.loading_animation)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<MarsPhoto>?) {
    val adapter = recyclerView.adapter as PhotoGridAdapter
    adapter.submitList(data)

}