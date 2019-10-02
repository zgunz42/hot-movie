package com.kangmicin.hotmovie

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kangmicin.hotmovie.model.TvShow

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [TvShowFragment.OnListFragmentInteractionListener] interface.
 */
class TvShowFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    private var tvShow: List<TvShow> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getParcelableArray(TV_SHOWS)?.forEach {
            tvShow = tvShow + it as TvShow
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tvshow_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = TvShowRecyclerViewAdapter(tvShow, listener)
            }
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: TvShow?)
    }

    companion object {

        // TODO: Customize parameter argument names
        private const val TV_SHOWS = "tv-shows"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(tvShows: List<TvShow>) =
            TvShowFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(TV_SHOWS, tvShows.toTypedArray())
                }
            }
    }
}
