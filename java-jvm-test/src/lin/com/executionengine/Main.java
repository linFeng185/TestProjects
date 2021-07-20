package lin.com.executionengine;

/**
 * 
 * @author lin
 * @date 2021/6/15 22:10
 **/
public class Main {

    /**
     * 上面代码并没有回收掉placeholder所占的内存，这能说的过去
     * 因为在执行垃圾回收的方法时，它还在方法的作用域内，虚拟机自然不敢回收掉placeholder的内存。
     * @param args
     */
    /*public static void main(String[] args) {
        byte [] placeholder = new byte[64 * 1024 * 1024];
        System.gc();
    }*/

    /**
     * 加了花括号之后，placeholder的作用域已经被限制在了花括号以内，按道理来说是可以被回收掉的
     * 但执行一下程序，可以看到其实并没有回收掉它的内存
     * @param args
     */
    /*public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        System.gc();
    }*/

    /**
     * 这个修改看起来很莫名其妙，但运行一下程序，却发现这次内存真的被正确回收了
     * @param args
     */
    /*public static void main(String[] args) {
        {
            byte[] placeholder = new byte[64 * 1024 * 1024];
        }
        int a = 0;
        System.gc();
    }*/

    /**
     * 如果一个局部变量定义了但没有赋初始值，那它是完全不能使用的
     * @param args
     */
    /*public static void main(String[] args) {
        int a;
        System.out.println(a);
    }*/
}
