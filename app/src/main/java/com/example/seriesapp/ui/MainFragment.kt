package com.example.seriesapp.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.addCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.movierama.ShowsDataSource.Companion.lastPageListener
import com.example.seriesapp.MobileNavigationArgs
import com.example.seriesapp.MyApplication
import com.example.seriesapp.R
import com.example.seriesapp.paging.PagedItemAdapter
import com.example.seriesapp.utils.ApiCallState
import com.example.seriesapp.viewmodels.SeriesViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainFragment : Fragment() {

    lateinit var manager: LinearLayoutManager
    lateinit var pagedAdapter: PagedItemAdapter

    private val args: MobileNavigationArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: SeriesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SeriesViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        observewViewmodel()

    }


    private fun setUpView() {
        manager = LinearLayoutManager(this.context)

        requireView().findViewById<RecyclerView>(R.id.recyclerview).apply {
            layoutManager = manager
            setHasFixedSize(true)
            pagedAdapter = PagedItemAdapter()
            adapter = pagedAdapter
        }

        viewModel.initFactory(args.obj.id.toString())

        activity?.onBackPressedDispatcher?.addCallback(this) {
            activity?.finish()
        }

        lastPageListener = {
            Snackbar.make(requireView(), "Last Page Loaded!", Snackbar.LENGTH_SHORT).show()
        }

        requireView().findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout).apply {
            setOnRefreshListener {
                try {
                    viewModel.itemPagedList?.value?.dataSource?.invalidate()
                    Handler(Looper.getMainLooper()).postDelayed({
                        this.isRefreshing = false
                    }, 1000)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }

    }

    private fun observewViewmodel() {
        viewModel.itemPagedList?.observe(viewLifecycleOwner, Observer {
            it?.let {
                pagedAdapter.submitList(it)
            }
        })

        viewModel.apiCallState.observe(viewLifecycleOwner, Observer {

            view?.findViewById<View>(R.id.no_internet_message)?.apply {
                when (it) {
                    is ApiCallState.NoInternetErrorMessage -> {
                            this.visibility = View.VISIBLE
                            findViewById<TextView>(R.id.noconnection).text = it.data
                    }
                    is ApiCallState.Success -> {
                        this.visibility = View.GONE
                    }
                    is ApiCallState.GeneralErrorMessage -> {
                        Snackbar.make(requireView(), it.data, Snackbar.LENGTH_SHORT).show()
                    }
                    is ApiCallState.NoResults -> {
                       view?.findViewById<ImageView>(R.id.no_results)?.visibility=View.VISIBLE
                    }
                    else -> {}

                }

                view?.findViewById<ProgressBar>(R.id.progressBar)?.visibility=View.GONE
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity?.application as MyApplication).appComponent.inject(this)
    }
}