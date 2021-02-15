package csc1304.gr13.retailmanagercsc.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import java.lang.ref.WeakReference;
import java.math.BigDecimal;
import java.text.NumberFormat;


/**
 * Created by CSC 1304 group 13 on 8/02/2021.
 */

public class MoneyTextWatcher implements TextWatcher {
    private final WeakReference<EditText> editTextWeakReference;

    public MoneyTextWatcher(EditText editText) {
        editTextWeakReference = new WeakReference<EditText>(editText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        EditText editText = editTextWeakReference.get();
        if (editText == null) return;
        String s = editable.toString();
        String formatedEditText = s.replace("UGX ","$");
        if (s.isEmpty()) return;
        editText.removeTextChangedListener(this);
        String cleanString = formatedEditText.replaceAll("[$,.]", "");
        BigDecimal parsed = new BigDecimal(cleanString).setScale(2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR);
        String formatted = NumberFormat.getCurrencyInstance().format(parsed);
        Log.d("FormattedString",formatted);
        String finalString =formatted.replace("$","UGX ");
        Log.d("FinalSting",finalString);

        editText.setText(finalString);
        editText.setSelection(finalString.length());
        editText.addTextChangedListener(this);
    }
}
