/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.exercise2;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.exercise2.R;
import com.example.android.exercise2.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // Done TODO (26) Create an EditText variable called mSearchBoxEditText
    private TextView mSearchBoxEditText;

    // Done TODO (27) Create a TextView variable called mUrlDisplayTextView
    // Done TODO (28) Create a TextView variable called mSearchResultsTextView
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;

    // Done TODO (71) Create a variable to store a reference to the error message TextView
    private TextView mErrorMessageDisplay;

    // Done TODO (83) Create a ProgressBar variable to store a reference to the ProgressBar
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Done TODO (29) Use findViewById to get a reference to mSearchBoxEditText
        mSearchBoxEditText = (TextView) findViewById(R.id.et_search_box);

        // Done TODO (30) Use findViewById to get a reference to mUrlDisplayTextView
        // Done TODO (31) Use findViewById to get a reference to mSearchResultsTextView
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

        // Done TODO (72) Get a reference to the error TextView using findViewById
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        // Done TODO (84) Get a reference to the ProgressBar using findViewById
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
    }

    // Done TODO (48) Create a method called makeGithubSearchQuery
    // Done TODO (49) Within this method, build the URL with the text from the EditText and set the built URL to the TextView
    public void makeGithubSearchQuery(){
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        // Done TODO (54) Call getResponseFromHttpUrl and display the results in mSearchResultsTextView
        // Done TODO (55) Surround the call to getResponseFromHttpUrl with a try / catch block to catch an IOException
        String githubSearchResult = null;
       /* try {
            githubSearchResult = NetworkUtils.getResponseFromHttpUrl(githubSearchUrl);
            mSearchResultsTextView.setText(githubSearchResult);
        }catch (IOException e){
            e.printStackTrace();
        }*/
       new GithubQueryTask().execute(githubSearchUrl);
        // Done TODO (59) Create a new GithubQueryTask and call its execute method, passing in the url to query
    }

    // Done TODO (73) Create a method called showJsonDataView to show the data and hide the error
    private void showJsonDataView(){
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    // Done TODO (74) Create a method called showErrorMessage to show the error and hide the data
    private void showErrorMessage(){
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
    }

    // Done TODO (56) Create a class called GithubQueryTask that extends AsyncTask<URL, Void, String>
    // Done TODO (57) Override the doInBackground method to perform the query. Return the results. (Hint: You've already written the code to perform the query)
    // Done TODO (58) Override onPostExecute to display the results in the TextView

    public class GithubQueryTask extends AsyncTask<URL, Void, String>{

        // Done TODO (85) Override onPreExecute to set the loading indicator to visible


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            }catch (IOException e){
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {

            // Done TODO (86) As soon as the loading is complete, hide the loading indicator
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                // Done TODO (76) Call showJsonDataView if we have valid, non-null results
                showJsonDataView();
                mSearchResultsTextView.setText(githubSearchResults);
            }
            // Done TODO (75) Call showErrorMessage if the result is null in onPostExecute
            showErrorMessage();
        }
    }

    // Do 2 - 7 in menu.xml ///////////////////////////////////////////////////////////////////////
    // Done TODO (33) Create a menu in xml called main.xml
    // main.xml address at app/res/menu/main.xml
    // Done TODO (34) Add one menu item to your menu
    // Done TODO (35) Give the menu item an id of @+id/action_search
    // Done TODO (36) Set the orderInCategory to 1
    // Done TODO (37) Show this item if there is room (use app:showAsAction, not android:showAsAction)
    // Done TODO (38) Set the title to the search string ("Search") from strings.xml
    // Do 2 - 7 in menu.xml ///////////////////////////////////////////////////////////////////////

    // Done TODO (39) Override onCreateOptionsMenu
    // Done TODO (40) Within onCreateOptionsMenu, use getMenuInflater().inflate to inflate the menu
    // Done TODO (41) Return true to display your menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Done TODO (42) Override onOptionsItemSelected
    // Done TODO (43) Within onOptionsItemSelected, get the ID of the item that was selected
    // Done TODO (44) If the item's ID is R.id.action_search, show a Toast and return true to tell Android that you've handled this menu click
    // Done TODO (45) Don't forgot to call .show() on your Toast
    // Done TODO (46) If you do NOT handle the menu click, return super.onOptionsItemSelected to let Android handle the menu click

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedMenuItem = item.getItemId();
        if (selectedMenuItem == R.id.action_search){
            // Done TODO (50) Remove the Toast message when the search menu item is clicked
            /*Context context = MainActivity.this;
            String message = "Search clicked";
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();*/
            // Done TODO (51) Call makeGithubSearchQuery when the search menu item is clicked
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
