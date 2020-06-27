package com.example.dentalplus.clase;

import android.app.Activity;
import android.content.Context;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class InputValidation {
    private Context context;

    public InputValidation(Context context) {
        this.context = context;
    }
    public boolean isInputEditTextFilled(EditText editText, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty()) {
            editText.requestFocus();
            editText.setError(message);
            hideKeyboardFrom(editText);
            return false;
        }
        return true;
    }
    public boolean isInputEditTextEmail(EditText editText, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            editText.requestFocus();
            editText.setError(message);
            hideKeyboardFrom(editText);
            return false;
        }
        return true;
    }
    public boolean isInputEditTextPhone(EditText editText, String message) {
        String value = editText.getText().toString().trim();
        if (value.isEmpty() || !Patterns.PHONE.matcher(value).matches()) {
            editText.requestFocus();
            editText.setError(message);
            hideKeyboardFrom(editText);
            return false;
        }
        return true;
    }

    public boolean isInputEditTextMatches(EditText editText1,EditText editText2, String message) {
        String value1 = editText1.getText().toString().trim();
        String value2 = editText2.getText().toString().trim();
        if (!value1.contentEquals(value2)) {
            editText2.setError(message);
            hideKeyboardFrom(editText2);
            return false;
        }
        if(!value2.contentEquals(value1)){
            editText1.setError(message);
            hideKeyboardFrom(editText2);
            return false;
        }
        return true;
    }

    /**
     * method to Hide keyboard
     *
     * @param view
     */
    private void hideKeyboardFrom(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
