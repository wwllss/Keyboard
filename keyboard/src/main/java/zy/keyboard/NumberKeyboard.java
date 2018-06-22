package zy.keyboard;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author zhangyuan
 * created on 2018/3/30.
 */
public class NumberKeyboard extends KeyboardLayout {

    public NumberKeyboard(Context context) {
        this(context, null);
    }

    public NumberKeyboard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NumberKeyboard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setKeyboardView(R.layout.widget_number_keyboard);
    }
}
