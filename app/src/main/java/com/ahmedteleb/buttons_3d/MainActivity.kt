package com.ahmedteleb.buttons_3d

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmedteleb.buttons3d.Button3d

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val disabledBtn = findViewById<Button3d>(com.ahmedteleb.buttons3d.R.id.disabled_button)
        disabledBtn.setButtonColor(getResources().getColor(R.color.purple_500));
        disabledBtn.setShadowColor(getResources().getColor(R.color.purple_200));
        disabledBtn.setShadowEnabled(true)
        disabledBtn.setShadowHeight(5)
        disabledBtn.setCornerRadius(5)

    }
}