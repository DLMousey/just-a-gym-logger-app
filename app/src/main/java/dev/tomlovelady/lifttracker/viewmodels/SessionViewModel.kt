package dev.tomlovelady.lifttracker.viewmodels

import androidx.lifecycle.*
import dev.tomlovelady.lifttracker.entities.Session
import dev.tomlovelady.lifttracker.repositories.SessionRepository
import kotlinx.coroutines.launch

class SessionViewModel(private val repository: SessionRepository) : ViewModel() {

    var allSessions: LiveData<List<Session>> = repository.allSessions.asLiveData()

    lateinit var currentSessions: LiveData<List<Session>>

    fun insert(session: Session) = viewModelScope.launch {
        repository.insert(session)
    }

    fun loadSessionsForGym(gymId: Long) = viewModelScope.launch {
        currentSessions = repository.findSessionsByGym(gymId).asLiveData()
    }
}

class SessionViewModelFactory(private val repository: SessionRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SessionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SessionViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}