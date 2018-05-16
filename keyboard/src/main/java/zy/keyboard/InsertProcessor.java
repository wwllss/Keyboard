package zy.keyboard;

import android.text.Editable;
import android.text.Selection;

/**
 * @author zhangyuan
 * @date 2018/5/16.
 */
class InsertProcessor implements InputProcessor {

    private final String inputContent;

    InsertProcessor(String inputContent) {
        this.inputContent = inputContent;
    }

    @Override
    public Editable process(Editable e) {
        String input = this.inputContent;
        int start = Selection.getSelectionStart(e);
        int end = Selection.getSelectionEnd(e);
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
