package id.haadii.favoriteapp.favorite.favoriteMovie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import id.haadii.favoriteapp.R
import kotlinx.android.synthetic.main.fragment_movie.*

class FavoriteMovieFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_movie_empty.visibility = View.VISIBLE

    }

}
