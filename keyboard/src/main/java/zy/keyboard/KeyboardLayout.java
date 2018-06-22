package zy.keyboard;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyuan
 * created on 2018/3/30.
 */
public class KeyboardLayout extends FrameLayout {

    private EditText attachedView;

    private OnInputListener onInputListener;

    private OnKeyCodeListener onKeyCodeListener;

    public KeyboardLayout(Context context) {
        this(context, null);
    }

    public KeyboardLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public void attach(Activity activity, EditText editText) {
        ViewGroup rootView = (ViewGroup) activity.findViewById(android.R.id.content);
        detach();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.BOTTOM;
        rootView.addView(this, params);
        attach(editText);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    public void detach() {
        ViewParent parent = getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(this);
        }
    }

    public void attach(EditText editText) {
        this.attachedView = editText;
    }

    public void setKeyboardView(@LayoutRes int layoutRes) {
        setKeyboardView(LayoutInflater.from(getContext()).inflate(layoutRes, this, false));
    }

    private void setKeyboardView(View contentView) {
        addView(contentView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        initView();
    }

    private void initView() {
        List<KeyboardView> keyboardViewList = getKeyboardViewList(this);
        for (KeyboardView view : keyboardViewList) {
            ((View) view).setOnClickListener(new InputClickListener());
        }
    }

    private class InputClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            if (attachedView == null) {
                throw new RuntimeException("please attach EditText");
            }
            final EditText editText = attachedView;
            if (v instanceof KeyboardView) {
                KeyboardView view = (KeyboardView) v;
                int action = view.getAction();
                boolean interceptKeyDown = onKeyCodeListener != null
                        && onKeyCodeListener.onKeyDown(action);
                if (!interceptKeyDown) {
                    Editable afterInput = InputProcessor.Factory.get(action).process(
                            editText.getText()
                    );
                    if (onInputListener != null) {
                        onInputListener.onInputChanged(afterInput.toString());
                    }
                    editText.setSelection(Selection.getSelectionStart(afterInput));
                }
            }
        }
    }

    private List<KeyboardView> getKeyboardViewList(ViewGroup parent) {
        List<KeyboardView> childList = new ArrayList<>();
        if (parent != null && parent.getChildCount() > 0) {
            for (int i = 0, childCount = parent.getChildCount(); i < childCount; i++) {
                View childAt = parent.getChildAt(i);
                if (childAt instanceof KeyboardView) {
                    childList.add((KeyboardView) childAt);
                }
                if (childAt instanceof ViewGroup) {
                    List<KeyboardView> childKeyboardViewList = getKeyboardViewList((ViewGroup) childAt);
                    childList.addAll(childKeyboardViewList);
                }
            }
        }
        return childList;
    }

    public String getText() {
        return attachedView.getText().toString();
    }

    public OnInputListener getOnInputListener() {
        return onInputListener;
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    public OnKeyCodeListener getOnKeyCodeListener() {
        return onKeyCodeListener;
    }

    public void setOnKeyCodeListener(OnKeyCodeListener onKeyCodeListener) {
        this.onKeyCodeListener = onKeyCodeListener;
    }
}
