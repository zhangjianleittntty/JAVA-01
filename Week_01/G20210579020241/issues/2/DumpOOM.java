package geektime.coding.zjl.camps.week1;

import java.util.Vector;

/**
 * @Program: zjl-project
 * @ClassName: DumpOOM
 * @Author: zhangjl
 * @Date: 2021-01-14 20:03
 * @Description:
 */
public class DumpOOM {

    public static void main(String[] args) {
        Vector v = new Vector();
        for (int i = 0;i < 25;i++) {
            v.add(new byte[1*1024*1024]);
        }
    }

}
