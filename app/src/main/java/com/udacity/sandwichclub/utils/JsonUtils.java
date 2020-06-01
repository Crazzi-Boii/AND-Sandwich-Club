package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {
    private static Sandwich sandwich = new Sandwich();

    public static Sandwich parseSandwichJson(String json) {
        List<String> alsoKnownAsList = new ArrayList<>();
        List<String> ingredientsList = new ArrayList<>();
        try {
            JSONObject sandwitchJobj = new JSONObject(json);

            JSONObject sandwitchNameJobj = new JSONObject(sandwitchJobj.getString("name"));
            sandwich.setMainName(sandwitchNameJobj.getString("mainName"));

            String alsoKnownAs = sandwitchNameJobj.getString("alsoKnownAs");
            if (alsoKnownAs.length() > 2) {
                alsoKnownAs = alsoKnownAs.substring(2, alsoKnownAs.length() - 2);
                String[] a = alsoKnownAs.split("\",\"");
                alsoKnownAsList.addAll(Arrays.asList(a));
                sandwich.setAlsoKnownAs(alsoKnownAsList);
            } else {
                sandwich.setAlsoKnownAs(null);
            }

            String placeOfOrigin = sandwitchJobj.getString("placeOfOrigin");
            if (placeOfOrigin.length() > 0)
                sandwich.setPlaceOfOrigin(placeOfOrigin);
            else
                sandwich.setPlaceOfOrigin("Unknown");

            String description = sandwitchJobj.getString("description");
            if (description != null)
                sandwich.setDescription(description);

            String image = sandwitchJobj.getString("image");
            if (image != null)
                sandwich.setImage(image);

            String ingredients = sandwitchJobj.getString("ingredients");
            if (ingredients.length() > 2) {

                ingredients = ingredients.substring(2, ingredients.length() - 2);
                String[] a = ingredients.split("\",\"");
                ingredientsList.addAll(Arrays.asList(a));
                sandwich.setIngredients(ingredientsList);
            } else {
                sandwich.setIngredients(null);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sandwich;
    }
}
