package com.example.android.marsphotos.overview

import android.os.Build.VERSION_CODES.M
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AbsListView.RecyclerListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsphotos.databinding.GridViewItemBinding
import com.example.android.marsphotos.network.MarsPhoto

class PhotoGridAdapter : ListAdapter<MarsPhoto,
        PhotoGridAdapter.MarsPhotoViewHolder>(DiffCallback) {

    class MarsPhotoViewHolder(private var binding://MarsPhoto를 레이아웃에 결합하기 위한 GridViewItemBinding 변수
                            GridViewItemBinding):
    RecyclerView.ViewHolder(binding.root){//기본 ViewHolder 클래스는 생성자에 뷰가 있어야함
        fun bind(MarsPhoto: MarsPhoto){//MarsPhoto 객체를 인수로 사용하고 binding.property를 이 객체로 설정하는 bind() 메서드
            binding.photo = MarsPhoto
            binding.executePendingBindings()// 업데이트가 즉시 실행
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPhotoViewHolder {
        return MarsPhotoViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPhotoViewHolder, position: Int) {
        val marsPhoto = getItem(position)//현재 RecyclerView 위치와 연결된 MarsPhoto객체를 가져옴
        holder.bind(marsPhoto)//이 속성을 MarsPhotoViewHolder의 bind()메서드에 전달
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MarsPhoto>() {
        //DiffCallback 객체는 비교할 일반 객체 유형 MarsPhoto로 DiffUtil.ItemCallback을 확장,  두 화성 사진 객체를 비교
        override fun areItemsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {//두 객체가 동일한지 확인하는 메서드, 새 MarsPhoto와 이전 MarsPhoto객체가 동일한지 확인
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MarsPhoto, newItem: MarsPhoto): Boolean {//두 항목의 데이터가 동일한지 확인, oldItem과 newItem의 URL을 비교
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }

    }
}

