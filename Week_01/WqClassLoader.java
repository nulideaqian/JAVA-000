import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;

public class WqClassLoader extends ClassLoader {

    private final static String HELLO_FILE_PATH = "F:\\培训视频\\极客时间\\java进阶训练营\\2020_10_17\\讲师秦金卫-资料分享\\Hello\\Hello.xlass";

    public static void main(String[] args) {
        try {
            Object helloObj = new WqClassLoader().findClass("Hello").newInstance();
            Method helloMethod = helloObj.getClass().getDeclaredMethod("hello");
            helloMethod.invoke(helloObj);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        byte[] reverseBytes = new byte[10];
        try {
            byte[] bytes = Files.readAllBytes(Paths.get(HELLO_FILE_PATH));
            reverseBytes = new byte[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                reverseBytes[i] = (byte) ~bytes[i];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, reverseBytes, 0, reverseBytes.length);
    }

}
