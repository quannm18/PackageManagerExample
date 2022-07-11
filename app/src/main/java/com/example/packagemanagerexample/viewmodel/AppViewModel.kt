package com.example.packagemanagerexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.packagemanagerexample.model.AppModel
import com.example.packagemanagerexample.repository.AppPagingSource
import kotlinx.coroutines.flow.Flow

private const val ITEMS_PER_PAGE =20
class AppViewModel : ViewModel() {
    var mData :  UpdateDataViewModel = UpdateDataViewModel()

    fun getListData(): Flow<PagingData<AppModel>>{
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = {
                AppPagingSource(mData)
            }
        ).flow.cachedIn(viewModelScope)
    }
}