package com.example.android.marsphotos

import android.media.Image
import android.view.View
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.android.marsphotos.network.MarsPhoto
import com.example.android.marsphotos.overview.MarsApiStatus
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

@BindingAdapter("marsApiStatus")//맞춤 속성 marsApiStatus를 매개변수로 전달하는 @BindingAdapter 주석을 메서드에 추가합니다.
fun bindStatus(statusImageView : ImageView, status : MarsApiStatus? ){
    when (status) {
        MarsApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        MarsApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        MarsApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        else -> {}
    }

}