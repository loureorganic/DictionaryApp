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
import com.example.dictionaryapp.R
import com.example.dictionaryapp.databinding.FragmentFirstBinding
import com.example.dictionaryapp.screens.home.RecyclerViewAdapter
import com.example.dictionaryapp.screens.home.viewmodel.ViewModelHome
import com.example.dictionaryapp.utils.State
import com.example.dictionaryapp.utils.errorHandling
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
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

        (activity as AppCompatActivity).supportActionBar?.hide();
        setupList()
        onClickItem()
    }

    private fun setupList() {
        viewModelHome.getList()
        viewModelHome.wordResponseState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    binding.errorTextInformation.visibility = View.GONE
                    binding.homeRecyclerView.visibility = View.VISIBLE
                    viewModelHome.wordResponse.observe(viewLifecycleOwner) {
                        adapter?.setDataList(it)
                        adapter?.notifyDataSetChanged()
                        setRecyclerView()
                    }
                }

                is State.Error -> {
                    //val errorResponse = errorHandling.convertErrorBody(state.throwable as HttpException)
                    //val errorResponseMessage = errorResponse?.error?.message.toString()
                    binding.errorTextInformation.visibility = View.VISIBLE
                    binding.homeRecyclerView.visibility = View.GONE
                }

            }
        }
    }

    private fun onClickItem() {
        adapter?.onItemClick = {
            val bundle = bundleOf("wordItemInformation" to it)
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }
    }


    private fun setRecyclerView() {
        binding.homeRecyclerView.setHasFixedSize(true)
        binding.homeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.homeRecyclerView.adapter = adapter
    }


}