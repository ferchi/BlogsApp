package com.jfsb.blogsapp.core.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jfsb.blogsapp.core.utils.Constants.BLOGS
import com.jfsb.blogsapp.features.dashboard.data.repository.EntryRepository
import com.jfsb.blogsapp.features.dashboard.domain.repository.EntryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    fun provideTicketsRef() = Firebase.firestore.collection(BLOGS)

    @Provides
    fun provideEntryRepository(
        entriesRef: CollectionReference
    ): EntryRepository = EntryRepositoryImpl(entriesRef)
}