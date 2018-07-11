package com.example.android.worldcupnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    LinearLayout emptyStateLayout;
    TextView emptyStateText;
    ProgressBar loading_spinner;
    Button retryButton;

    // URL for the article data from the Guardian API
    private static final String guardian_API_URL =
            "https://content.guardianapis.com/search?";

    // Adapter for the list of articles
    private ArticleAdapter mAdapter;

    // Constant value for the loader ID
    private static final int loaderID = 1;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    // Get a ref for the LoaderManager
    LoaderManager loaderManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the required views in the layout
        emptyStateLayout = findViewById(R.id.emptyviewlayout);
        emptyStateText = findViewById(R.id.emptyviewtext);
        loading_spinner = findViewById(R.id.loading_spinner);
        retryButton = findViewById(R.id.retrybutton);
        ListView listView = findViewById(R.id.listview);

        // Set the empty view for the ListView
        listView.setEmptyView(emptyStateLayout);

        // Create a new ArticleAdapter
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Set the adapter on the ListView
        listView.setAdapter(mAdapter);

        // Launch the loader
        launchLoader();

        // Set an on item click listener on the ListView to open the url for the article
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Article currentArticle = mAdapter.getItem(i);
                Uri articleUri = Uri.parse(currentArticle.getURL());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, articleUri);
                startActivity(webIntent);
            }
        });

        // Set an on click listener for the retry button
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emptyStateText.setText("");
                retryButton.setVisibility(View.GONE);
                loading_spinner.setVisibility(View.VISIBLE);
                launchLoader();
            }
        });

    }

    // Launch the loader
    public void launchLoader() {
        // Initiate the Loader Manager
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        loaderManager = getLoaderManager();

        if (networkInfo != null) {
            loaderManager.initLoader(loaderID, null, this);
        } else {
            loading_spinner.setVisibility(View.GONE);
            emptyStateText.setText(getResources().getString(R.string.noNetworkResponse));
            retryButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        String dateFrom = sharedPrefs.getString(
                getString(R.string.settingsDateFromKey),
                getString(R.string.settingsDateFromDefault));

        String dateTo = sharedPrefs.getString(
                getString(R.string.settingsDateToKey),
                getString(R.string.settingsDateToDefault));

        String orderBy = sharedPrefs.getString(
                getString(R.string.settingsOrderByKey),
                getString(R.string.settingsOrderByDefault));

        Uri baseUri = Uri.parse(guardian_API_URL);

        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("section", "football");
        uriBuilder.appendQueryParameter("q", "world cup");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("page-size", "30");
        uriBuilder.appendQueryParameter("from-date", dateFrom);
        uriBuilder.appendQueryParameter("to-date", dateTo);
        uriBuilder.appendQueryParameter("order-by", orderBy);
        uriBuilder.appendQueryParameter("api-key", getResources().getString(R.string.apiKey));

        return new ArticleLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        emptyStateText.setText(getResources().getString(R.string.noArticlesResponse));
        retryButton.setVisibility(View.VISIBLE);
        loading_spinner.setVisibility(View.GONE);

        // If there are articles, update the adapter
        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        mAdapter.clear();
    }

    @Override
    // This method initialises the contents of the settings menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

