package com.vikas.paging3.view.demo

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vikas.paging3.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@ExperimentalPagingApi
class DemoFragment : Fragment(R.layout.fragment_demo) {

    lateinit var rvDemo: RecyclerView
    lateinit var demoViewModel: DemoViewModel
    lateinit var adapter: DemoImageAdapter
    lateinit var demoStateAdapter: DemoStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchDemoImages()
    }

    private fun fetchDemoImages() {
        lifecycleScope.launch {
            demoViewModel.fetDemoImage().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun initMembers() {
        demoViewModel = defaultViewModelProviderFactory.create(DemoViewModel::class.java)
        adapter = DemoImageAdapter()
        demoStateAdapter = DemoStateAdapter { adapter.retry() }
    }

    private fun setUpViews(view: View) {
        rvDemo = view.findViewById(R.id.rvDemo)
        rvDemo.layoutManager = GridLayoutManager(context, 2)
        rvDemo.adapter = adapter.withLoadStateFooter(demoStateAdapter)
    }
}