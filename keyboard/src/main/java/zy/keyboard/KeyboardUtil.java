package zy.keyboard;

import android.os.Build;
import android.text.InputType;
import android.widget.EditText;

import java.lang.reflect.Method;

/**
 * @author zhangyuan
 * @date 2018/4/2.
 */
public class KeyboardUtil {

    public static void hideSoftInputMethod(EditText ed) {

        int currentVersion = Build.VERSION.SDK_INT;
        if (currentVersion >= Build.VERSION_CODES.LOLLIPOP) {
            ed.setShowSoftInputOnFocus(false);
            return;
        }
        String methodName = null;
        if (currentVersion >= Build.VERSION_CODES.JELLY_BEAN) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        } else if (currentVersion >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        } else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
