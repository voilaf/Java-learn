import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseXlass extends ClassLoader {

    public static void main(String[] args) {
        try {
            Object obj = new ParseXlass().findClass("http://localhost/Hello.xlass").newInstance();
//            Object obj = new ParseXlass().findClass("/Users/www/jvm-gc/src/main/java/Hello.xlass").newInstance();
            Method method = obj.getClass().getMethod("hello");
            method.invoke(obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String file) throws ClassNotFoundException {
        Pattern pattern = Pattern.compile("(\\w+)\\.\\w+$");
        Matcher matcher = pattern.matcher(file);
        String className = null;
        if (matcher.find()) {
            className = matcher.group(1);
        }
        // 类名解析失败
        if (className == null || className.equals("")) {
            return null;
        }
        try {
            byte[] b = null;
            if (file.startsWith("http")) {
                // 读取网络资源
                b = getBytesByHttpRequest(file);
            } else {
                File localFile = new File(file);
                if (localFile.exists() && localFile.isFile()) {
                    // 绝对路径
                    DataInputStream dis = new DataInputStream(new FileInputStream(file));
                    b = new byte[dis.available()];
                    dis.read(b);
                }
            }
            if (b == null) {
                return null;
            }

            int len = b.length;
            for (int i = 0; i < len; i++) {
                b[i] = (byte) (255 - b[i]);
            }
            return defineClass(className, b, 0, b.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getBytesByHttpRequest(String url) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(url).get().build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response != null && response.isSuccessful()) {
            try {
                return response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
