package com.example.seriesapp.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.seriesapp.MyApplication
import com.example.seriesapp.R
 import com.example.seriesapp.viewmodels.SeriesViewModel
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SplashFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SplashFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: SeriesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SeriesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSeries()
        viewModel.currentDetailObj.observe(viewLifecycleOwner, Observer {
            it?.let {

                //first way of finding the latest object based on startyear
                if( it.data.results.isEmpty())
                    return@Observer

                it.data.results.map { obj -> obj.startYear }.maxOrNull()?.let { latestyear ->
                    val lastDateObj = it.data.results.find { it.startYear == latestyear }

                    if(lastDateObj !=null)
                      findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment(lastDateObj))
                }

                //second way of finding the latest object based on startyear

            /*  val res= it.data.results.sortedBy { it.startYear }
                if(res.isEmpty())
                    return@Observer

                val lastDateObj = res[res.size -1]
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToMainFragment(lastDateObj))   */



            }

        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }
}