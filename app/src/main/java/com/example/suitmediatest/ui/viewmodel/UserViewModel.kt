package com.example.suitmediatest.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.suitmediatest.data.model.User
import com.example.suitmediatest.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {

    private val _userList = MutableLiveData<List<User>>()
    val userList: LiveData<List<User>> get() = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _isEmpty = MutableLiveData<Boolean>()
    val isEmpty: LiveData<Boolean> get() = _isEmpty

    private var currentPage = 1
    private val perPage = 6
    private var isLastPage = false
    private val loadedUsers = mutableListOf<User>()

    fun loadUsers(refresh: Boolean = false) {
        if (refresh) {
            currentPage = 1
            isLastPage = false
            loadedUsers.clear()
        }

        if (isLastPage) return

        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = UserRepository.getUsers(currentPage, perPage)
                val newUsers = response.data

                if (newUsers.isEmpty() && loadedUsers.isEmpty()) {
                    _isEmpty.value = true
                    isLastPage = true
                } else {
                    _isEmpty.value = false
                    loadedUsers.addAll(newUsers)
                    _userList.value = loadedUsers
                    currentPage++
                }


            } catch (e: Exception) {
                _isEmpty.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
