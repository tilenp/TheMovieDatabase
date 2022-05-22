import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.themoviedatabase.ui.common.LoadingView

@Composable
fun <T : Any> ComposablePagedGrid(
    columns: Int,
    modifier: Modifier = Modifier,
    pagedItems: LazyPagingItems<T>,
    itemContent: @Composable (T) -> Unit,
    errorContent: @Composable (Modifier, Throwable, () -> Unit) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(columns),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        contentPadding = PaddingValues(start = 4.dp, top = 8.dp, end = 4.dp, bottom = 8.dp)
    ) {
        items(pagedItems.itemCount) { index ->
            pagedItems[index]?.let { itemContent(it) }
        }
        pagedItems.apply {
            when {
                loadState.refresh is LoadState.Error && itemCount > 0 -> {
                    val error = loadState.refresh as LoadState.Error
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        errorContent(Modifier.fillMaxWidth(), error.error) { retry() }
                    }
                }
                loadState.append is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    }
                }
                loadState.append is LoadState.Error -> {
                    val error = loadState.append as LoadState.Error
                    item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                        errorContent(Modifier.fillMaxWidth(), error.error) { retry() }
                    }
                }
            }
        }
    }
    pagedItems.apply {
        when {
            loadState.refresh is LoadState.Loading -> {
                LoadingView(modifier = Modifier.fillMaxSize())
            }
            loadState.refresh is LoadState.Error && itemCount == 0 -> {
                val error = loadState.refresh as LoadState.Error
                errorContent(Modifier.fillMaxSize(), error.error) { pagedItems.retry() }
            }
        }
    }
}