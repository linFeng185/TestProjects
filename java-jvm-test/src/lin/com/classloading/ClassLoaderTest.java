package lin.com.classloading;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载器与instanceof关键字演示（类加载器对instanceof关键字运算的影响）
 * @author lin
 * @date 2021/5/10 22:29
 **/
public class ClassLoaderTest {

    public  static void main (String [] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ClassLoader myLoader = new ClassLoader(){
            /**
             * 根据全限定名来获取类文件，并返回Class对象
             * @param name
             * @return
             * @throws ClassNotFoundException
             */
          @Override
          public Class<?> loadClass(String name) throws ClassNotFoundException {
           try {
               String fileName = name.substring(name.lastIndexOf(".")+1)+".class";
               //获取类文件的字节流
               InputStream is = getClass().getResourceAsStream(fileName);
               if(is==null){
                   return super.loadClass(name);
               }
               //创建存储字节的数组
               byte [] b = new byte[is.available()];
               //将读取到的内容存入数组中
               is.read(b);
               //根据字节数组创建一个Class对象
               return defineClass(name,b,0,b.length);
           } catch (IOException e) {
               throw new ClassNotFoundException(name);
           }
          }
        };
        //根据类的全限定名来加载类，并实例化
//        Object obj = myLoader.loadClass("java.lang.StringBuffer").newInstance();
        Object obj = myLoader.loadClass("lin.com.classloading.StringBuffer").newInstance();
        System.out.println(obj.getClass());
        System.out.println(obj instanceof lin.com.classloading.ClassLoaderTest);
    }
}
