package khaledhaouas.com.tmdbmovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import khaledhaouas.com.tmdbmovies.ui.movielist.MovieListFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MovieListFragment.newInstance())
                    .commitNow();
        }
    }
}
