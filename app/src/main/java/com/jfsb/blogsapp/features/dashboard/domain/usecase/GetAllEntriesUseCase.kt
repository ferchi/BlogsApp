package com.jfsb.blogsapp.features.dashboard.domain.usecase

import com.jfsb.blogsapp.features.dashboard.domain.repository.EntryRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class GetAllEntriesUseCase @Inject constructor (private val repo: EntryRepositoryImpl) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke() = repo.getAllEntries()
}