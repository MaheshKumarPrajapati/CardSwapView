package com.mahesh_prajapati.itunes.utils

import androidx.recyclerview.widget.DiffUtil
import com.mahesh_prajapati.itunes.storage.model.ResultX

class SpotDiffCallback(
    private val old: List<ResultX>,
    private val new: List<ResultX>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].login!!.username == new[newPosition].login!!.username
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].login!!.username == new[newPosition].login!!.username
    }

}
