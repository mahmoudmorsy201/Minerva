package com.example.minerva.di

import com.example.minerva.data.repository.Repository
import com.example.minerva.data.repository.RepositoryInterface
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindMainRepository(
        repository: Repository
    ): RepositoryInterface
}


