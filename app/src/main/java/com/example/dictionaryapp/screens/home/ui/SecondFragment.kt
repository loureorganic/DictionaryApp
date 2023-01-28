package com.example.dictionaryapp.screens.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dictionaryapp.databinding.FragmentSecondBinding
import com.example.dictionaryapp.model.WordModelItem


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordInfo: WordModelItem

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val wordInformation = arguments?.getSerializable("wordItemInformation")
        wordInfo = wordInformation as WordModelItem
        wordInfo
        binding.textviewSecond.text = wordInfo.word

    }

}