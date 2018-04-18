package zy.keyboard;

/**
 * @author zhangyuan
 * @date 2018/3/30.
 */
public class InputAction {

    public static int NUM_ZERO = 0;

    public static int NUM_ONE = 1;

    public static int NUM_TWO = 2;

    public static int NUM_THREE = 3;

    public static int NUM_FOUR = 4;

    public static int NUM_FIVE = 5;

    public static int NUM_SIX = 6;

    public static int NUM_SEVEN = 7;

    public static int NUM_EIGHT = 8;

    public static int NUM_NINE = 9;

    public static int NUM_POINT = 10;

    public static int NUM_DOUBLE_ZERO = 11;

    public static int KEYBOARD_HIDE = 12;

    public static int DELETE = 13;

    public static int ENTER = 14;

    public static boolean isInsert(int action) {
        return action >= NUM_ZERO && action <= NUM_DOUBLE_ZERO;
    }

    public static boolean isDelete(int action) {
        return action == DELETE;
    }

}
