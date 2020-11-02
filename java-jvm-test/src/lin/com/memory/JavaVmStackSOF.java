package lin.com.memory;

/**
 * 测试java栈溢出
 * -Xss减少栈内存容量
 * VM参数配置： -Xss128k
 * @author lin
 * @date 2020/10/31 18:14
 **/
public class JavaVmStackSOF {
    private int stackLength = 1;

    public void stackLeak(){
        stackLength++;
        stackLeak();
    }
    public static void main(String [] args){
        JavaVmStackSOF oom=new JavaVmStackSOF();
        try{
            oom.stackLeak();
        }catch (Throwable e){
            System.out.println("stack length:"+oom.stackLength);
            throw e;
        }
    }
}
