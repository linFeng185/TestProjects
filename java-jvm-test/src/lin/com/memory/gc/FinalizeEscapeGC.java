package lin.com.memory.gc;

import java.awt.*;

/**
 * 演示对象可以在被回收时自救
 * @author lin
 * @date 2020/11/7 18:37
 **/
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC SAVE_HOOK=null;

    public void isAlive(){
        System.out.println("没想到吧程太郎！这就是我的逃跑路线！");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("finalize方法执行中");
        super.finalize();
        FinalizeEscapeGC.SAVE_HOOK=this;
    }

    public static void main(String [] args) throws InterruptedException {
        SAVE_HOOK=new FinalizeEscapeGC();
        //对象第一次成功自救
        SAVE_HOOK=null;
        System.gc();
        //因为finalize方法优先级很低，先暂停一会，等待它
        Thread.sleep(500);
        if(SAVE_HOOK !=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("没想到吧程太郎！这就是我的逃跑路。。。。");
            System.out.println("啊！");
        }
        //下面这段代码和上面的完全相同，但是这次自救却失败了
        SAVE_HOOK=null;
        System.gc();
        //因为finalize方法优先级很低，先暂停一会，等待它
        Thread.sleep(500);
        if(SAVE_HOOK !=null){
            SAVE_HOOK.isAlive();
        }else {
            System.out.println("没想到吧程太郎！这就是我的逃跑路。。。。");
            System.out.println("啊！");
        }
    }
    //要正式回收一个对象的话，该对象至少要被标记两次才可以被回收。
    // 被可达性算法标记了为可回收对象时，对象还有一次自救机会，条件为该对象是否有必要执行一次finalize()方法，
    // 如果对象没有覆盖finalize方法，或者finalize方法已经被虚拟机调用过，那么虚拟机将会将这两种情况都视为“没有必要回收”。
    //如果这个对象被判定为却又必要执行finalize方法，那么该对象将会被放置在一个名为F-Queue队列中，并稍后由一条虚拟机自动建立的低调度优先级的finalizer运行，
    //
}
