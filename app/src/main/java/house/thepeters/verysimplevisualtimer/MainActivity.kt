package house.thepeters.verysimplevisualtimer

import android.os.*
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import android.content.Intent
import androidx.core.view.isVisible
import android.media.RingtoneManager
import android.view.WindowManager



class MainActivity : AppCompatActivity() {

    private var progressBar: ProgressBar? = null
    private var isCancelled = false
    private var btnStart: Button? = null
    private var btnCancel: Button? = null
    private var etMinutes: EditText? = null
    private var etSeconds: EditText? = null
    private var maxMilli = 100L
    private var max = 100
    private var prog = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById<ProgressBar>(R.id.pbCountDown)
        btnStart = findViewById<Button>(R.id.btnStart)
        btnCancel = findViewById<Button>(R.id.btnCancel)
        etMinutes = findViewById<EditText>(R.id.etMinutes)
        etSeconds = findViewById<EditText>(R.id.etSeconds)

        btnStart!!.setOnClickListener {
            var minutes = 0
            var seconds = 0
            isCancelled = false
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)


            if (etMinutes?.text.toString() != ""){
                minutes = etMinutes?.text.toString().toInt()
            }
            if (etSeconds?.text.toString() != ""){
                seconds = etSeconds?.text.toString().toInt()
            }

            maxMilli = (((minutes.toLong()*60) + seconds.toLong()) * 1000)
            max = maxMilli.toInt()

            btnStart!!.isVisible = false
            etMinutes!!.isEnabled = false
            etSeconds!!.isEnabled = false
            btnCancel!!.isVisible = true
            startTimer(maxMilli)

        }
        btnCancel!!.setOnClickListener {
            isCancelled = true
            btnCancel!!.isVisible = false
            btnStart!!.isVisible = true
            progressBar!!.progress = 0
            etSeconds!!.isEnabled = true
            etMinutes!!.isEnabled = true
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.miLicenses -> startActivity(Intent(this, OssLicensesMenuActivity::class.java))
            R.id.miPrivacyPolicy -> startActivity(Intent(this,PrivacyPolicyActivity::class.java))
        }
        return true
    }

    private fun startTimer(tProg: Long){
        object : CountDownTimer(tProg, 10) {


            override fun onTick(millisUntilFinished: Long) {
                if (isCancelled){
                    cancel()
                } else {
                    prog = max - millisUntilFinished.toInt()
                    updateProgress()
                }
            }

            override fun onFinish() {
                progressBar!!.progress = max
                Toast.makeText(applicationContext, "Timer is Done!", Toast.LENGTH_LONG).show()
                btnCancel!!.isVisible = false
                btnStart!!.isVisible = true
                etMinutes!!.isEnabled = true
                etSeconds!!.isEnabled = true
                window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

                val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val r = RingtoneManager.getRingtone(applicationContext, defaultSoundUri)
                r.play()

            }
        }.start()
    }

    fun updateProgress(){
        progressBar!!.max = max
        progressBar!!.progress = prog
    }
}