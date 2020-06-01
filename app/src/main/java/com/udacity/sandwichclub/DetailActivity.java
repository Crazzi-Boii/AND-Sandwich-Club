package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
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

    private ImageView imageIv;
    private TextView alsoknownasTv;
    private TextView ingredientsTv;
    private TextView originTv;
    private TextView descriptionTv;

    private void populateUI() {
        showData();
        if (sandwich.getAlsoKnownAs() != null) {
            String temp = alsoknownasTv.getText().toString();
            for (String temp1 : sandwich.getAlsoKnownAs()) {
                temp = temp.concat(temp1);
                temp = temp.concat("\n");
            }
            temp = temp.trim();
            alsoknownasTv.setText(temp);
        } else
            alsoknownasTv.setText(R.string.noAlsoKnownAsFound);

        if (sandwich.getIngredients() != null) {
            String temp = ingredientsTv.getText().toString();
            for (String temp1 : sandwich.getIngredients()) {
                temp = temp.concat(temp1);
                temp = temp.concat("\n");
            }
            temp = temp.trim();
            ingredientsTv.setText(temp);
        } else
            ingredientsTv.setText(R.string.noIngredients);

        if (sandwich.getPlaceOfOrigin() != null)
            originTv.setText(sandwich.getPlaceOfOrigin());

        if (sandwich.getDescription() != null)
            descriptionTv.setText(sandwich.getDescription());
        else
            descriptionTv.setText(R.string.noDescription);

    }

    public void initViews() {
        imageIv = findViewById(R.id.image_iv);
        alsoknownasTv = findViewById(R.id.also_known_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);
        originTv = findViewById(R.id.origin_tv);
        descriptionTv = findViewById(R.id.description_tv);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initViews();


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
                .into(imageIv);

        setTitle(sandwich.getMainName());
    }
}
