package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private static Sandwich sandwich;

    public static void showData() {
        // GETTING LIST DATA

        Log.e("-----------------------", "--------------------------------");
        Log.e("showData: Main Name", "" + sandwich.getMainName());

        if (sandwich.getAlsoKnownAs() != null)
            for (String a : sandwich.getAlsoKnownAs())
                Log.e("showData: Also Known As", "" + a);
        else
            Log.e("showData: Also Known As", "null value");

        if (sandwich.getPlaceOfOrigin() != null)
            Log.e("showData: placeOfOrigin", "" + sandwich.getPlaceOfOrigin());

        if (sandwich.getDescription() != null)
            Log.e("showData: Description", "" + sandwich.getDescription());

        if (sandwich.getImage() != null)
            Log.e("showData: Image", "" + sandwich.getImage());

        if (sandwich.getIngredients() != null)
            for (String a : sandwich.getIngredients())
                Log.e("showData: Ingredients", "" + a);
        else
            Log.e("showData: Ingredients", "null value");

    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        assert intent != null;
        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }
}
