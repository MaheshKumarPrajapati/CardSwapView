package com.mahesh_prajapati.matchingapp.ui.main.viewmodel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mahesh_prajapati.matchingapp.data.repository.MainRepository
import com.mahesh_prajapati.matchingapp.storage.DatabaseClient
import com.mahesh_prajapati.matchingapp.storage.model.ResultX
import com.mahesh_prajapati.matchingapp.utils.AppConstants
import com.mahesh_prajapati.matchingapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    var cardList: ArrayList<ResultX>? = ArrayList<ResultX>()
    var listSelected: ArrayList<ResultX> = ArrayList<ResultX>()
    var listRejected: ArrayList<ResultX> = ArrayList<ResultX>()

    fun getCardDataFromWebService() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getItunesSongs().results))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

    fun setCardDataToDB(context: Context, candidateList: List<ResultX>) {
        GlobalScope.launch {
            if (cardList == null) {
                cardList = DatabaseClient.getInstance(context)!!.appDatabase.cardsDao()!!
                    .getAll() as ArrayList<ResultX>
            }
            if (candidateList.isNotEmpty()) {
                for (item in candidateList) {
                    if (cardList!!.size > 0) {
                        if (!cardList!!.any { it -> it.id == item.id }) {
                            cardList!!.add(item)
                            DatabaseClient.getInstance(context)!!.appDatabase
                                .cardsDao()!!.insert(item)
                        }
                    } else {
                        cardList!!.add(item)
                        DatabaseClient.getInstance(context)!!.appDatabase
                            .cardsDao()!!.insert(item)
                    }

                }
            }

        }
    }

    fun getCardDataFromDB(context: Context) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            cardList = DatabaseClient.getInstance(context)!!.appDatabase.cardsDao()!!
                .getAll() as ArrayList<ResultX>
            emit(
                Resource.success(
                    data = DatabaseClient.getInstance(context)!!.appDatabase.cardsDao()!!.getAll()
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }

    }



    fun getCardDetails(intent: Intent) = liveData(Dispatchers.IO) {
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
                .cardsDao()!!.updateCards(result.selectVal!!, result.dbId!!)

        }
    }

    fun filterList(songs: List<ResultX>): List<ResultX> {
        var list: ArrayList<ResultX> = ArrayList<ResultX>()
        for (item in songs) {
            if (item.selectVal == null || item.selectVal == 0) {
                list.add(item)
            }
        }
        return list
    }

    fun getHistoryWithFilteredData(
        type: String,
        context: Context?
    ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            if (cardList!!.size == 0) {
                cardList = DatabaseClient.getInstance(context!!)!!.appDatabase.cardsDao()!!
                    .getAll() as ArrayList<ResultX>
            }
            if (listRejected.size == 0 || listSelected.size == 0) {
                listSelected.clear()
                listRejected.clear()
                for (item in cardList!!) {
                    if (item.selectVal == 1) {
                        listSelected.add(item)
                    } else if (item.selectVal == -1) {
                        listRejected.add(item)
                    }
                }
            }
            if (type == AppConstants.All_DATA) {
                var listAll: ArrayList<ResultX> = ArrayList<ResultX>()
                listAll.addAll(listSelected)
                listAll.addAll(listRejected)
                emit(Resource.success(data = listAll))
            } else if (type == AppConstants.SELECTED_DATA) {
                emit(Resource.success(data = listSelected))
            } else if (type == AppConstants.REJECTED_DATA) {
                emit(Resource.success(data = listRejected))
            }

        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}