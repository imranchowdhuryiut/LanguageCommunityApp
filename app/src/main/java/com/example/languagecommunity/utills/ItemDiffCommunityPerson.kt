package com.example.languagecommunity.utills

import androidx.recyclerview.widget.DiffUtil
import com.example.languagecommunity.data.model.CommunityPerson

/**
 * Created by Imran Chowdhury on 3/1/2022.
 */
object ItemDiffCommunityPerson: DiffUtil.ItemCallback<CommunityPerson>() {
    override fun areItemsTheSame(oldItem: CommunityPerson, newItem: CommunityPerson): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CommunityPerson, newItem: CommunityPerson): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.firstName == newItem.firstName
    }
}