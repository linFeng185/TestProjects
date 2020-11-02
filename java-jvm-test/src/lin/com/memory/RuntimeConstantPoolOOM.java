package lin.com.memory;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试常量池导致的内存溢出异常
 * vm配置：-XX:PermSize=6M -XX:MaxPerSize=6M
 * @author lin
 * @date 2020/11/2 22:04
 **/
public class RuntimeConstantPoolOOM {

    public static void main(String [] args){
        //使用set保持常量池引用，避免full gc回收常量池行为
        Set<String> set=new HashSet<>();
        //在short范围内足以让6mb的PermSize产生OOM
        //使用jdk7或更高版本的jdk来运行这段代码并不会得到相同的结果，无论是在jdk7中继续使用-XX:MaxPerSize
        // 或者在jdk8以上使用-XX:MaxMeta-apaceSize参数把方法区限制在6m，也都不会重现jdk6中的溢出异常，循环将进行下去，永不停歇
        //这是因为从jdk7开始，存放在永久代中的字符串常量池被移植到java堆中，所以在jdk7及以上版本，限制方法区的容量在对该测试用例来说是毫无意义的
        short i=0;
        while(true){
            set.add(String.valueOf(i++).intern());
        }
    }

    /**
     * 这段代码在jdk6中运行，会有两个false，而在jdk7及以上运行时，会得到一个true和一个false。
     * 产生差异的原因是，在jdk6中，intern()方法会把首次遇到的字符串实例复制到永久代的字符串常量池中存储，返回的也是在永久代里面这个字符串实例的引用
     * 而由StringBuild创建的字符串对象实例在java堆上，所以必不可能是同一个引用，结果将返回false
     *
     * 而7以上的话，intern方法就不需要在拷贝字符串的实例到永久代中了，既然字符串常量池已经移植到java堆中，那只需要记录一下首次出现的引用实例就好，
     * 因此intern返回的引用和由StringBuild创建的那个字符串实例就是同一个。
     */
    private static class RuntimeConstantPoolOOM2{
        public static void main(String [] args){
            //"计算机软件"这个词是首次出现的，所以记录的引用就是刚开始toString的那个字符串的引用，比较结果为true
            String str1=new StringBuilder("计算机").append("软件").toString();
            System.out.println(str1.intern()==str1);
            String str2=new StringBuilder("ja").append("va").toString();
            //这个返回false的话，是因为在堆中已经有了一个叫java的常量值引用，StringBuild创建的并不是第一个值为"java"的字符串，所以返回false
            //java首次出现是在加载sun.misc.Version这个类的时候进入常量池的
            System.out.println(str2.intern()==str2);
            String str3=new StringBuilder("hello").append("world").toString();
            String str4=new StringBuilder("hello").append("world").toString();
            System.out.println(str3.intern()==str4);
        }
    }

}
