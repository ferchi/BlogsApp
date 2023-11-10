package com.jfsb.blogsapp.features.dashboard.presentation.viewmodel

import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jfsb.blogsapp.core.network.models.DefaultResult
import com.jfsb.blogsapp.features.dashboard.data.datasource.remote.model.EntryModel
import com.jfsb.blogsapp.features.dashboard.domain.usecase.CreateEntryUseCase
import com.jfsb.blogsapp.features.dashboard.domain.usecase.GetAllEntriesUseCase
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch

@HiltViewModel
class EntryViewModel @Inject constructor(
    private val createEntryUseCase: CreateEntryUseCase,
    private val getAllEntriesUseCase: GetAllEntriesUseCase,
    @ApplicationContext private val context: android.content.Context
    ) : ViewModel() {

    private val _entriesListState = mutableStateOf<DefaultResult<List<EntryModel>>>(DefaultResult.Loading)
    val entriesListState: State<DefaultResult<List<EntryModel>>> = _entriesListState

    private val _currentEntry: MutableLiveData<EntryModel> = MutableLiveData()
    val currentEntry: LiveData<EntryModel> = _currentEntry

    private val _title: MutableLiveData<String> = MutableLiveData()
    val title: LiveData<String> = _title

    private val _author: MutableLiveData<String> = MutableLiveData()
    val author: LiveData<String> = _author

    private val _content: MutableLiveData<String> = MutableLiveData()
    val content: LiveData<String> = _content

    private val _query: MutableLiveData<String> = MutableLiveData()
    val query: LiveData<String> = _query

    private val _isEntryAddedState = mutableStateOf<DefaultResult<Void?>>(DefaultResult.Success(null))
    val isEntryAddedState: State<DefaultResult<Void?>> = _isEntryAddedState

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setAuthor(author: String) {
        _author.value = author
    }

    fun setContent(content: String) {
        _content.value = content
    }

    fun setCurrentEntry(entry: EntryModel) {
        _currentEntry.value = entry
    }

    fun setQuery(query: String) {
        _query.value = query
        if (query == "") getAllEntries()
        else searchEntry(query)
    }

    fun clearTicketData(){
        _currentEntry.value = EntryModel()
        _title.value = ""
        _content.value = ""
        _author.value = ""
    }

    fun getAllEntries() {
        viewModelScope.launch {
            getAllEntriesUseCase.invoke().collect { response ->
                _entriesListState.value = response
            }
        }
    }

    fun createEntry(entry: EntryModel) {
        viewModelScope.launch {
            createEntryUseCase.invoke(entry).collect { response ->
                _isEntryAddedState.value = response
                Toast.makeText(
                    context,
                    "Entrada cargada correctamente",
                    Toast.LENGTH_LONG
                ).show()
                clearTicketData()
                getAllEntries()
            }
        }
    }

    fun isValidateForm(): Boolean {
        return (_title.value != null && _title.value != "")
                && (_author.value != null && _author.value != "")
                && (_content.value != null && _content.value != "")
    }

    fun searchEntry(query: String) {
        when (val currentState = _entriesListState.value) {
            is DefaultResult.Success<*> -> {
                val filteredList = (currentState.data as List<EntryModel>).filter {
                    it.title?.contains(query, true) ?: false
                            || it.author?.contains(query, true) ?: false
                            || it.content?.contains(query, true) ?: false
                }
                _entriesListState.value = DefaultResult.Success(filteredList)
            }

            else -> {
                // No hagas nada si el estado no es de éxito, para no perder información
            }
        }
    }

    init {
        getAllEntries()
    }
}