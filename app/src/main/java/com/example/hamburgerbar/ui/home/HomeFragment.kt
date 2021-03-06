package com.example.hamburgerbar.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hamburgerbar.R
import com.example.hamburgerbar.databinding.FragmentHomeBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

class HomeFragment : Fragment() {

    val _voice_btn = view?.findViewById<ImageButton>(R.id.et_text_input)
    val _send_btn = view?.findViewById<FloatingActionButton>(R.id.send_btn)
    val _et_text_input = view?.findViewById<EditText>(R.id.et_text_input)

    //SST-Func
    companion object {
        private const val REQUEST_CODE_STT = 1
    }
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(requireActivity(),
            TextToSpeech.OnInitListener { status ->
                if (status == TextToSpeech.SUCCESS) {
                    textToSpeechEngine.language = Locale.JAPANESE
                }
            })
    }
    //</SST>

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val textView: TextView = binding.textHome

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
            textView.movementMethod = ScrollingMovementMethod()

        })
        return root
    }

    //<SST func>
    /*?????????????????????setOnClickListener???*/
    /*
    override fun onStart() {
        super.onStart()
        _send_btn?.setOnClickListener {
            val text = _et_text_input?.text.toString().trim()
            if (text.isNotEmpty()) {
                textToSpeechEngine.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts1")
            } else {
                Toast.makeText(requireActivity(), "?????????????????????????????????", Toast.LENGTH_LONG).show()
            }
        }
        //</SST func>

    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let {
                        val recognizedText = it[0]
                        _et_text_input?.setText(recognizedText)
                    }
                }
            }
        }
    }

    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}