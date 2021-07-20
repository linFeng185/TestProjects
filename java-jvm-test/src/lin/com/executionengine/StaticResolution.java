package lin.com.executionengine;

/**
 * 方法静态解析演示
 * @author lin
 * @date 2021/7/6 22:19
 **/
public class StaticResolution {

    public static void sayHello(){
        System.out.println("hello world");
    }

    public static void main(String[] args) {
        StaticResolution.sayHello();
    }
}
