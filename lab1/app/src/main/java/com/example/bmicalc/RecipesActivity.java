package com.example.bmicalc;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bmicalc.DTO.RecipeDTO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class RecipesActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        final TextView textView = findViewById(R.id.result_title);
        final ImageView imageView = findViewById(R.id.form_recipe_image);

        final TextView recipeTitleView = findViewById(R.id.form_recipe_title);
        final TextView recipeView = findViewById(R.id.form_recipe_recipe);
        ArrayList<RecipeDTO> recipeDTOS = new ArrayList<>();
        Bundle b = getIntent().getExtras();

        int kcal = b.getInt("kcal");

        try {
            InputStream inputStream = getAssets().open("recipies.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);


            JSONArray jsonArray = new JSONArray(new String(buffer));
            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                RecipeDTO recipeDTO = new RecipeDTO(
                        jsonObject.getString("name"),
                        jsonObject.getInt("kcal"),
                        jsonObject.getString("recipe"),
                        jsonObject.getString("image")
                );

                recipeDTOS.add(recipeDTO);
            }


        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < recipeDTOS.size(); i++) {
            RecipeDTO recipeDTO = recipeDTOS.get(i);

            if (recipeDTO.getKcal() <= kcal || i == recipeDTOS.size() - 1) {
                Bitmap bmImg = BitmapFactory.decodeFile(recipeDTO.getImage());
                imageView.setImageBitmap(bmImg);

                try {
                    InputStream ims = getAssets().open(recipeDTO.getImage());
                    Drawable d = Drawable.createFromStream(ims, null);
                    imageView.setImageDrawable(d);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                recipeTitleView.setText(recipeDTO.getName());
                recipeView.setText(Html.fromHtml(recipeDTO.getRecipe()));
                recipeView.setMovementMethod(new ScrollingMovementMethod());
                break;
            }
        }

    }

    public void onButtonClick(View v) {
        Intent myIntent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(myIntent);
    }


    public void start(View view) {
        Intent myIntent = new Intent(getBaseContext(), QuizActivity.class);
        startActivity(myIntent);
    }
}