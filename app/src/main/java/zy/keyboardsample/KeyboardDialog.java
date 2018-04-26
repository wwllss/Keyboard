package zy.keyboardsample;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;

import zy.keyboard.InputAction;
import zy.keyboard.NumberKeyboard;
import zy.keyboard.OnInputListener;
import zy.keyboard.OnKeyCodeListener;

/**
 * @author zhangyuan
 * created on 2018/4/2.
 */
public class KeyboardDialog extends DialogFragment {

    private NumberKeyboard keyboard;

    private OnInputListener onInputListener;

    private ProvideAttachment provideAttachment;

    public static KeyboardDialog newInstance() {
        Bundle args = new Bundle();
        KeyboardDialog fragment = new KeyboardDialog();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_number_keyboard, container, false);
        keyboard = (NumberKeyboard) view.findViewById(R.id.keyboard);
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
        });
        if (provideAttachment == null) {
            throw new RuntimeException("must provide an EditText view to attach");
        } else {
            keyboard.attach(provideAttachment.provide());
        }
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new AppCompatDialog(getActivity(), R.style.BottomDialog);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return dialog;
    }

    public void setOnInputListener(OnInputListener onInputListener) {
        this.onInputListener = onInputListener;
    }

    public void setProvideAttachment(ProvideAttachment provideAttachment) {
        this.provideAttachment = provideAttachment;
    }

    public interface ProvideAttachment {
        EditText provide();
    }

}
