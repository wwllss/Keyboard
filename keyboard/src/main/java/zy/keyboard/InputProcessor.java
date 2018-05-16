package zy.keyboard;

import android.annotation.SuppressLint;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangyuan
 * created on 2018/3/30.
 */
public interface InputProcessor {

    /**
     * @param e e
     * @return Editable
     */
    Editable process(Editable e);

    class Factory {

        static InputProcessor DEFAULT = new InputProcessor() {
            @Override
            public Editable process(Editable e) {
                return e;
            }
        };

        static InputProcessor NUM_POINT = new InputProcessor() {
            @Override
            public Editable process(Editable e) {
                String str = e.toString();
                char charPoint = '.';
                if (str.indexOf(charPoint) >= 0) {
                    return e;
                }
                return e.append(charPoint);
            }
        };

        static InputProcessor KEYBOARD_HIDE = DEFAULT;

        static InputProcessor DELETE = new InputProcessor() {
            @Override
            public Editable process(Editable e) {
                if (TextUtils.isEmpty(e)) {
                    Selection.setSelection(e, 0);
                    return e;
                }
                int start = Selection.getSelectionStart(e);
                int end = Selection.getSelectionEnd(e);
                if (start == end) {
                    int index = start - 1;
                    if (index >= 0) {
                        Editable delete = e.delete(index, end);
                        Selection.setSelection(delete, index);
                        return delete;
                    } else {
                        Selection.setSelection(e, 0);
                        return e;
                    }
                }
                Editable delete = e.delete(start, end);
                Selection.setSelection(delete, start);
                return delete;
            }
        };

        static InputProcessor ENTER = DEFAULT;

        @SuppressLint("UseSparseArrays")
        private static final Map<Integer, InputProcessor> PROCESSOR_MAP = new HashMap<>();

        static {
            PROCESSOR_MAP.put(InputAction.NUM_ZERO, newInsert("0"));
            PROCESSOR_MAP.put(InputAction.NUM_ONE, newInsert("1"));
            PROCESSOR_MAP.put(InputAction.NUM_TWO, newInsert("2"));
            PROCESSOR_MAP.put(InputAction.NUM_THREE, newInsert("3"));
            PROCESSOR_MAP.put(InputAction.NUM_FOUR, newInsert("4"));
            PROCESSOR_MAP.put(InputAction.NUM_FIVE, newInsert("5"));
            PROCESSOR_MAP.put(InputAction.NUM_SIX, newInsert("6"));
            PROCESSOR_MAP.put(InputAction.NUM_SEVEN, newInsert("7"));
            PROCESSOR_MAP.put(InputAction.NUM_EIGHT, newInsert("8"));
            PROCESSOR_MAP.put(InputAction.NUM_NINE, newInsert("9"));
            PROCESSOR_MAP.put(InputAction.NUM_POINT, NUM_POINT);
            PROCESSOR_MAP.put(InputAction.NUM_DOUBLE_ZERO, newInsert("00"));
            PROCESSOR_MAP.put(InputAction.KEYBOARD_HIDE, KEYBOARD_HIDE);
            PROCESSOR_MAP.put(InputAction.DELETE, DELETE);
            PROCESSOR_MAP.put(InputAction.ENTER, ENTER);
        }

        public static InputProcessor get(int action) {
            if (PROCESSOR_MAP.containsKey(action)) {
                return PROCESSOR_MAP.get(action);
            }
            return DEFAULT;
        }

        static InputProcessor newInsert(String inputContent) {
            return new InsertProcessor(inputContent);
        }

    }

}
