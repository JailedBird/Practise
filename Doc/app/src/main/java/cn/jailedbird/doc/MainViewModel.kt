package cn.jailedbird.doc

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit

interface MainApiService {

}

class MainViewModel : ViewModel() {

    init {
        val retrofit = Retrofit.Builder().baseUrl("").build()
        retrofit.create(MainApiService::class.java)
    }


}