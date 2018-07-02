package com.example.android.worldcupnews;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Article>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    TextView emptyStateView;
    ProgressBar loading_spinner;

    // URL for the article data from the Guardian API
    private static final String guardian_API_URL = "https://content.guardianapis.com/search?from-date=2018-06-14&order-by=newest&section=football&page-size=20&q=world%20cup&api-key=0905eb78-ce98-4ba4-89d0-a45d6022cd9d";

    // Adapter for the list of articles
    private ArticleAdapter mAdapter;

    // Constant value for the loader ID
    private static final int loaderID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the required views in the layout
        emptyStateView = findViewById(R.id.emptyview);
        loading_spinner = findViewById(R.id.loading_spinner);
        ListView listView = findViewById(R.id.listview);

        // Set the empty view for the ListView
        listView.setEmptyView(emptyStateView);

        // Create a new ArticleAdapter
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());

        // Set the adapter on the ListView
        listView.setAdapter(mAdapter);

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

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // Get a ref for the LoaderManager
        LoaderManager loaderManager = getLoaderManager();

        if (networkInfo != null) {
            loaderManager.initLoader(loaderID, null, this);
        } else {
            loading_spinner.setVisibility(View.GONE);
            emptyStateView.setText("Check network connection");
        }
    }

    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new ArticleLoader(this, guardian_API_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();
        emptyStateView.setText("No articles found :(");
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
}

