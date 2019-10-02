package com.kangmicin.hotmovie

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kangmicin.hotmovie.TvShowFragment.OnListFragmentInteractionListener
import com.kangmicin.hotmovie.model.TvShow
import kotlinx.android.synthetic.main.fragment_tvshow.view.*

/**
 * [RecyclerView.Adapter] that can display a [TvShow] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class TvShowRecyclerViewAdapter(
    private val mValues: List<TvShow>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TvShowRecyclerViewAdapter.ViewHolder>() {

    private val mOnClickListener: View.OnClickListener

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as TvShow
            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_tvshow, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: TvShow = mValues[position]
        val posterImage = Utils.roundedImage(holder.mView.context, item.poster, R.dimen.corner)

        holder.mTitle.text = item.title
        holder.mContent.text = item.plot
        holder.mPoster.setImageDrawable(posterImage)

        with(holder.mView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mTitle: TextView = mView.tvshow_title
        val mPoster: ImageView = mView.tvshow_poster
        val mContent: TextView = mView.tvshow_plot

        override fun toString(): String {
            return super.toString() + """ '""" + mTitle.text + """'"""
        }
    }
}
