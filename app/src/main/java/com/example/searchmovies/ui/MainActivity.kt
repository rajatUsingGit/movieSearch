package com.example.searchmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
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
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        setSupportActionBar(mBinding.customToolbar)
        supportActionBar?.title = getString(R.string.search_your_favorite_movie)
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.action_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                (searchItem?.actionView as SearchView?)?.apply {
                    onQueryTextFlow()
                        .debounce(500)
                        .distinctUntilChanged()
                        .flatMapLatest { query -> viewModel.getDataFromNetwork(query) }
                        .flowOn(Dispatchers.IO)
                        .catch { viewModel.getDataFromLocalDb(query.toString())}
                        .collect { result ->
                            if (result.isNotEmpty()) {
                                mBinding.welcomeText.text = result
                            } else {
                                mBinding.welcomeText.text = getString(R.string.welcome)
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