package com.jfsb.blogsapp.features.dashboard.domain.usecase

import com.jfsb.blogsapp.features.dashboard.data.datasource.remote.model.EntryModel
import com.jfsb.blogsapp.features.dashboard.domain.repository.EntryRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class CreateEntryUseCase @OptIn(ExperimentalCoroutinesApi::class)
@Inject constructor(private val repo: EntryRepositoryImpl) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(entry: EntryModel) = repo.createEntry(entry)
}