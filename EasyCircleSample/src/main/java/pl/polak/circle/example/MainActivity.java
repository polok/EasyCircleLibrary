package pl.polak.circle.example;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

import pl.polak.circle.BorderCircleProgressBar;
import pl.polak.circle.CircleProgressBar;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        private CircleProgressBar circleProgressBar;
        private BorderCircleProgressBar borderCircleProgressBar;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            circleProgressBar = (CircleProgressBar) rootView.findViewById(R.id.circle_progress_bar);
            borderCircleProgressBar = (BorderCircleProgressBar) rootView.findViewById(R.id.border_circle_progress_bar);
            return rootView;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            new Thread(new CircleProgressBarTask()).start();
        }

        class CircleProgressBarTask implements Runnable {
            @Override
            public void run() {
                int percent = 0;
                while (true) {
                    try {
                        Thread.sleep(150);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    percent += 5;
                    if (percent > 100) {
                        percent = 0;
                    }
                    circleProgressBar.changePercentage(percent);
                    borderCircleProgressBar.changePercentage(percent);
                }
            }
        }
    }
}
