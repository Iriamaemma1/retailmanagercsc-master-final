package csc1304.gr13.retailmanagercsc.options.categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.history.models.Category;
import csc1304.gr13.retailmanagercsc.history.models.User;
import csc1304.gr13.retailmanagercsc.utils.CategoriesHelper;

import java.util.ArrayList;
/**
 * Created by CS1304 on 8/02/2021.
 */

public class CustomCategoriesActivity extends AppCompatActivity {

    private User user;
    private ArrayList<Category> customCategoriesList;
    private CustomCategoriesAdapter customCategoriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_categories);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Custom categories");



        ListView listView = findViewById(R.id.custom_categories_list_view);
        customCategoriesList = new ArrayList<>();
        customCategoriesAdapter = new CustomCategoriesAdapter(this, customCategoriesList, getApplicationContext());
        listView.setAdapter(customCategoriesAdapter);

        findViewById(R.id.add_custom_category_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CustomCategoriesActivity.this, AddCustomCategoryActivity.class));
            }
        });

    }

    private void dataUpdated() {
        if (user == null) return;
        customCategoriesList.clear();
        customCategoriesList.addAll(CategoriesHelper.getCustomCategories(user));
        customCategoriesAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }
}
