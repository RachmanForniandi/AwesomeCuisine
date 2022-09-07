package rachmanforniandi.awesomecuisine.repo

import dagger.hilt.android.scopes.ViewModelScoped
import rachmanforniandi.awesomecuisine.data.LocalDataSource
import rachmanforniandi.awesomecuisine.data.RemoteDataSource
import javax.inject.Inject

@ViewModelScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    val remote = remoteDataSource
    val local = localDataSource
}