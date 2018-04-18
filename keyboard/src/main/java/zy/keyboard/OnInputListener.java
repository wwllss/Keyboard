package zy.keyboard;

/**
 * @author zhangyuan
 * @date 2018/3/30.
 */
public interface OnInputListener {

    void onInputChanged(String text);

    void onInputFinished(String text);

}
