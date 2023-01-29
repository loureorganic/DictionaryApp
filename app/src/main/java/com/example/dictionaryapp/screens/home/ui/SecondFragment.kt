package com.example.dictionaryapp.screens.home.ui

import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.dictionaryapp.databinding.FragmentSecondBinding
import com.example.dictionaryapp.model.WordModelItem
import java.io.IOException


class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!
    private lateinit var wordInfo: WordModelItem
    var mediaPlayer: MediaPlayer? = null

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
        playAudio(wordInfo.phonetics?.get(0)?.audio?: "null")
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