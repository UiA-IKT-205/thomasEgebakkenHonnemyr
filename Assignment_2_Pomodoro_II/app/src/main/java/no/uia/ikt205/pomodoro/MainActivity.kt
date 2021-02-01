package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime
import java.lang.NullPointerException

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var pauseTimer:CountDownTimer
    lateinit var startButton:Button
    lateinit var countdownDisplay:TextView
    lateinit var timeSeekbar:SeekBar
    lateinit var pauseSeekbar:SeekBar
    lateinit var repetitionEditText:EditText

    var timeToCountDownInMs = 5000L
    val timeTicks = 1000L
    var pauseTime = 5000L
    var repetitions: Int = 0

    var isCounting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           if (!isCounting){
               isCounting = true
               repetitions = repetitionEditText.text.toString().toInt()
               startCountDown(it)

               Toast.makeText(this@MainActivity,"Husk å legge til hvor mange repetisjoner du vil ha", Toast.LENGTH_SHORT).show()
           }
       }

       repetitionEditText = findViewById(R.id.repetitionEdittext)

       countdownDisplay = findViewById<TextView>(R.id.countDownView)

       timeSeekbar = findViewById(R.id.timeSeekbar)
       timeSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
           override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               if (!isCounting){
                   timeToCountDownInMs = progress * 60 * 1000L
                   updateCountDownDisplay(timeToCountDownInMs)
               }
           }

           override fun onStartTrackingTouch(seekBar: SeekBar?) {
               Toast.makeText(this@MainActivity,"Pausetid", Toast.LENGTH_SHORT).show()
           }

           override fun onStopTrackingTouch(seekBar: SeekBar?) {
               Toast.makeText(this@MainActivity,"Pausetid", Toast.LENGTH_SHORT).show()
           }
       })

       pauseSeekbar = findViewById(R.id.pauseSeekbar)
       pauseSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
           override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
               if (!isCounting){
                   pauseTime = progress * 60 * 1000L
                   updateCountDownDisplay(pauseTime)
               }
           }

           override fun onStartTrackingTouch(seekBar: SeekBar?) {
               Toast.makeText(this@MainActivity,"Pausetid", Toast.LENGTH_SHORT).show()
           }

           override fun onStopTrackingTouch(seekBar: SeekBar?) {
               Toast.makeText(this@MainActivity,"Pausetid", Toast.LENGTH_SHORT).show()
           }
       })
    }

    fun startCountDown(v: View){

        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Pausetid", Toast.LENGTH_SHORT).show()
                isCounting = false
                repetitions--
                if (repetitions > 0){
                    startPauseCountDown(v)
                }
                Toast.makeText(this@MainActivity,"Gratulerer du fullførte før appen crashet :)", Toast.LENGTH_SHORT).show()

            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun startPauseCountDown(v: View){

        pauseTimer = object : CountDownTimer(pauseTime, timeTicks){
            override fun onFinish() {
                startCountDown(v)
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }

        pauseTimer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

}