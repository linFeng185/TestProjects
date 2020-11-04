package lin.com.memory.gc;

/**
 * 引用计数法的弊端
 * @author lin
 * @date 2020/11/4 22:57
 **/
public class ReferenceCountingGC {

    public Object instance=null;

    private static final int _1MB=1024*1024;

    /**
     * 这个成员属性的唯一意义就是占点内存，以便能在gc日志中看清楚是否有回收过
     */
    private byte[] bigSize=new byte[2*_1MB];

    public static void main(String [] args){
        ReferenceCountingGC objA=new ReferenceCountingGC();
        ReferenceCountingGC objB=new ReferenceCountingGC();
        //这两个对象都有instance字段，赋值令这两个对象在互相引用，实际上这两个对象都不可能再被访问。
        //但是这两个对象都在互相引用对方，导致他们的引用计数都不为零，引用计数算法也就无法回收他们
        objA.instance=objB;
        objB.instance=objA;
        objA=null;
        objB=null;

        //假设在这行发生gc，这两个对象能否被回收？
        System.gc();
        //查看内存回收日志可以清楚的看到，xxxxK->xxxK，意味着虚拟机并没有因为这两个对象互相引用就放弃回收他们，
        // 也侧面说明了虚拟机并不是通过引用计数算法来判断对象是否被引用的
        //java的内存回收使用的是可达性算法来分析判断一个对象是否能够回收的。
        // 这个算法就是通过一系列称为GC Roots 的根对象来作为起始点集，从这些节点开始，根据引用关系向下搜索，
        // 搜索走过的路径被称为 引用链 如果某个对象到Gc Roots之间没有任何引用链相连的话，则证明这个对象是不可能再被使用的
        //因此会被判断为可回收对象
        //在java技术体系里面，固定可作为GC Roots的对象包括下面几种：
        //在虚拟机栈（栈帧中的本地变量表）中引用的对象，譬如各个线程被调用的方法堆栈中使用到的参数，局部变量，临时变量等
        //在方法区中类静态属性引用的对象，譬如java雷的引用类型静态变量
        //在方法区中常量引用的对象，譬如字符串常量池（String Table）里的引用
        //在本地方法栈中JNI（即通常说的native方法）引用的对象
        //java虚拟机内部的引用，如基本数据类型对应的class对象，一些常驻的异常类型对象（比如NullPointException，OutOfMemoryError）等，还有系统类加载器
        //所有被同步鎖（synchronized关键字）持有的对象
        //反应java虚拟机内部情况的JMXBean，JVMTI中注册的回调，本地代码缓存等。
        //除了这些固定的GC Roots集合外，根据用户所选的垃圾收集器以及当前回收的内存区域不同，还可以有其他对象临时性的加入，共同构成完整的GC Roots集合
    }
}
