package dev.tomlovelady.lifttracker.viewmodels

import androidx.lifecycle.*
import dev.tomlovelady.lifttracker.entities.Gym
import dev.tomlovelady.lifttracker.repositories.GymRepository
import kotlinx.coroutines.launch


class GymViewModel(private val repository: GymRepository) : ViewModel() {

    val allGyms: LiveData<List<Gym>> = repository.allGyms.asLiveData()

    fun insert(gym: Gym) = viewModelScope.launch {
        repository.insert(gym)
    }
}

class GymViewModelFactory(private val repository: GymRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GymViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GymViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}