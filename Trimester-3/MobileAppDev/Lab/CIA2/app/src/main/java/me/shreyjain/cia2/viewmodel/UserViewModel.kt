package me.shreyjain.cia2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import me.shreyjain.cia2.data.AppDatabase
import me.shreyjain.cia2.data.User
import me.shreyjain.cia2.data.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository
    
    private val _registrationState = MutableStateFlow<RegistrationState>(RegistrationState.Idle)
    val registrationState: StateFlow<RegistrationState> = _registrationState.asStateFlow()
    
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    // Add a new state for user details
    private val _userDetails = MutableStateFlow<User?>(null)
    val userDetails: StateFlow<User?> = _userDetails

    init {
        val userDao = AppDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            _registrationState.value = RegistrationState.Loading
            
            // Check if user with this email already exists
            val existingUser = repository.getUserByEmail(email)
            if (existingUser != null) {
                _registrationState.value = RegistrationState.Error("Email already registered")
                return@launch
            }
            
            // Create and insert new user
            val user = User(name = name, email = email, password = password)
            val userId = repository.insertUser(user)
            
            if (userId > 0) {
                _registrationState.value = RegistrationState.Success
            } else {
                _registrationState.value = RegistrationState.Error("Failed to register user")
            }
        }
    }

    fun attemptLogin(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading
            
            // Get user by email and password
            val user = repository.getUserByEmailAndPassword(email, password)
            
            if (user != null) {
                _loginState.value = LoginState.Success(user)
            } else {
                _loginState.value = LoginState.Error("Invalid email or password")
            }
        }
    }
    
    fun resetRegistrationState() {
        _registrationState.value = RegistrationState.Idle
    }
    
    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    // Function to load user details by ID
    fun loadUserById(userId: Long) {
        viewModelScope.launch {
            repository.getUserById(userId)?.let { user ->
                _userDetails.value = user
            }
        }
    }

    sealed class RegistrationState {
        object Idle : RegistrationState()
        object Loading : RegistrationState()
        object Success : RegistrationState()
        data class Error(val message: String) : RegistrationState()
    }
    
    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        data class Success(val user: User) : LoginState()
        data class Error(val message: String) : LoginState()
    }
} 