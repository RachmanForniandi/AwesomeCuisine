package rachmanforniandi.awesomecuisine.repo

import dagger.hilt.android.scopes.ActivityRetainedScoped
import rachmanforniandi.awesomecuisine.di.RemoteDataSource
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(remoteDataSource: RemoteDataSource) {

    val remote = remoteDataSource
}