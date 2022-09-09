package rachmanforniandi.awesomecuisine.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import rachmanforniandi.awesomecuisine.data.database.RecipesDatabase
import rachmanforniandi.awesomecuisine.util.Constants.Companion.DATABASE_NAME
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context)=
        Room.databaseBuilder(context,
            RecipesDatabase::class.java,DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDao(db:RecipesDatabase)= db.recipesDao()

}