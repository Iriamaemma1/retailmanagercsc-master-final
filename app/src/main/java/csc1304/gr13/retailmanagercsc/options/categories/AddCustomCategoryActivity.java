package csc1304.gr13.retailmanagercsc.options.categories;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputLayout;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;

import csc1304.gr13.retailmanagercsc.R;
import csc1304.gr13.retailmanagercsc.history.models.User;

/**
 * Created by CS1304 on 8/02/2021.
 */
public class AddCustomCategoryActivity extends AppCompatActivity {

    private EditText selectNameEditText;
    private Button selectColorButton;
    private Button addCustomCategoryButton;
    private User user;
    private ImageView iconImageView;
    private int selectedColor = Color.parseColor("#000000");
    private TextInputLayout selectNameInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_custom_category);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add custom category");


    }

    private void dataUpdated() {
        if (user == null) return;
        iconImageView = findViewById(R.id.icon_imageview);
        iconImageView.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
        selectNameEditText = findViewById(R.id.select_name_edittext);
        selectNameInputLayout = findViewById(R.id.select_name_inputlayout);
        selectColorButton = findViewById(R.id.select_color_button);
        selectColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(AddCustomCategoryActivity.this,
                        (selectedColor >> 16) & 0xFF,
                        (selectedColor >> 8) & 0xFF,
                        (selectedColor >> 0) & 0xFF);
                colorPicker.show();

                Button okColor = colorPicker.findViewById(R.id.okColorButton);

                okColor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectedColor = colorPicker.getColor();
                        iconImageView.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
                        colorPicker.dismiss();
                    }
                });
            }
        });

        addCustomCategoryButton = findViewById(R.id.add_custom_category_button);
        addCustomCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    addCustomCategory(selectNameEditText.getText().toString(), "#" + Integer.toHexString(selectedColor));
                } catch (Exception e) {
                    selectNameInputLayout.setError(e.getMessage());
                }


            }
        });
    }

    private void addCustomCategory(String categoryName, String categoryHtmlCode) throws Exception {
        if(categoryName == null || categoryName.length() == 0)
            throw new Exception("Entry name length should be > 0");

        finish();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        onBackPressed();
        return true;
    }
}
