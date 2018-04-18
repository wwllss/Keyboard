package zy.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * @author zhangyuan
 * created on 2018/3/30.
 */
class KeyboardButton extends android.support.v7.widget.AppCompatTextView implements KeyboardView {

    public static final int NO_ACTION = -1;

    private int action;

    public KeyboardButton(Context context) {
        this(context, null);
    }

    public KeyboardButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyboardButton);
        action = a.getInt(R.styleable.KeyboardButton_input_action, NO_ACTION);
        a.recycle();
    }

    @Override
    public int getAction() {
        return action;
    }

    void setAction(int action) {
        this.action = action;
    }
}
