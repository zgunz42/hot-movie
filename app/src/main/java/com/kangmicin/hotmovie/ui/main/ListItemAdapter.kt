package com.kangmicin.hotmovie.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kangmicin.hotmovie.R
import com.kangmicin.hotmovie.data.entity.DisplayData
import com.kangmicin.hotmovie.databinding.ItemCardBinding

class ListItemAdapter(
    private var mValues: List<DisplayData>,
    private val mListener: View.OnClickListener?
) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.click = mListener
        holder.binding?.item = mValues[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mValues.size

    fun updateAdapter(data: List<DisplayData>) {
        mValues = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val binding: ItemCardBinding? = DataBindingUtil.bind(mView)

        init {
            mView.tag = binding
        }
    }
}
