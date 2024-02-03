package com.app.globalserverchatapp.presentation.username

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserNameViewModel @Inject constructor() : ViewModel() {
    private val _usernameText = mutableStateOf("")
    val userNameText = _usernameText

    private val _onJoinChat = MutableSharedFlow<String>()
    val onJoinChat = _onJoinChat.asSharedFlow()

    fun onUserNameChanged(userName:String){
        _usernameText.value = userName
    }

    fun onJoinClicked() = viewModelScope.launch{
        if(userNameText.value.isNotBlank()){
            _onJoinChat.emit(userNameText.value)
        }
    }
}