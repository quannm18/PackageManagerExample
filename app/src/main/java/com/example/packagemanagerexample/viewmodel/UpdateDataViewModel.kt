package com.example.packagemanagerexample.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.packagemanagerexample.model.AppModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateDataViewModel : ViewModel() {
    public var mData : MutableList<AppModel> = mutableListOf()


    fun setMData(mAppModel: AppModel){
        viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun getList(): MutableList<AppModel> {
        return mData
    }
}
