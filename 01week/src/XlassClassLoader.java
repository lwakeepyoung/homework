import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;

/**
 * 题目2（必做）
 */
public class XlassClassLoader extends ClassLoader{
    public static void main(String[] args){
        try {
            //构建对象
            Object helloObject = new XlassClassLoader().buildClass("Hello.xlass").newInstance();
            //反射调用方法
            for (Method declaredMethod : helloObject.getClass().getDeclaredMethods()) {
                declaredMethod.invoke(helloObject);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }  catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析文件并解密（x= 255 - x）
     * 构建class文件
     * @param fileName
     * @return
     * @throws IOException
     */
    public Class<?> buildClass(String fileName) throws IOException {
        InputStream inputStream = XlassClassLoader.class.getResourceAsStream(fileName);
        int len = inputStream.available
                ();
        byte[] bytes = new byte[len];
        inputStream.read(bytes,0,len);
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) (255-bytes[i]);
        }
        return new XlassClassLoader().defineClass("Hello",bytes,0,bytes.length);
    }
}
