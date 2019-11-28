package id.haadii.favoriteapp.favorite

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.haadii.favoriteapp.R
import id.haadii.favoriteapp.favorite.favoriteMovie.FavoriteMovieFragment
import id.haadii.favoriteapp.favorite.favoriteTvShow.FavoriteTvShowFragment

class MyPagerAdapter(fragmentManager: FragmentManager?, private val context: Context) : FragmentPagerAdapter(fragmentManager!!) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                FavoriteMovieFragment()
            }
            else -> {
                return FavoriteTvShowFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                context.getString(R.string.movie)
            }
            else -> {
                context.getString(R.string.tv_show)
            }
        }
    }

}