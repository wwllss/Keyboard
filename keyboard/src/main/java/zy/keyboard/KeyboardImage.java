package zy.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import static zy.keyboard.KeyboardButton.NO_ACTION;

/**
 * @author zhangyuan
 * created on 2018/3/30.
 */
class KeyboardImage extends android.support.v7.widget.AppCompatImageView implements KeyboardView {

    private int action;

    public KeyboardImage(Context context) {
        this(context, null);
    }

    public KeyboardImage(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardImage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.KeyboardImage);
        action = a.getInt(R.styleable.KeyboardImage_input_action, NO_ACTION);
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
