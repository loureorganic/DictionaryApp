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
        /*setupList()
        onClickItem()*/
    }

    private fun setupList() {
        viewModelHome.getList()
        binding.errorTextInformation.visibility = View.GONE
        binding.homeRecyclerView.visibility = View.VISIBLE
        viewModelHome.wordResponse.observe(viewLifecycleOwner) {
            adapter.setDataList(it)
            adapter?.notifyDataSetChanged()
            setRecyclerView()
        }
    }

    fun teste(){
        binding.homeRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                //if (!infiniteList.hasNext()) return
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                if (lastVisibleItemPosition == adapter.itemCount - 1) {
                    currentPage++
                    //adapter.updateData( infiniteList.asIterable().take(currentPage * pageSize + pageSize))
                }
            }
        })
    }

    /* private fun loadMore() {
         rowsArrayList.add(null)
         adapter.notifyItemInserted(rowsArrayList.size - 1)

         val handler = Handler()
         handler.postDelayed({
             rowsArrayList.removeAt(rowsArrayList.size - 1)
             val scrollPosition = rowsArrayList.size
             adapter.notifyItemRemoved(scrollPosition)
             var currentSize = scrollPosition
             val nextLimit = currentSize + 10

             while (currentSize - 1 < nextLimit) {
                 rowsArrayList.add("Item $currentSize")
                 currentSize++
             }

             adapter.notifyDataSetChanged()
             isLoading = false
         }, 2000)
     }*/

    private fun onClickItem() {
        adapter.onItemClick = {
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