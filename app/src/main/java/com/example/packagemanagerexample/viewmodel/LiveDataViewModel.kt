package com.example.packagemanagerexample.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.packagemanagerexample.model.AppModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LiveDataViewModel(application: Application) : AndroidViewModel(application) {
    var mList : MutableLiveData<AppModel> = MutableLiveData()

    fun setMList (appModel: AppModel){
        viewModelScope.launch (Dispatchers.IO){
            mList.postValue(appModel)
        }
    }

    fun getList() : MutableLiveData<AppModel> {
        return mList
    }
}