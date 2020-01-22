package com.example.goodnote.ui

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.goodnote.R
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    private lateinit var image: ImageView
    private lateinit var imageHidden: ImageView
    private lateinit var text: TextView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        image = findViewById(R.id.test_image)
        imageHidden = findViewById(R.id.test_image_hidden)
        btn = findViewById(R.id.test_btn)
        text = findViewById(R.id.test_text)
        text.text = "Testing custom matchers for image"

        btn.setOnClickListener {
            imageHidden.visibility = if (imageHidden.isVisible) View.INVISIBLE else View.VISIBLE
        }
    }
}
