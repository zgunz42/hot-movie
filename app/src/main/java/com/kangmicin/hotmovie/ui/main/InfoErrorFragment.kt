package com.kangmicin.hotmovie.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.kangmicin.hotmovie.R
import kotlinx.android.synthetic.main.fragment_info_error.view.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InfoErrorFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [InfoErrorFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InfoErrorFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var message: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            message = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info_error, container, false)

        view.subtitle.text = message ?: view.subtitle.text
        view.retry_action.setOnClickListener {
            listener?.onRetryInteraction()
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
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
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onRetryInteraction()
    }

    companion object {

        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private const val ARG_PARAM1 = "message"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment InfoErrorFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            InfoErrorFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
