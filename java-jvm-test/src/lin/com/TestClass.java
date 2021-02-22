package lin.com;

/**
 * 深入了解java虚拟机第六章：类文件结构
 * @author lin
 * @date 2020/12/12 17:11
 **/
public class TestClass {

    private int m;

    /*public int inc(){
        return m+1;
    }*/

    public int inc(){
        int x;
        try {
            x=1;
            return x;
        }catch (Exception e){
            x=2;
            return x;
        }finally {
            x=3;
        }
    }
}
