package com.example.searchmovies.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
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
    private lateinit var mSearchItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        mBinding.lifecycleOwner = this
        mBinding.viewModel = mViewModel
        mBinding.recyclerView.adapter = MainListAdapter()
        setSupportActionBar(mBinding.searchBar)
        supportActionBar?.title = ""
        setContentView(mBinding.root)
        runUILogic()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        mSearchItem = menu.findItem(R.id.action_search)
        val searchView = mSearchItem.actionView as SearchView?

        mBinding.searchBar.setOnClickListener {
            onSearchRequested()
        }

        searchView?.apply {
            onActionViewExpanded()
            setQuery(mViewModel.query.value, false)
            setOnQueryTextListener(
                object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        return true
                    }
                    override fun onQueryTextChange(newText: String): Boolean {
                        mViewModel.query.value = newText
                        return true
                    }
                })
        }

        return true
    }

    override fun onSearchRequested(): Boolean {
        mSearchItem.expandActionView()
        mSearchItem.actionView.requestFocus()
        return super.onSearchRequested()
    }

    @OptIn(FlowPreview::class)
    private fun runUILogic() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                mViewModel.query.debounce(500).collect {
                    mBinding.apply {
                        welcomeText.visibility =
                            if (it.isEmpty()) View.VISIBLE else View.GONE
                        recyclerView.visibility =
                            if (it.isNotEmpty()) View.VISIBLE else View.GONE
                    }
                    mViewModel.onNewSearch()
                }
            }
        }
    }

}