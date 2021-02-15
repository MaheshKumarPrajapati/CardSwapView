package com.mahesh_prajapati.itunes.ui.main.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mahesh_prajapati.itunes.data.repository.MainRepository
import com.mahesh_prajapati.itunes.storage.DatabaseClient
import com.mahesh_prajapati.itunes.storage.model.ResultX
import com.mahesh_prajapati.itunes.ui.main.view.CardActivity
import com.mahesh_prajapati.itunes.utils.AppConstants
import com.mahesh_prajapati.itunes.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    var songsList: ArrayList<ResultX>? = null

    fun getSongList() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getItunesSongs().results))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun setCondidatesToDataBase(context: Context, candidateList: List<ResultX>) {
        GlobalScope.launch {
            if (songsList == null) {
                songsList = DatabaseClient.getInstance(context)!!.appDatabase.songDao()!!
                    .getAll() as ArrayList<ResultX>
            }
            if(candidateList.isNotEmpty()){
                for(item in candidateList){
                    if(songsList!!.size>0){
                        if (!songsList!!.any { it -> it.id == item.id }) {
                            songsList!!.add(item)
                            DatabaseClient.getInstance(context)!!.appDatabase
                                .songDao()!!.insert(item)
                        }
                    }else
                    {
                        songsList!!.add(item)
                        DatabaseClient.getInstance(context)!!.appDatabase
                            .songDao()!!.insert(item)
                    }

                }
            }

        }

    }

    fun getSongFromHistory(context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            songsList = DatabaseClient.getInstance(context)!!.appDatabase.songDao()!!
                .getAll() as ArrayList<ResultX>
            emit(
                Resource.success(
                    data = DatabaseClient.getInstance(context)!!.appDatabase.songDao()!!.getAll()
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }


    fun removeSongFromHistory(
        context: Context,
        song: ResultX
    ) {
        GlobalScope.launch {
            DatabaseClient.getInstance(context)!!.appDatabase
                .songDao()!!.delete(song)
        }
    }



    fun getSongDetails(intent: Intent)= liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = intent.extras!!.getParcelable<ResultX>(AppConstants.CARD_DATA)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun updateValueToDB(
        context: Context,
        result: ResultX
    ) {
        GlobalScope.launch {
            DatabaseClient.getInstance(context)!!.appDatabase
                .songDao()!!.updateCandidate(result.selectVal!!,result.dbId!!)

        }
    }

    fun filterList(songs: List<ResultX>): List<ResultX> {
        var list:ArrayList<ResultX> = ArrayList<ResultX>()
        for(item in songs){
           if(item.selectVal==null || item.selectVal==0) {
               list.add(item)
           }
        }
      return list
    }
}