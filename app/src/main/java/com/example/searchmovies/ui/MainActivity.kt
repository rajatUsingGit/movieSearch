package com.example.searchmovies.ui

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.searchmovies.R
import com.example.searchmovies.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private val mViewModel: MainViewModel by viewModels()
    private lateinit var searchItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        mBinding.recyclerView.adapter = MainListAdapter()
        setSupportActionBar(mBinding.customToolbar)
        supportActionBar?.title = ""
        setContentView(mBinding.root)
        mBinding.customToolbar.setOnClickListener {
            onSearchRequested()
        }
    }

    override fun onSearchRequested(): Boolean {
        searchItem.expandActionView()
        searchItem.actionView.requestFocus()
        return super.onSearchRequested()
    }

    @OptIn(FlowPreview::class)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView?

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                searchView?.apply {
                    onQueryTextFlow()
                        .debounce(1000)
                        .distinctUntilChanged()
                        .flowOn(Dispatchers.Default)
                        .collect { query ->
                            if (query.isNotEmpty()) {
                                mBinding.welcomeText.visibility = View.GONE
                                mBinding.recyclerView.visibility = View.VISIBLE
                                mViewModel.refreshUI(query)
                            } else {
                                mBinding.welcomeText.visibility = View.VISIBLE
                                mBinding.recyclerView.visibility = View.GONE
                            }
                        }
                }
            }
        }
        return true
    }

    private fun SearchView.onQueryTextFlow(): StateFlow<String> {
        val query = MutableStateFlow("")
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                query.value = newText
                return true
            }
        })
        return query
    }

}