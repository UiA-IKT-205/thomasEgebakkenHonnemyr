package com.example.superpiano

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.superpiano.data.Note
import com.example.superpiano.databinding.FragmentPianoBinding
import kotlinx.android.synthetic.main.fragment_full_tone_piano_key.view.*
import kotlinx.android.synthetic.main.fragment_half_tone_piano_key.view.*
import kotlinx.android.synthetic.main.fragment_piano.view.*
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class PianoLayout : Fragment() {

    private var _binding:FragmentPianoBinding? = null
    private val binding get() = _binding!!

    private val fullTones = listOf("C", "D", "E", "F", "G", "A", "B", "C2", "D2", "E2", "F2", "G2")
    private val halfTones = listOf("C#", "D#", "E#", "F#", "G#", "A#", "B#")

    private var score:MutableList<Note> = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPianoBinding.inflate(layoutInflater)
        val view = binding.root

        val fm = childFragmentManager
        val ft = fm.beginTransaction()

        fullTones.forEach{
            val fullTonePianoKey = FullTonePianoKeyFragment.newInstance(it)
            var startPlay:Long = 0

            var fullToneStartTime:String = ""

            fullTonePianoKey.onKeyDown = {note ->
                startPlay = System.nanoTime()

                val currentTime = LocalDateTime.now()
                fullToneStartTime = currentTime.format(DateTimeFormatter.ISO_TIME)
                println("Piano key down $note. Started $fullToneStartTime")
            }

            fullTonePianoKey.onKeyUp = {
                var endPlay = System.nanoTime()

                var fullToneTotalTime:Double = 0.0
                var fullTonetime:Long = 0
                fullTonetime = endPlay - startPlay
                fullToneTotalTime = fullTonetime.toDouble() / 1000000000
                val note = Note(it, startPlay, fullToneTotalTime)
                score.add(note)
                println("Piano key up $note. Start $fullToneStartTime. Duration $fullToneTotalTime.")
            }

            ft.add(view.fullToneKeyLayout.id, fullTonePianoKey, "note_$it")
        }

        halfTones.forEach{
            val halfTonePianoKey = HalfTonePianoKeyFragment.newInstance(it)
            var startPlay:Long = 0
            var halfToneStartTime:String = ""

            halfTonePianoKey.onKeyDown = { note ->
                startPlay = System.nanoTime()
                val currentTime = LocalDateTime.now()
                halfToneStartTime = currentTime.format(DateTimeFormatter.ISO_TIME)
                println("Piano key down $note. Started $halfToneStartTime")
            }

            halfTonePianoKey.onKeyUp = {
                var endPlay = System.nanoTime()
                var halfToneTotalTime:Double = 0.0
                var halfToneTime:Long = 0

                halfToneTime = endPlay - startPlay
                halfToneTotalTime = halfToneTime.toDouble() / 1000000000

                val note = Note(it, startPlay, halfToneTotalTime)
                score.add(note)
                println("Piano key up $note. Start $halfToneStartTime. Duration $halfToneTotalTime")
            }

            ft.add(view.halfToneKeyLayout.id, halfTonePianoKey, "note_$it")
        }

        ft.commit()

        view.saveScoreBt.setOnClickListener{

            var fileName = view.fileNameTextEdit.text.toString()
            val path = this.activity?.getExternalFilesDir(null)
            val newScoreFile = File(path, fileName)

            when {
                score.count() == 0 -> Toast.makeText(activity, "Cant save when there is nothing to save m8", Toast.LENGTH_SHORT).show()
                fileName.isEmpty() -> Toast.makeText(activity, "Need to have a filename first m8", Toast.LENGTH_SHORT).show()
                path == null -> Toast.makeText(activity, "Path doesnt exist m8", Toast.LENGTH_SHORT).show()
                newScoreFile.exists() -> Toast.makeText(activity, "A score with that name already exists m8", Toast.LENGTH_SHORT).show()

                else -> {
                    fileName = "$fileName.music"
                    FileOutputStream(newScoreFile, true).bufferedWriter().use { writer ->
                        score.forEach{
                            writer.write("${it.toString()}\n")
                        }
                    }
                    Toast.makeText(activity, "nice one friend :)", Toast.LENGTH_SHORT).show()
                    score.clear()
                    FileOutputStream(newScoreFile).close()

                    print("Saved as $fileName at $path/$fileName")
                }
            }
        }

        return view
    }

}