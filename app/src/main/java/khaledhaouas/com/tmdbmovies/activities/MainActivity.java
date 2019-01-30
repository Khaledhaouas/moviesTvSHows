package khaledhaouas.com.tmdbmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.ui.movielist.MovieDetailsFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MovieDetailsFragment.newInstance())
                    .commitNow();
        }
    }
}
