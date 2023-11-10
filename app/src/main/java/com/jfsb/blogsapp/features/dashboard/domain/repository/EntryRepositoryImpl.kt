package com.jfsb.blogsapp.features.dashboard.domain.repository

import com.google.firebase.firestore.CollectionReference
import com.jfsb.blogsapp.core.network.models.DefaultResult
import com.jfsb.blogsapp.core.utils.Constants.DATE
import com.jfsb.blogsapp.features.dashboard.data.datasource.remote.model.EntryModel
import com.jfsb.blogsapp.features.dashboard.data.repository.EntryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@ExperimentalCoroutinesApi
class EntryRepositoryImpl @Inject constructor(
    private val entryRef: CollectionReference
) : EntryRepository {

    override suspend fun getAllEntries(): Flow<DefaultResult<List<EntryModel>>> = callbackFlow {
            val snapshotListener = entryRef.orderBy(DATE)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val entries = snapshot.toObjects(EntryModel::class.java)
                        DefaultResult.Success(entries)
                    } else {
                        DefaultResult.Error(e?.message ?: e.toString())
                    }
                    trySend(response).isSuccess
                }
            awaitClose {
                snapshotListener.remove()
            }
        }


    override suspend fun createEntry(entry: EntryModel): Flow<DefaultResult<Void?>> = flow {
        try {
            emit(DefaultResult.Loading)
            val addition = entryRef.document().set(entry).await()
            emit(DefaultResult.Success(addition))
        } catch (e: Exception) {
            emit(DefaultResult.Error(e.message ?: e.toString()))
        }
    }
}