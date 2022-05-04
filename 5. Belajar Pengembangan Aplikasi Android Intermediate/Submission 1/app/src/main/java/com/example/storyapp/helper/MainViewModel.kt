package com.example.storyapp.helper

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapp.database.Injection
import com.example.storyapp.network.ListStoryItem

class MainViewModel() : ViewModel() {
//    private val _quote = MutableLiveData<List<ListStoryItem>>()
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXVHdEtoRWpNa3FGN2JBQVUiLCJpYXQiOjE2NTEwMzk5NzR9.Yu6aBBH6X8ewl20eUXnTM8KxaMHurzGELu0QehpFGio"
    private val storyRepository = Injection.provideRepository(token)
    val quote: LiveData<PagingData<ListStoryItem>> =
        storyRepository.getQuote().cachedIn(viewModelScope)

//    fun getQuote() {
//        viewModelScope.launch {
//            _quote.postValue(storyRepository.getQuote())
//        }
//    }

//    fun <T : ViewModel> T.createFactory(): ViewModelProvider.Factory {
//        val viewModel = this
//        return object : ViewModelProvider.Factory {
//            @Suppress("UNCHECKED_CAST")
//            override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModel as T
//        }
//    }
}

