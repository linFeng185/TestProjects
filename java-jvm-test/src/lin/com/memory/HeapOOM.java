package lin.com.memory;

import java.util.ArrayList;
import java.util.List;

/**
 *  VM配置： -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * @author lin
 * @date 2020/10/31 16:23
 **/
public class HeapOOM {
    static class OOMObject{

    }
    public static void main(String [] args){
        List<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
