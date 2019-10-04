package com.kangmicin.hotmovie

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
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
 * [ListItemFragment.OnListFragmentInteractionListener] interface.
 */
class ListItemFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    private var items: List<Parcelable> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getParcelableArray(LIST_ITEM)?.forEach {
            items = items + it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lists_item, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = ListItemAdapter(items, listener)
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
        fun onListFragmentInteraction(item: Parcelable?)
    }

    companion object {

        private const val LIST_ITEM = "LIST_ITEM"

        @JvmStatic
        fun newInstance(tvShows: List<Parcelable>) =
            ListItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArray(LIST_ITEM, tvShows.toTypedArray())
                }
            }
    }
}
