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
     * @param e     e
     * @param start selection start
     * @param end   selection end
     * @return Editable
     */
    Editable process(Editable e, int start, int end);

    class Factory {

        static InputProcessor NUM_ZERO = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "0", start, end);
            }
        };

        static InputProcessor NUM_ONE = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "1", start, end);
            }
        };

        static InputProcessor NUM_TWO = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "2", start, end);
            }
        };

        static InputProcessor NUM_THREE = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "3", start, end);
            }
        };

        static InputProcessor NUM_FOUR = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "4", start, end);
            }
        };

        static InputProcessor NUM_FIVE = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "5", start, end);
            }
        };

        static InputProcessor NUM_SIX = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "6", start, end);
            }
        };

        static InputProcessor NUM_SEVEN = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "7", start, end);
            }
        };

        static InputProcessor NUM_EIGHT = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "8", start, end);
            }
        };

        static InputProcessor NUM_NINE = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "9", start, end);
            }
        };

        static InputProcessor NUM_POINT = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                String str = e.toString();
                char charPoint = '.';
                if (str.indexOf(charPoint) >= 0) {
                    return e;
                }
                return e.append(charPoint);
            }
        };

        static InputProcessor NUM_DOUBLE_ZERO = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return insert(e, "00", start, end);
            }
        };

        static InputProcessor KEYBOARD_HIDE = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return e;
            }
        };

        static InputProcessor DELETE = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                if (TextUtils.isEmpty(e)) {
                    Selection.setSelection(e, 0);
                    return e;
                }
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

        static InputProcessor ENTER = new InputProcessor() {
            @Override
            public Editable process(Editable e, int start, int end) {
                return e;
            }
        };

        @SuppressLint("UseSparseArrays")
        private static final Map<Integer, InputProcessor> PROCESSOR_MAP = new HashMap<>();

        static {
            PROCESSOR_MAP.put(InputAction.NUM_ZERO, NUM_ZERO);
            PROCESSOR_MAP.put(InputAction.NUM_ONE, NUM_ONE);
            PROCESSOR_MAP.put(InputAction.NUM_TWO, NUM_TWO);
            PROCESSOR_MAP.put(InputAction.NUM_THREE, NUM_THREE);
            PROCESSOR_MAP.put(InputAction.NUM_FOUR, NUM_FOUR);
            PROCESSOR_MAP.put(InputAction.NUM_FIVE, NUM_FIVE);
            PROCESSOR_MAP.put(InputAction.NUM_SIX, NUM_SIX);
            PROCESSOR_MAP.put(InputAction.NUM_SEVEN, NUM_SEVEN);
            PROCESSOR_MAP.put(InputAction.NUM_EIGHT, NUM_EIGHT);
            PROCESSOR_MAP.put(InputAction.NUM_NINE, NUM_NINE);
            PROCESSOR_MAP.put(InputAction.NUM_POINT, NUM_POINT);
            PROCESSOR_MAP.put(InputAction.NUM_DOUBLE_ZERO, NUM_DOUBLE_ZERO);
            PROCESSOR_MAP.put(InputAction.KEYBOARD_HIDE, KEYBOARD_HIDE);
            PROCESSOR_MAP.put(InputAction.DELETE, DELETE);
            PROCESSOR_MAP.put(InputAction.ENTER, ENTER);
        }

        public static InputProcessor get(int action) {
            if (PROCESSOR_MAP.containsKey(action)) {
                return PROCESSOR_MAP.get(action);
            }
            return new InputProcessor() {
                @Override
                public Editable process(Editable e, int start, int end) {
                    return e;
                }
            };
        }

        private static Editable insert(Editable e, String input, int start, int end) {
            if (start == end) {
                Editable insert = e.insert(start, input);
                Selection.setSelection(insert, start + input.length());
                return insert;
            }
            Editable replace = e.replace(start, end, input);
            Selection.setSelection(replace, start + input.length());
            return replace;
        }

    }

}
