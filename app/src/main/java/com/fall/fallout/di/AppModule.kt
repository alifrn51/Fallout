package com.fall.fallout.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.fall.fallout.data.data_source.FalloutDatabase
import com.fall.fallout.data.repository.PersonRepositoryIml
import com.fall.fallout.domain.repository.PersonRepository
import com.fall.fallout.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideFalloutDatabase(
        @ApplicationContext context: Context
    )  = Room.databaseBuilder(
        context,
        FalloutDatabase::class.java,
        FalloutDatabase.DATABASE_NAME
    ).build()


    @Provides
    @Singleton
    fun providePersonRepository(db:FalloutDatabase): PersonRepository = PersonRepositoryIml(db.daoPerson())

    @Provides
    @Singleton
    fun providePersonUseCases(repository: PersonRepository): PersonUseCases = PersonUseCases(
        getPersonsUseCase = GetPersonsUseCase(repository),
        addPersonUseCase = AddPersonUseCase(repository),
        deletePersonUseCase = DeletePersonUseCase(repository),
        updatePersonUseCase = UpdatePersonUseCase(repository)
    )
}