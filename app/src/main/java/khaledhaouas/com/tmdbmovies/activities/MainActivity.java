package khaledhaouas.com.tmdbmovies.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import khaledhaouas.com.tmdbmovies.R;
import khaledhaouas.com.tmdbmovies.ui.account.AccountFragment;
import khaledhaouas.com.tmdbmovies.ui.movielist.MovieListFragment;
import khaledhaouas.com.tmdbmovies.ui.tvshowlist.TvShowListFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private BottomNavigationView mNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MovieListFragment.newInstance())
                    .addToBackStack(null)
                    .commit();
        }

        mNavigationBar = findViewById(R.id.navigation_bar);
        mNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.action_movies:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, MovieListFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.action_tv_shows:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, TvShowListFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.action_favorites:
                        break;
                    case R.id.action_account:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, AccountFragment.newInstance())
                                .addToBackStack(null)
                                .commit();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if (!(fragment instanceof MovieListFragment)) {
            super.onBackPressed();
        } else {
            System.exit(0);
        }
    }
}
