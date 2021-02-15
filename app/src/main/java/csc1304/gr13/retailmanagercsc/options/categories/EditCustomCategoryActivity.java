package csc1304.gr13.retailmanagercsc.options.categories;

import android.content.res.ColorStateList;
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
/**
 * Created by CS1304 on 8/02/2021.
 */

public class EditCustomCategoryActivity extends AppCompatActivity {

    private EditText selectNameEditText;
    private Button selectColorButton;
    private Button editCustomCategoryButton;
    private ImageView iconImageView;
    private int selectedColor;
    private String categoryID;
    private Button removeCustomCategoryButton;
    private String categoryName;
    private TextInputLayout selectNameInputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        categoryID = getIntent().getExtras().getString("category-id");
        categoryName = getIntent().getExtras().getString("category-name");
        selectedColor = getIntent().getExtras().getInt("category-color");

        setContentView(R.layout.activity_edit_custom_category);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Edit custom category");


        iconImageView = findViewById(R.id.icon_imageview);
        iconImageView.setBackgroundTintList(ColorStateList.valueOf(selectedColor));
        selectNameEditText = findViewById(R.id.select_name_edittext);
        selectNameEditText.setText(categoryName);
        selectNameInputLayout = findViewById(R.id.select_name_inputlayout);
        selectColorButton = findViewById(R.id.select_color_button);
        selectColorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(EditCustomCategoryActivity.this,
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

        editCustomCategoryButton = findViewById(R.id.edit_custom_category_button);
        editCustomCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    editCustomCategory(selectNameEditText.getText().toString(), "#" + Integer.toHexString(selectedColor));
                } catch (Exception e) {
                    selectNameInputLayout.setError(e.getMessage());
                }

            }
        });

        removeCustomCategoryButton = findViewById(R.id.remove_custom_category_button);

    }

    private void editCustomCategory(String categoryName, String categoryHtmlCode) throws Exception {
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
