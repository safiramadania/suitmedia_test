package com.example.suitmediatest.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.suitmediatest.databinding.ActivitySecondScreenBinding

class SecondScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySecondScreenBinding

    private val userLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val selectedUserName = result.data?.getStringExtra("selected_user")
            binding.tvSelectedUser.text = selectedUserName ?: "-"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val passedName = intent.getStringExtra("name")
        binding.tvName.text = passedName ?: "(No name)"

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnChooseUser.setOnClickListener {
            val intent = Intent(this, ThirdScreen::class.java)
            userLauncher.launch(intent)
        }
    }
}
