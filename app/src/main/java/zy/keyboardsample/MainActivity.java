package zy.keyboardsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import zy.keyboard.InputAction;
import zy.keyboard.KeyboardLayout;
import zy.keyboard.KeyboardUtil;
import zy.keyboard.NumberKeyboard;
import zy.keyboard.OnInputListener;
import zy.keyboard.OnKeyCodeListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText editText = findViewById(R.id.edit_text);

        KeyboardUtil.hideSoftInputMethod(editText);

        final CheckBox checkBox = findViewById(R.id.checkbox);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyboard(editText, checkBox.isChecked());
            }
        });

    }

    private void showKeyboard(final EditText editText, boolean checked) {

        final KeyboardLayout keyboard;
        if (checked) {
            keyboard = new KeyboardLayout(this);
            keyboard.setKeyboardView(R.layout.layout_keyboard_number);
        } else {
            keyboard = new NumberKeyboard(this);
        }
        final OnInputListener onInputListener = new OnInputListener() {
            @Override
            public void onInputChanged(String text) {
                editText.setText(text);
            }
        };
        keyboard.setOnInputListener(onInputListener);
        keyboard.setOnKeyCodeListener(new OnKeyCodeListener() {
            @Override
            public boolean onKeyDown(int keyCode) {
                if (InputAction.ENTER == keyCode
                        || InputAction.KEYBOARD_HIDE == keyCode) {
                    keyboard.detach();
                    editText.setSelection(editText.getText().length());
                    return true;
                }
                return false;
            }
        });
        keyboard.attach(this, editText);
    }
}
