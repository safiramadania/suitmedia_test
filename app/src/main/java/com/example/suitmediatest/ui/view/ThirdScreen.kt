package com.example.suitmediatest.ui.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.suitmediatest.databinding.ActivityThirdScreenBinding
import com.example.suitmediatest.ui.viewmodel.UserViewModel

class ThirdScreen : AppCompatActivity() {

    private lateinit var binding: ActivityThirdScreenBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var adapter: UserAdapter
    private var isLoadingNextPage = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        setupRecyclerView()
        observeViewModel()
        setupPullToRefresh()

        viewModel.loadUsers()
    }

    private fun setupRecyclerView() {
        adapter = UserAdapter { user ->
            val resultIntent = Intent().apply {
                putExtra("selected_user", "${user.first_name} ${user.last_name}")
            }
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.rvUsers.layoutManager = LinearLayoutManager(this)
        binding.rvUsers.adapter = adapter

        binding.rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(rv: RecyclerView, dx: Int, dy: Int) {
                if (!rv.canScrollVertically(1) && !isLoadingNextPage) {
                    isLoadingNextPage = true
                    viewModel.loadUsers()
                }
            }
        })
    }

    private fun observeViewModel() {
        viewModel.userList.observe(this) { users ->
            adapter.setData(users)
            isLoadingNextPage = false

            binding.tvEmptyState.visibility = if (users.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoading.observe(this) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }
    }


    private fun setupPullToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.loadUsers(refresh = true)
        }
    }
}
