package lin.com.classloading;

/**
 * @author lin
 * @date 2021/3/8 23:39
 **/
public class SubClass extends SuperClass {
    static {
        System.out.println("SubClass init");
    }
}
