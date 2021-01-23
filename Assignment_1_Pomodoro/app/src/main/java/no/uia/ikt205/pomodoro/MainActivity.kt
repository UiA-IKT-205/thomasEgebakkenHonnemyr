package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer:CountDownTimer
    lateinit var startButton:Button
    lateinit var thirtyMinButton:Button
    lateinit var sixtyMinButton:Button
    lateinit var ninetyMinButton:Button
    lateinit var twoHourButton:Button
    lateinit var coutdownDisplay:TextView

    var timeToCountDownInMs = 5000L
    val timeTicks = 1000L
    var isCounting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           if(!isCounting){
               startCountDown(it)
           }
           isCounting = true
       }
       thirtyMinButton = findViewById<Button>(R.id.startThirtyMinCountdown)
       thirtyMinButton.setOnClickListener(){
           if (isCounting){
                resetTimer()
           }
           timeToCountDownInMs = 1800000L
           updateCountDownDisplay(timeToCountDownInMs)
       }
       sixtyMinButton = findViewById(R.id.startSixtyMinCountdown)
       sixtyMinButton.setOnClickListener(){
           if (isCounting){
               resetTimer()
           }
           timeToCountDownInMs = 3600000L
           updateCountDownDisplay(timeToCountDownInMs)
       }
       ninetyMinButton = findViewById(R.id.startNinetyMinCountdown)
       ninetyMinButton.setOnClickListener(){
           if (isCounting){
               resetTimer()
           }
           timeToCountDownInMs = 5400000L
           updateCountDownDisplay(timeToCountDownInMs)
       }
       twoHourButton = findViewById(R.id.startTwoHourCountdown)
       twoHourButton.setOnClickListener(){
           if (isCounting){
               resetTimer()
           }
           timeToCountDownInMs = 7200000L
           updateCountDownDisplay(timeToCountDownInMs)
       }

       coutdownDisplay = findViewById<TextView>(R.id.countDownView)

    }

    fun startCountDown(v: View){
        timer = object : CountDownTimer(timeToCountDownInMs,timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity,"Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                isCounting = false
            }

            override fun onTick(millisUntilFinished: Long) {
               updateCountDownDisplay(millisUntilFinished)
            }
        }

        timer.start()
    }

    fun updateCountDownDisplay(timeInMs:Long){
        coutdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)
    }

    fun resetTimer(){
        timer.cancel()
        isCounting = false
    }

}