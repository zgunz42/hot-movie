package com.kangmicin.hotmovie

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.kangmicin.hotmovie.model.Movie

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [MovieListFragment.OnListFragmentInteractionListener] interface.
 */
class MovieListFragment : Fragment() {

    private var listener: OnListFragmentInteractionListener? = null
    private var movies: List<Movie> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movie_list, container, false)

        arguments?.getParcelableArray("MOVIES")?.forEach {
            movies = movies + it as Movie
        }

        Log.i("TOPMOVIE", movies.size.toString())

        if (view is ListView) {
            with(view) {
                adapter = MovieAdapter(movies, context, listener)
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
        fun onListFragmentInteraction(item: Movie?)
    }
}
