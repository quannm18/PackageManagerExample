package com.example.packagemanagerexample.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.packagemanagerexample.model.AppModel
import com.example.packagemanagerexample.viewmodel.UpdateDataViewModel
import java.io.IOException
import kotlin.math.max

class AppPagingSource(private val mViewModel: UpdateDataViewModel) : PagingSource<Int, AppModel>() {
    override fun getRefreshKey(state: PagingState<Int, AppModel>): Int? {
        var anchorPos = state.anchorPosition?:return null
        val item = state.closestItemToPosition(anchorPos)?:return null

        return ensureValidKey(item.name?.length?: (0-10))
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AppModel> {
        return LoadResult.Page(
            data = mViewModel.getList(),
            prevKey= myPos,
            nextKey = ++myPos
        )
    }
    companion object{
        private const val FIRST_PAGE_INDEX = 1
        private var myPos = 1
    }

    private fun ensureValidKey(key: Int) = max(FIRST_PAGE_INDEX, key)
}