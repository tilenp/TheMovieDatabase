package com.example.themoviedatabase.service.impl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.themoviedatabase.database.MovieDatabase
import com.example.themoviedatabase.database.query.MovieSummaryQuery
import com.example.themoviedatabase.database.table.ImagePathTable
import com.example.themoviedatabase.database.table.MoviePagingKeysTable
import com.example.themoviedatabase.database.table.MovieTable
import com.example.themoviedatabase.mapper.Mapper
import com.example.themoviedatabase.model.dto.MovieDTO
import com.example.themoviedatabase.network.MovieApi
import com.example.themoviedatabase.network.MovieRequestQuery
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator constructor(
    private val movieApi: MovieApi,
    private val database: MovieDatabase,
    private val queryBuilder: MovieRequestQuery.Builder,
    private val movieTableMapper: Mapper<MovieDTO, MovieTable>,
    private val imagePathTableMapper: Mapper<MovieDTO, ImagePathTable>
) : RemoteMediator<Int, MovieSummaryQuery>() {

    private val imagePathDao = database.getImagePathDao()
    private val movieDao = database.getMovieDao()
    private val pagingKeysDao = database.getMoviePagingKeysDao()

    override suspend fun initialize(): InitializeAction {
        // Launch remote refresh as soon as paging starts and do not trigger remote prepend or
        // append until refresh has succeeded. In cases where we don't mind showing out-of-date,
        // cached offline data, we can return SKIP_INITIAL_REFRESH instead to prevent paging
        // triggering remote refresh.
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieSummaryQuery>
    ): MediatorResult {

        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for prepend.
                val prevKey = remoteKeys?.previousKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // If remoteKeys is null, that means the refresh result is not in the database yet.
                // We can return Success with `endOfPaginationReached = false` because Paging
                // will call this method again if RemoteKeys becomes non-null.
                // If remoteKeys is NOT NULL but its prevKey is null, that means we've reached
                // the end of pagination for append.
                val nextKey = remoteKeys?.nextKey
                if (nextKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                }
                nextKey
            }
        }

        try {
            val query = queryBuilder.build()
            val response = movieApi.getMovies(sortBy = query.sortBy.value, page = page)
            val endReached = response.page == response.totalPages

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.deleteMovies()
                    imagePathDao.deleteImagePaths()
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endReached) null else page + 1
                val keys = response.results.map { movieDto ->
                    MoviePagingKeysTable(movieId = movieDto.id, previousKey = prevKey, nextKey = nextKey)
                }
                movieDao.insertMovies(response.results.map { movieTableMapper.map(it) })
                imagePathDao.insertImagePaths(response.results.map { imagePathTableMapper.map(it) })
                pagingKeysDao.insertPagingKeys(keys)
            }
            return MediatorResult.Success(endOfPaginationReached = endReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, MovieSummaryQuery>): MoviePagingKeysTable? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movieSummary ->
                // Get the remote keys of the last item retrieved
                pagingKeysDao.getPagingKeysForMovieId(movieSummary.movieTable.id)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, MovieSummaryQuery>): MoviePagingKeysTable? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movieSummary ->
                // Get the remote keys of the first items retrieved
                pagingKeysDao.getPagingKeysForMovieId(movieSummary.movieTable.id)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, MovieSummaryQuery>
    ): MoviePagingKeysTable? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.movieTable?.id?.let { movieId ->
                pagingKeysDao.getPagingKeysForMovieId(movieId)
            }
        }
    }
}