package com.kangmicin.hotmovie

import android.os.Parcelable
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kangmicin.hotmovie.ListItemFragment.OnListFragmentInteractionListener
import com.kangmicin.hotmovie.model.Movie
import com.kangmicin.hotmovie.model.TvShow
import kotlinx.android.synthetic.main.item_card.view.*

/**
 * [RecyclerView.Adapter] that can display a [TvShow] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class ListItemAdapter(
    private val mValues: List<Parcelable>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<ListItemAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            mListener?.onListFragmentInteraction(v.tag as Parcelable)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_card, parent, false)
        return ViewHolder(view)
    }

    private fun bindToModel(model: TvShow, holder: ViewHolder) {
        val posterImage = Utils.roundedImage(holder.mView.context, model.poster, R.dimen.corner)

        holder.mTitle.text = model.title
        holder.mContent.text = model.plot
        holder.mPoster.setImageDrawable(posterImage)

        with(holder.mView.tvshow_item) {
            tag = model
            setOnClickListener(mOnClickListener)
        }
    }

    private fun bindToModel(model: Movie, holder: ViewHolder) {
        val posterImage = Utils.roundedImage(holder.mView.context, model.poster, R.dimen.corner)

        holder.mTitle.text = model.title
        holder.mContent.text = model.plot
        holder.mPoster.setImageDrawable(posterImage)

        with(holder.mView.tvshow_item) {
            tag = model
            setOnClickListener(mOnClickListener)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]

        if (item is Movie) {
            bindToModel(item, holder)
        }

        if (item is TvShow) {
            bindToModel(item, holder)
        }

    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitle: TextView = mView.item_title
        val mPoster: ImageView = mView.item_poster
        val mContent: TextView = mView.item_subtitle

        override fun toString(): String {
            return super.toString() + """ '""" + mTitle.text + """'"""
        }
    }
}
