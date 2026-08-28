package com.example.touchlock

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.infoText).text =
            "1. Нажмите кнопку ниже.\n" +
            "2. Найдите \"TouchLock\" в списке специальных возможностей.\n" +
            "3. Включите его.\n\n" +
            "После этого:\n" +
            "* — заблокировать сенсор\n" +
            "# — разблокировать сенсор"

        findViewById<Button>(R.id.openSettingsButton).setOnClickListener {
            startActivity(Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS))
        }
    }
}
