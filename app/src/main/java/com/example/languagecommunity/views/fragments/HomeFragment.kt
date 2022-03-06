package com.example.languagecommunity.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagecommunity.R
import com.example.languagecommunity.data.model.CommunityPerson
import com.example.languagecommunity.data.network.Resource
import com.example.languagecommunity.databinding.FragmentHomeBinding
import com.example.languagecommunity.di.Injection
import com.example.languagecommunity.utills.Constants
import com.example.languagecommunity.utills.OnItemClickCallback
import com.example.languagecommunity.viewModels.HomeViewModel
import com.example.languagecommunity.viewModels.ViewModelFactory
import com.example.languagecommunity.views.adaptyers.PersonListAdapter


class HomeFragment : Fragment(), OnItemClickCallback<CommunityPerson> {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var mViewModel: HomeViewModel

    private var fetchedPage = 1

    private var mAdapter: PersonListAdapter = PersonListAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initViewModel()
        getPersonsFromDB()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                this,
                Injection.provideCommunityRepository()
            )
        )[HomeViewModel::class.java]
    }

    private fun fetchPersons() {
        mViewModel.fetchPersonList().observe(viewLifecycleOwner, { resources ->
            resources?.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        _binding?.progressBar?.visibility = View.GONE
                    }
                    Resource.Status.ERROR -> {
                        _binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            resources.data?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    Resource.Status.LOADING -> {
                        _binding?.progressBar?.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun getPersonsFromDB() {
        mViewModel.getSavedPersonList().observe(viewLifecycleOwner, { list ->
            if (list.isEmpty()) {
                fetchPersons()
            } else {
                mAdapter.submitList(list)
            }
        })
    }

    private fun initView() {
        _binding?.apply {
            toolBar.btnBack.visibility = View.GONE
            toolBar.tvToolbarTitle.text = getString(R.string.community)
            val layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            rvPersons.layoutManager = layoutManager
            rvPersons.adapter = mAdapter
            rvPersons.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    RecyclerView.VERTICAL
                )
            )
            rvPersons.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == mAdapter.currentList.size - 1) {
                        if (fetchedPage + 1 <= Constants.totalPage) {
                            fetchPersons()
                        }
                    }
                }
            })
        }
    }

    override fun onClick(model: CommunityPerson) {
        if (model.isLiked) {
            model.id?.let { mViewModel.updatePersonLike(it, false) }
        } else {
            model.id?.let { mViewModel.updatePersonLike(it, true) }
        }
    }

}