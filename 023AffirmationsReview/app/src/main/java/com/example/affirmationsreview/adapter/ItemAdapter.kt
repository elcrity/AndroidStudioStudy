package com.example.affirmationsreview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.affirmationsreview.R
import com.example.affirmationsreview.model.Affirmation


//context - 문자열 리소스를 확인하는 방법에 관한 정보 전달
/*
RecyclerView는 항목 뷰와 직접 상호작용x, 대신 ViewHolders를 처리함
ViewHolder는 RecyclerView의 단일 목록 항목 뷰를 나타냄, 재사용 가능
목록 항목 레이아웃 안에 개별 뷰의 참조 보유(여기서는 textView?)
*/

class ItemAdapter(
    private val context: Context,
    private val dataset: List<Affirmation>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
        val imageView : ImageView = view.findViewById(R.id.item_image)
        val textView: TextView = view.findViewById(R.id.item_title)
    }
    //onCreateViewHolder() 메서드는 RecyclerView의 새 뷰 홀더를 만들기 위해 레이아웃 관리자를 호출합니다
    //parent - 항목 뷰가 하위 요소로 사용되서 연결되는 뷰 그룹, 여기서 상위요소는 RecyclerView
    //viewType - 동일한 RecyclerView에 항목 뷰 유형이 여러개일때 중요. 동일한 항목 뷰 유혀을 가진 뷰만 재활용 가능
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        //LayoutInflater - 메서드에서 제공된 컨텍스트(parent(ItemAdapter)의 parent)XML레이아웃을 뷰 객체의 계층 구조로 확장해줌.
        //inflate - 실제 목록 항목 뷰를 확장. 레이아웃 리소스 ID R.layout.list_item, 및 praent 뷰 그룹 전달, 마지막은 attachToRoot인수
        val adapterLayout = LayoutInflater.from(parent.context)
                            .inflate(R.layout.list_item, parent,false)

        return ItemViewHolder(adapterLayout)
    }

    //목록 항목 뷰의 콘텐츠를 바꾸기 위해 레이아웃 관리자를 호출
    //holder - onCreateViewHolder() 메서드에서 생성된 ItemViewHolder
    //position - 현재 항목 포지션을 나타냄. 이 위치를 기반으로 Affirmation개체를 찾음
    //item.stringResourceId를 호출하여 상응하는 문자열 리소스 Id를 찾음
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        holder.textView.text = context.resources.getString(item.stringResourceId)
        holder.imageView.setImageResource(item.imageResourceId)
    }
    //메서드의 데이터 세트 크기 반환
    override fun getItemCount() = dataset.size

}