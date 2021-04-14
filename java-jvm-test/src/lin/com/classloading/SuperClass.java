package lin.com.classloading;

/**
 * 被动使用类字段演示一：通过子类访问父类的静态字段，不会触发子类的初始化
 * @author lin
 * @date 2021/3/8 23:36
 **/
public class SuperClass {

    static {
        System.out.println("superClass init");
    }

    public static int val=123;
}
