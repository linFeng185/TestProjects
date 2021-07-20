package lin.com.executionengine;

import java.util.Random;

/**
 * 方法静态分派演示
 * @author lin
 * @date 2021/7/7 21:36
 **/
public class StaticDispatch {

    static abstract class Human{

    }

    static class Man extends Human{

    }

    static class Woman extends Human{

    }

    public void sayHello(Human guy){
        System.out.println("hello,guy");
    }

    public void sayHello(Man guy){
        System.out.println("hello,gentleman");
    }

    public void sayHello(Woman guy){
        System.out.println("hello,lady");
    }

    public static void main(String[] args) {
        //实际类型变化
        Human human = (new Random()).nextBoolean() ? new Man() : new Woman();
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(woman);
        //静态类型变化
        sr.sayHello((Man)man);
        sr.sayHello((Woman)woman);
    }
}
