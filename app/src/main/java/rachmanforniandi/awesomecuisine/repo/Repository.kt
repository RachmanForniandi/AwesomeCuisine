package rachmanforniandi.awesomecuisine.repo

import dagger.hilt.android.scopes.ActivityRetainedScoped
import rachmanforniandi.awesomecuisine.data.LocalDataSource
import rachmanforniandi.awesomecuisine.data.RemoteDataSource
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    val remote = remoteDataSource
    val local = localDataSource
}