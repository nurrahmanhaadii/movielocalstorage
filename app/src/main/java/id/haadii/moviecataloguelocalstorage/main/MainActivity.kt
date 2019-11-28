package id.haadii.moviecataloguelocalstorage.main

import android.content.Intent
import android.database.ContentObserver
import android.database.Cursor
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.haadii.moviecataloguelocalstorage.R
import id.haadii.moviecataloguelocalstorage.db.DatabaseContract
import id.haadii.moviecataloguelocalstorage.favorite.FavoriteFragment
import id.haadii.moviecataloguelocalstorage.helper.MappingHelper
import id.haadii.moviecataloguelocalstorage.movie.MovieFragment
import id.haadii.moviecataloguelocalstorage.setting.SettingActivity
import id.haadii.moviecataloguelocalstorage.tvShow.TvShowFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setupBottomNavigation()

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler) {
            override fun onChange(self: Boolean) {
                Log.e("onChange", "true")
                loadMoviesAsync()
            }
        }

        contentResolver.registerContentObserver(DatabaseContract.MovieColumns.CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadMoviesAsync()
        }
    }

    private fun loadMoviesAsync() {

        GlobalScope.launch(Dispatchers.Main) {
            val deferredMovies = async(Dispatchers.IO) {
                val cursor = contentResolver.query(DatabaseContract.MovieColumns.CONTENT_URI, null, null, null, null) as Cursor
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val movies = deferredMovies.await()
            Log.e("moviessss", "$movies")
            if (movies.size > 0) {

            }
        }
    }

    private fun setupBottomNavigation() {
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener())
        bottomNavigation.selectedItemId = R.id.movie
    }

    private fun navigationItemSelectedListener() : BottomNavigationView.OnNavigationItemSelectedListener {
        return BottomNavigationView.OnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.movie -> {
                    val fragment = MovieFragment()
                    openFragment(fragment)
                    title = resources.getString(R.string.movie)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.tv_show -> {
                    val fragment = TvShowFragment()
                    openFragment(fragment)
                    title = resources.getString(R.string.tv_show)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    val fragment = FavoriteFragment()
                    openFragment(fragment)
                    title = resources.getString(R.string.favorite)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }
    }

    override fun onResume() {
        super.onResume()
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.selectedItemId = R.id.movie
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.contentFrame, fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val search = menu?.findItem(R.id.search)
        val searchView = search?.actionView as SearchView
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        searchView.queryHint = "Search"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0 != null && p0.isNotEmpty()) {
                    when (bottomNavigation.selectedItemId) {
                        R.id.movie -> {
                            viewModel.getSearchMovie(p0)
                        }
                        R.id.tv_show -> {
                            viewModel.getSearchTv(p0)
                        }
                        R.id.favorite -> {

                        }
                    }
                } else {
                    when (bottomNavigation.selectedItemId) {
                        R.id.movie -> {
                            viewModel.getMovie()
                        }
                        R.id.tv_show -> {
                            viewModel.getTvShow()
                        }
                        R.id.favorite -> {

                        }
                    }
                }
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }
        })

        search.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
                when (bottomNavigation.selectedItemId) {
                    R.id.movie -> {
                        viewModel.getMovie()
                    }
                    R.id.tv_show -> {
                        viewModel.getTvShow()
                    }
                    R.id.favorite -> {

                    }
                }
                return true
            }

            override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_change_settings) {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        } else if (item.itemId == R.id.setting) {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
