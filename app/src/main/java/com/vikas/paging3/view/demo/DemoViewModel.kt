package com.vikas.paging3.view.demo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.vikas.paging3.data.DemoImagePagingSource
import com.vikas.paging3.data.DoggoImagePagingSource
import com.vikas.paging3.data.DoggoImagesRepository
import com.vikas.paging3.model.DemoModel
import com.vikas.paging3.model.DoggoImageModel
import kotlinx.coroutines.flow.Flow

@ExperimentalPagingApi
class DemoViewModel(val repository: DoggoImagesRepository = DoggoImagesRepository.getInstance()) :
    ViewModel() {

    /**
     * returning non modified PagingData<DoggoImageModel> value as opposite to remote view model
     * where we have mapped the coming values into different object
     */
    fun fetDemoImage(): Flow<PagingData<DemoModel>> {
        return repository.letDemoImagesFlow().cachedIn(viewModelScope)
    }
}