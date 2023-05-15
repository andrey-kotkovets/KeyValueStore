package com.ak.data.di

import com.ak.data.repository.StoreRepositoryImpl
import com.ak.data.store.KeyValueStore
import com.ak.data.store.KeyValueStoreImpl
import com.ak.domain.repository.StoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideKeyValueStore(): KeyValueStore {
        return KeyValueStoreImpl()
    }

    @Singleton
    @Provides
    fun provideStoreRepository(keyValueStore: KeyValueStore): StoreRepository {
        return StoreRepositoryImpl(keyValueStore)
    }
}
