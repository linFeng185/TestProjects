package lin.com.classloading;

/**
 * @author lin
 * @date 2021/4/29 22:42
 **/
public class Test {
    static{
        //给变量赋值可以正常编译通过
        i = 0;
        //这行代码编译器会提示“非法向前引用”
//        System.out.print(i);
    }
    private static int i = 1;
}
