package lin.com.executionengine;

import java.io.Serializable;

/**
 * 展示重载方法匹配优先级的示例
 * @author lin
 * @date 2021/7/7 22:15
 **/
public class Overload {

//    public static void sayHello(Object arg){
//        System.out.println("hello Object");
//    }

//    public static void sayHello(int arg){
//        System.out.println("hello int");
//    }

//    public static void sayHello(long arg){
//        System.out.println("hello long");
//    }

//    public static void sayHello(Character arg){
//        System.out.println("hello character");
//    }

//    public static void sayHello(char arg){
//        System.out.println("hello char");
//    }

    public static void sayHello(char... arg){
        System.out.println("hello char...");
    }

//    public static void sayHello(Serializable arg){
//        System.out.println("hello serializable");
//    }

    public static void main(String[] args) {
        //如果注释掉sayHello(char arg)方法，则会变为 hello int。这时发生了一次自动类型转换，'a'除了可以代表一个字符串，还可以代表数字 97
        // （字符 'a'的Unicode数值为十进制数97），因此参数类型为int的重载也是合适的。
        //我们继续注释掉 sayHello(int arg)方法，那输出则会变为 hello long。
        //这时发生了两次类型转换，'a'转换成整数97之后，进一步转换为长整数97L，匹配了参数类型为long的重载。
        // 代码中没有写其他类型的重载，不过实际上自动转型还能继续发生多次，按照 char>int>long>float>double 的顺序转型进行匹配，
        // 但不会匹配到byte和short类型的重载，因为char到byte或short的转换是不安全的。
        //我们继续注释掉sayHello(long arg),那输出会变为 hello character.
        //这时发生了一次自动装箱，'a'被包装为它的封装类型 java.lang.Character，所以匹配到了参数类型为Character的重载
        //继续注释掉sayHello(Character arg)方法，那输出会变为 hello serializable。
        //这个输出可能会让人摸不着头脑，一个字符与序列化有什么关系？出现 hello serializable 是因为
        // java.lang.Serializable是java.lang.Character类实现的一个接口，当自动装箱后还是找不到所属类型，但是找到了装箱类所实现的接口类型，
        // 所以紧接着又发生一次自动转型。char可以转型成int，但是Character是绝对不会转型为Integer的，它只能安全的转型为它实现的接口或者父类。
        // Character还实现了另外一个接口java.lang.Comparable<Character`>，如果同时出现两个参数都是该类的实现的父类或接口时，
        // 由于此时它们的优先级是相同的，编译器无法确定要自动转型为哪种类型，会提示 类型模糊（Type Ambiguous） 并拒绝编译。
        // 程序必须在调用时显示地指定字面量的静态类型，如sayHello((Comparable<Character·>) 'a')，才能编译通过。
        // 但如果大家愿意花费一点时间，绕过Javac编译器，自己去构造出表达相同语义的字节码，将会发现这是能够通过Java虚拟机的类加载校验，
        // 而且能够被Java虚拟机正常执行的，但是会选择Serializable还是Comparable<Character·>的重载方法并不能事先确定，
        // 这是《Java虚拟机规范》所允许的，在之前的文章介绍接口方法解析过程是曾经提到过。
        //下面继续注释掉sayHello(Serializable arg)，输出会变为 hello Object。
        //这时是char装箱后转型为父类了，如果有多个父类，那将在继承关系中从下往上开始搜索，越接近上层的优先级越低。
        // 即使方法调用传入的参数值为null时，这个规则依然适用。
        //我们把sayHello(Object arg)也注释掉，输出将会变为 hello char... 。
        sayHello('a');
    }
}
