package loveq.rc.plaiddemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var findViewById = findViewById<LinearLayout>(R.id.ll_test)
        findViewById.setOnClickListener { Toast.makeText(this, "helllo", Toast.LENGTH_SHORT).show() }

    }
}
