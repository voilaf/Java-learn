package com.voilaf;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ParseXlass extends ClassLoader {

    public static void main(String[] args) {
        try {
            Object obj = new Main().findClass("Hello").newInstance();
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
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            DataInputStream dis = new DataInputStream(new FileInputStream("E:\\work\\www\\Hello.xlass"));
            byte []b = new byte[dis.available()];
            dis.read(b);
            int len = b.length;
            for (int i = 0; i < len; i++) {
                b[i] = (byte) (255 - b[i]);
            }
            return defineClass(name, b, 0, b.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
