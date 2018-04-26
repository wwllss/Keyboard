package zy.keyboardsample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import zy.keyboard.InputAction;
import zy.keyboard.NumberKeyboard;
import zy.keyboard.OnInputListener;
import zy.keyboard.OnKeyCodeListener;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * @author zhangyuan
 * created on 2018/4/3.
 */
public class KeyboardPop extends PopupWindow {

    private NumberKeyboard keyboard;

    private OnInputListener onInputListener;

    public KeyboardPop(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View contentView = View.inflate(context, R.layout.pop_number_keyboard, null);
        setContentView(contentView);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setWidth(MATCH_PARENT);
        setHeight(WRAP_CONTENT);
        setTouchable(true);
        setFocusable(false);
        setOutsideTouchable(false);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (onInputListener != null) {
                    onInputListener.onInputFinished(keyboard.getText());
                }
            }
        });
        keyboard = (NumberKeyboard) contentView.findViewById(R.id.keyboard);
        keyboard.setOnKeyCodeListener(new OnKeyCodeListener() {
            @Override
            public boolean onKeyDown(int keyCode) {
                if (keyCode == InputAction.ENTER
                        || keyCode == InputAction.KEYBOARD_HIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
        keyboard.setOnInputListener(new OnInputListener() {
            @Override
            public void onInputChanged(String text) {
                if (onInputListener != null) {
                    onInputListener.onInputChanged(text);
                }
            }

            @Override
            public void onInputFinished(String text) {
                if (onInputListener != null) {
                    onInputListener.onInputFinished(text);
                }
            }
        });
    }

    public void show(Activity activity) {
        View actContentView = activity.findViewById(android.R.id.content);
        showAtLocation(actContentView, Gravity.BOTTOM, 0, 0);
    }

    public void attach(EditText editText) {
        keyboard.attach(editText);
    }

    public OnInputListener getOnInputListener() {
        return onInputListener;
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }
}
