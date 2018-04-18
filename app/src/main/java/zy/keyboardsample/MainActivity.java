package zy.keyboardsample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import zy.keyboard.InputAction;
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

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKeyboard(editText);
            }
        });

    }

    private void showKeyboard(final EditText editText) {

        final NumberKeyboard keyboard = new NumberKeyboard(this);
        final OnInputListener onInputListener = new OnInputListener() {
            @Override
            public void onInputChanged(String text) {
                editText.setText(text);
            }

            @Override
            public void onInputFinished(String text) {
                editText.setText(text);
                editText.setSelection(text.length());
            }
        };
        keyboard.setOnInputListener(onInputListener);
        keyboard.setOnKeyCodeListener(new OnKeyCodeListener() {
            @Override
            public boolean onKeyDown(int keyCode) {
                if (InputAction.ENTER == keyCode
                        || InputAction.KEYBOARD_HIDE == keyCode) {
                    keyboard.detach();
                    onInputListener.onInputFinished(keyboard.getText());
                    return true;
                }
                return false;
            }
        });
        keyboard.attach(this, editText);
    }
}
