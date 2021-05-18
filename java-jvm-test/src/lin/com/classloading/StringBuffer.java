package lin.com.classloading;

/**
 * 测试不同包下的SpringBuff会不会被加载
 * @author lin
 * @date 2021/5/12 22:08
 **/
public class StringBuffer {

    public StringBuffer(){
        System.out.println("new lin.com.classloader.StringBuffer!");
    }
}
