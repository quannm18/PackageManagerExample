package com.example.packagemanagerexample

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.Intent.ACTION_PACKAGE_REPLACED
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.packagemanagerexample.adapter.MyAdapter
import com.example.packagemanagerexample.adapter.SimpleAdapter
import com.example.packagemanagerexample.model.AppModel
import com.example.packagemanagerexample.viewmodel.IClickUninstallListener
import com.example.packagemanagerexample.viewmodel.LiveDataViewModel
import com.example.packagemanagerexample.viewmodel.UpdateDataViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.security.auth.login.LoginException

class MainActivity : AppCompatActivity(), IClickUninstallListener {
    private lateinit var mRCV: RecyclerView
    private var mUpdateDataViewModel: UpdateDataViewModel = UpdateDataViewModel()
    private var mAdapter: MyAdapter = MyAdapter()
    private var simpleAdapter: SimpleAdapter = SimpleAdapter()
    private lateinit var liveDataViewModel: LiveDataViewModel
    private var myList: MutableList<AppModel> = mutableListOf()

    private var myPackageNameApp: MutableList<String> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged", "QueryPermissionsNeeded")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mRCV = findViewById(R.id.rcvMain)
        liveDataViewModel = LiveDataViewModel(application)
        val manager = packageManager

//        val mList: List<ApplicationInfo> = manager.getInstalledApplications(
//            PackageManager.GET_META_DATA
//        )

        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val mList: List<ResolveInfo> = manager.queryIntentActivities(
            intent,
            0,
            )
        runBlocking {
            withContext(Dispatchers.IO) {
                myPackageNameApp = querySettingPkgName()
        Log.e("Error","$myPackageNameApp")
            }
        }
        mList.forEach {
            if (!it.activityInfo.packageName.contains("com.google.android")){
                if (!it.activityInfo.packageName.contains("com.android")){
                    if (!myPackageNameApp.contains(it.activityInfo.packageName)) {
                        myList.add(
                            AppModel(
                                it.activityInfo.loadLabel(manager).toString(), it.activityInfo.packageName,
                                it.activityInfo.loadIcon(manager)
                            )
                        )
                    }
                }
            }
        }


        simpleAdapter.initMyList(myList,this)

        mRCV.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = simpleAdapter
            addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        }
    }

    private fun querySettingPkgName(): MutableList<String> {
        var listNamePkg: MutableList<String> = mutableListOf()

        val intent = Intent(Settings.ACTION_SETTINGS)
        val resolveInfos = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            resolveInfos.forEach {
                listNamePkg.add(it.activityInfo.packageName)
            }

        return listNamePkg
    }

    override fun delete(i: Int) {
        val mApp = myList[i]
        val uri : Uri = Uri.fromParts("package",mApp.packageName,null)
        val intent = Intent(Intent.ACTION_DELETE,uri)
        startActivity(intent)

        if (!isPackageExist(mApp.packageName.toString())){
            myList.removeAt(i)
            simpleAdapter.notifyItemRemoved(i)
        }
    }


//    fun initViewModel(){
//        val mViewModel = ViewModelProvider(this)[AppViewModel::class.java]
//        lifecycleScope.launchWhenCreated {
//            mViewModel.getListData().collectLatest {
//                mAdapter.submitData(it)
//            }
//        }
//    }
@SuppressLint("QueryPermissionsNeeded")
fun Context.isPackageExist(target: String): Boolean {
    return packageManager.getInstalledApplications(0).find { info -> info.packageName == target } != null
}
}