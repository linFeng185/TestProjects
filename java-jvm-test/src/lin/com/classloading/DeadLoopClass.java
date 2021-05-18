package lin.com.classloading;

/**
 * 测试类初始化时的进程阻塞【<clinit>()方法】
 * @author lin
 * @date 2021/5/10 22:01
 **/
public class DeadLoopClass {
    static{
        //如果不加上这个if语句，编译器将提示“Initializer does not complete normally”，并拒绝编译
        if(true){
            System.out.println(Thread.currentThread()+"init DeadLoopClass");
            while(true){

            }
        }
    }

    public static void main(String [] args){
        Runnable script = () -> {
            System.out.println(Thread.currentThread()+"start");
            DeadLoopClass dlc=new DeadLoopClass();
            System.out.println(Thread.currentThread()+"run over");
        };
        Thread thread1=new Thread(script);
        Thread thread2=new Thread(script);
        thread1.start();
        thread2.start();
    }
}
