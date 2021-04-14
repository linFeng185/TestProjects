package lin.com.classloading;

/**
 * @author lin
 * @date 2021/3/9 22:52
 **/
public class ConstClass {
    static {
        System.out.println("ConstClass init");
    }

    public static final String HELLO_WORLD = "hello world";
}
