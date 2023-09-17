package cn.jailedbird.doc

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

interface MainApiService {

}

class MainViewModel : ViewModel() {

    /*init {
        val retrofit = Retrofit.Builder().baseUrl("").build()
        retrofit.create(MainApiService::class.java)
    }*/

    fun test() {
        viewModelScope.launch{

        }
    }


}