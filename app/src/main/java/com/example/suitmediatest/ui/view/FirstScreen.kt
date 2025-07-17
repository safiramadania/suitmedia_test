package com.example.suitmediatest.ui.view

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import com.example.suitmediatest.databinding.ActivityFirstScreenBinding
import com.example.suitmediatest.ui.viewmodel.MainViewModel
import kotlin.jvm.java

class FirstScreen : AppCompatActivity() {

    private lateinit var binding: ActivityFirstScreenBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCheck.setOnClickListener {
            val sentence = binding.etSentence.text.toString()
            val isPalindrome = viewModel.isPalindrome(sentence)

            AlertDialog.Builder(this)
                .setTitle("Result")
                .setMessage(if (isPalindrome) "isPalindrome" else "not palindrome")
                .setPositiveButton("OK", null)
                .show()
        }

        binding.btnNext.setOnClickListener {
            val name = binding.etName.text.toString()
            if (name.isBlank()) {
                AlertDialog.Builder(this)
                    .setMessage("Name cannot be empty")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }

            val intent = Intent(this, SecondScreen::class.java)
            intent.putExtra("name", name)
            startActivity(intent)
        }
        window.statusBarColor = Color.TRANSPARENT
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        supportActionBar?.elevation = 0f
    }

}
