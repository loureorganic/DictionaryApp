package com.example.dictionaryapp.screens.home.ui

import ItemAdapter
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.databinding.FragmentSecondBinding
import com.example.dictionaryapp.model.Item
import com.example.dictionaryapp.model.WordModelItem
import java.io.IOException


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordInfo: WordModelItem
    var mediaPlayer: MediaPlayer? = null
    var id1: Int = 0
    private var itemClickListener: ItemClickListener? = null

    var listAudios = mutableListOf<Item>()
    var idStore: Int? = null

    private lateinit var itemAdapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val wordInformation = arguments?.getSerializable("wordItemInformation")
        wordInfo = wordInformation as WordModelItem

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtTitleItemMain.text = wordInfo.word
        //binding.txtDescriptionItemMain.text = wordInfo.
        wordInfo.phonetics?.map {
            if (!it.text.isNullOrEmpty()) {
                binding.txtDescription.text = it.text
                binding.txtPhonetic.visibility = View.VISIBLE
                binding.txtDescription.visibility = View.VISIBLE
            } else {
                binding.txtPhonetic.visibility = View.GONE
                binding.txtDescription.visibility = View.GONE
            }
        }
        wordInfo.meanings?.map {
            if (!it.definitions.isNullOrEmpty()) {
                it.definitions.map {
                    binding.txtDescriptionDefinition.text = it.definition
                    binding.txtDescriptionDefinition.visibility = View.VISIBLE
                    binding.txtDefinitions.visibility = View.VISIBLE
                }
            } else {
                binding.txtDescriptionDefinition.visibility = View.GONE
                binding.txtDefinitions.visibility = View.GONE
            }
        }
        if (!wordInfo.phonetics.isNullOrEmpty()) {
            playAudio(wordInfo.phonetics?.get(0)?.audio ?: "null")
        }

        wordInfo.phonetics?.map {
            it.audio
            id1++;
            listAudios.add(Item(id1, it.audio.toString(), "Audio $id", false))
        }
        listAudios
        setupAdapter(listAudios)
    }


    private fun setupAdapter(listAudios: List<Item>) {
        binding.idRvAudio.layoutManager = LinearLayoutManager(requireContext())
        itemAdapter = ItemAdapter(
            listAudios,
            requireContext(),
            itemClickListener
        )
        binding.idRvAudio.adapter = itemAdapter
        setupListenerAdapter()
    }

    private fun setupListenerAdapter() {
        itemClickListener = object : ItemClickListener {
            override fun onClick(item: Item) {
                binding.idRvAudio.post {
                    itemAdapter.notifyDataSetChanged()
                    idStore = item.id
                }
            }
        }
    }

    private fun playAudio(urlAudio: String) {
        binding.playAudioButton.setOnClickListener {

            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setAudioStreamType(AudioManager.STREAM_MUSIC)
            try {
                mediaPlayer!!.setDataSource(urlAudio)
                mediaPlayer!!.prepare()
                mediaPlayer!!.start()

            } catch (e: IOException) {
                e.printStackTrace()
            }
            Toast.makeText(context, "Audio Started", Toast.LENGTH_LONG).show()
        }
    }

}