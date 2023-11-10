package com.jfsb.blogsapp.features.dashboard.data.repository

import com.jfsb.blogsapp.core.network.models.DefaultResult
import com.jfsb.blogsapp.features.dashboard.data.datasource.remote.model.EntryModel
import kotlinx.coroutines.flow.Flow


interface EntryRepository {
    suspend fun getAllEntries(): Flow<DefaultResult<List<EntryModel>>>
    suspend fun createEntry(entry: EntryModel): Flow<DefaultResult<Void?>>
}