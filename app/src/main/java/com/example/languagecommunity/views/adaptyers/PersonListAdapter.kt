package com.example.languagecommunity.views.adaptyers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.languagecommunity.R
import com.example.languagecommunity.data.model.CommunityPerson
import com.example.languagecommunity.databinding.ItemPersonBinding
import com.example.languagecommunity.utills.ItemDiffCommunityPerson
import com.example.languagecommunity.utills.OnItemClickCallback

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */
class PersonListAdapter(
    private val mCallBack: OnItemClickCallback<CommunityPerson>? = null
): ListAdapter<CommunityPerson, CommunityPersonViewHolder>(ItemDiffCommunityPerson) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityPersonViewHolder {
        return CommunityPersonViewHolder.createViewHolder(parent, mCallBack)
    }

    override fun onBindViewHolder(holder: CommunityPersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

class CommunityPersonViewHolder(
    private val binding: ItemPersonBinding,
    private val mCallBack: OnItemClickCallback<CommunityPerson>? = null
): RecyclerView.ViewHolder(binding.root) {

    init {
        binding.imgLike.setOnClickListener {
            mModel?.let { it1 -> mCallBack?.onClick(it1) }
        }
    }

    private var mModel: CommunityPerson? = null

    fun bind(item: CommunityPerson?) {
        Glide.with(itemView.context)
            .load(item?.pictureUrl)
            .placeholder(R.drawable.progress_animation)
            .into(binding.imgProfileImage)
        mModel = item
        binding.tvPersonName.text = item?.firstName
        binding.tvTopic.text = item?.topic
        binding.tvNativeLang.text = item?.natives?.get(0)
        binding.tvLearningLang.text = item?.learns?.get(0)
        if (item?.referenceCnt == 0) {
            binding.tvNewReference.visibility = View.VISIBLE
            binding.tvReferenceCount.visibility = View.GONE
        } else {
            binding.tvNewReference.visibility = View.GONE
            binding.tvReferenceCount.visibility = View.VISIBLE
            binding.tvReferenceCount.text = item?.referenceCnt.toString()
        }
        if (item?.isLiked == true) {
            binding.imgLike.setImageResource(R.drawable.ic_baseline_thumb)
        } else {
            binding.imgLike.setImageResource(R.drawable.ic_outline_thumb)
        }
    }


    companion object {
        fun createViewHolder(
            parent: ViewGroup,
            mCallBack: OnItemClickCallback<CommunityPerson>? = null
        ): CommunityPersonViewHolder {
            val view = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return CommunityPersonViewHolder(view, mCallBack)
        }
    }

}