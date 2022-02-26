package house.thepeters.verysimplevisualtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val web = findViewById<WebView>(R.id.webView)
        web.loadUrl("https://github.com/bpeters504/VerySimpleVisualTimer/blob/master/PRIVACY.md")

    }
}