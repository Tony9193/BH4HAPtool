package com.example.bh4haptool.core.toolkit.di

import com.example.bh4haptool.core.toolkit.draw.DrawEngine
import com.example.bh4haptool.core.toolkit.draw.RandomDrawEngine
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ToolkitModule {
    @Binds
    @Singleton
    abstract fun bindDrawEngine(impl: RandomDrawEngine): DrawEngine
}
