package com.example.dictionaryapp.screens.home.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.FragmentFirstBinding
import com.example.dictionaryapp.screens.home.ImageLoader
import com.example.dictionaryapp.screens.home.RecyclerViewAdapter
import com.example.dictionaryapp.screens.home.viewmodel.ViewModelHome
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!

    var adapter: RecyclerViewAdapter? = null




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

        setupList()
        onClickItem()
    }

    private fun setupList() {
        viewModelHome.getList()
        viewModelHome.wordResponse.observe(viewLifecycleOwner) {
            adapter?.setDataList(it)
            adapter?.notifyDataSetChanged()
            setRecyclerView()
        }
    }

    private fun onClickItem() {
        adapter?.onItemClick ={
            val bundle = bundleOf("wordItemInformation" to it)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
    }


    private fun setRecyclerView() {
        binding.homeRecyclerView.setHasFixedSize(true)
        binding.homeRecyclerView.layoutManager = GridLayoutManager(context, 2)
        binding.homeRecyclerView.adapter = adapter
    }


}