package geektime.coding.zjl.camps.week1;

import java.util.Base64;

/**
 * @Program: zjl-project
 * @ClassName: HelloClassLoader
 * @Author: zhangjl
 * @Date: 2021-01-12 22:38
 * @Description:  自定义类加载器
 */
public class HelloClassLoader extends ClassLoader{

    public static void main(String[] args) {
        try {
            new HelloClassLoader().findClass("geektime.coding.zjl.camps.week1.HelloWorld").newInstance();
            System.out.println("自定义类加载器执行成功:HelloClassLoader");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        //return super.findClass(name);
        String helloBase64 = "yv66vgAAADQAHAoABgAOCQAPABAIABEKABIAEwcAFAcAFQEABjxpbml0PgEAAygpVgEABENvZGUBAA9MaW5lTnVtYmVyVGFib" +
                "GUBAAg8Y2xpbml0PgEAClNvdXJjZUZpbGUBAA9IZWxsb1dvcmxkLmphdmEMAAcACAcAFgwAFwAYAQAYSGVsbG8gQ2xhc3MgSW5pdGlhbGl6ZWQhBwA" +
                "ZDAAaABsBACpnZWVrdGltZS9jb2RpbmcvempsL2NhbXBzL3dlZWsxL0hlbGxvV29ybGQBABBqYXZhL2xhbmcvT2JqZWN0AQAQamF2YS9sYW5nL1N5c" +
                "3RlbQEAA291dAEAFUxqYXZhL2lvL1ByaW50U3RyZWFtOwEAE2phdmEvaW8vUHJpbnRTdHJlYW0BAAdwcmludGxuAQAVKExqYXZhL2xhbmcvU3RyaW5n" +
                "OylWACEABQAGAAAAAAACAAEABwAIAAEACQAAAB0AAQABAAAABSq3AAGxAAAAAQAKAAAABgABAAAACgAIAAsACAABAAkAAAAlAAIAAAAAAAmyAAISA7Y" +
                "ABLEAAAABAAoAAAAKAAIAAAANAAgADgABAAwAAAACAA0=";
        byte[] bytes = decode(helloBase64);
        return defineClass(name,bytes,0,bytes.length);
    }

    public byte[] decode(String base64) {
        return Base64.getDecoder().decode(base64);
    }
}
