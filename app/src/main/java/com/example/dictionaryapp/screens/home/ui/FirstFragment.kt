package com.example.dictionaryapp.screens.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.FragmentFirstBinding
import com.example.dictionaryapp.screens.home.RecyclerViewAdapter
import com.example.dictionaryapp.screens.home.viewmodel.ViewModelHome
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    var currentPage = 0
    var adapter = RecyclerViewAdapter()
    var currentValue = 0

    @Inject
    lateinit var viewModelHome: ViewModelHome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = RecyclerViewAdapter()

        (activity as AppCompatActivity).supportActionBar?.hide();
        setupList(currentValue.toString())
        onClickItem()
        teste()
    }

    private fun setupList(startAt: String) {
        viewModelHome.getList(startAt)
        viewModelHome.wordResponse.observe(viewLifecycleOwner) {
            adapter.addData(it)
            adapter.notifyDataSetChanged()
            setRecyclerView()
        }
    }

    fun teste() {
        binding.homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition == adapter.itemCount - 1) {
                    currentPage++
                    currentValue += 25
                    setupList(currentValue.toString())
                }
            }
        })
    }



    private fun onClickItem() {
        adapter.onItemClick = {
            val bundle = bundleOf("wordItemInformation" to it)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
    }


    private fun setRecyclerView() {
        binding.homeRecyclerView.setHasFixedSize(true)
        val start = adapter.itemCount
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)
        val currentPosition = (binding.homeRecyclerView.layoutManager as LinearLayoutManager).findFirstCompletelyVisibleItemPosition()
        binding.homeRecyclerView.adapter = adapter
        binding.homeRecyclerView.post {
            binding.homeRecyclerView.scrollToPosition(if (currentPosition == -1) start else currentPosition)
        }
    }


}