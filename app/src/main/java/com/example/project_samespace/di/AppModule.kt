package com.example.project_samespace.di

import com.example.project_samespace.data.ApiService
import com.example.project_samespace.data.MusicRepositoryImpl
import com.example.project_samespace.domain.MusicRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideApiInstance(): ApiService {
        return Retrofit
            .Builder()
            .baseUrl("https://cms.samespace.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideMusicRepository(
        apiService: ApiService
    ): MusicRepository{
        return MusicRepositoryImpl(apiService)
    }


}