package com.pvpin.gyhhy.jsrunntime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
//import jdk.nashorn.api.scripting.ScriptObjectMirror
public final class Toolbox {
    public static class ToolClass{
        public static void print(Object o){
            System.out.print(o);
        }
        public static void print(String o){
            System.out.print(o);
        }
        public static void print(int o){
            System.out.print(o);
        }
        public static void print(long o){
            System.out.print(o);
        }
        public static void print(boolean o){
            System.out.print(o);
        }
        public static void print(double o){
            System.out.print(o);
        }
        public static void print(float o){
            System.out.print(o);
        }
        public static void print(char o){
            System.out.print(o);
        }
        public static void print(char[] o){
            System.out.print(o);
        }
        public static PrintStream printf(String format, Object... args){
            return System.out.printf(format, args);
        }
        public static PrintStream printf(Locale l, String format, Object... args){
            return System.out.printf(Locale.UK, format, args);
        }
        public static void println(){
            System.out.println();
        }
        public static void println(Object o){
            System.out.println(o);
        }
        public static void println(String o){
            System.out.println(o);
        }
        public static void println(int o){
            System.out.println(o);
        }
        public static void println(long o){
            System.out.println(o);
        }
        public static void println(boolean o){
            System.out.println(o);
        }
        public static void println(double o){
            System.out.println(o);
        }
        public static void println(float o){
            System.out.println(o);
        }
        public static void println(char o){
            System.out.print(o);
        }
        public static void println(char[] o){
            System.out.print(o);
        }
        public static ClassLoader getClassLoader(){
            return Toolbox.ToolClass.class.getClassLoader();
        }
        public static InputStream getResource(String filename){
            if (filename == null) {
                throw new IllegalArgumentException("Filename cannot be null");
            }
            try{
                URL url = getClassLoader().getResource(filename);
                if (url == null) {
                    return null;
                }
                URLConnection connection = url.openConnection();
                connection.setUseCaches(false);
                return connection.getInputStream();
            }
            catch (Exception e) {}
            return null;
        }
    }
    public static boolean isInherit(Class<?> classA,Class<?> classB){
        return classA.isAssignableFrom(classB) || classB.isAssignableFrom(classA);
    }
    private Toolbox(){}
    public static final class MethodTool{
        private MethodTool(){}
        public static List<Method> getAllMethods(Class<?> class_){
            List<Method> f = new ArrayList();
            Method[] methods;
            while (class_ != null){
                methods = class_.getDeclaredMethods();
                for (Method m : methods){
                    if(!isIn(f,m))f.add(m);
                }
                class_ = class_.getSuperclass();
            }
            return f;
        }
        public static List<Method> getAllMethods(Object obj){
            return getAllMethods(obj.getClass());
        }
        public static boolean isIn(List<Method> f,Method m){
            boolean b = false;
            for (Method ff : f){
                if (ff.getName().equals(m.getName())){
                    Class<?>[] listA = ff.getParameterTypes(),
                            listB = m.getParameterTypes();
                    if (listA.length == listB.length){
                        b = true;
                        for (int i = 0; i < listA.length;i++){
                            if (!(listA[i] == listB[i])){
                                b = false;
                                break;
                            }
                        }
                    }
                }
            }
            return b;
        }
        public static List<Method> getMethodsByName(Object obj,String name){
            return getMethodsByName(obj.getClass(),name);
        }
        public static List<Method> getMethodsByName(Class<?> class_,String name){
            List<Method> list = new ArrayList();
            List<Method> methods = getAllMethods(class_);
            for (Method m : methods){
                if (m.getName().equals(name)){
                    list.add(m);
                }
            }
            return list;
        };
        public static List<Method> getMethodsByParameter(Object obj,Class<?>... parameter){
            return getMethodsByParameter(obj.getClass(),parameter);
        }
        public static List<Method> getMethodsByParameter(Class<?> class_,Class<?>... parameter){
            List<Method> list = new ArrayList();
            List<Method> methods = getAllMethods(class_);
            Class<?>[] types;
            int i;
            for (Method m : methods){
                types = m.getParameterTypes();
                if (types.length == parameter.length){
                    for (i = 0; i < types.length; i++){
                        if (types[i] == parameter[i]){
                            list.add(m);
                        }
                    }
                }
            }
            return list;
        }
        public static Object callMethod(Method method,Object thiz,Object... args) 
                throws IllegalAccessException,
                IllegalArgumentException,
                InvocationTargetException{
            method.setAccessible(true);
            return method.invoke(thiz, args);
        }
    }
    public static final class FieldTool{
        private FieldTool(){}
        public static List<Field> getAllField(Object obj){
            return getAllField(obj.getClass());
        }
        public static List<Field> getAllField(Class<?> class_){
            List<Field> f = new ArrayList();
            Field[] methods;
            while (class_ != null){
                methods = class_.getDeclaredFields();
                f.addAll(Arrays.asList(methods));
                class_ = class_.getSuperclass();
            }
            return f;
        }
        public static Field getFieldByName(Object obj,String name){
            return getFieldByName(obj.getClass(),name);
        }
        public static Field getFieldByName(Class<?> class_,String name){
            Field f = null;
            List<Field> fs = getAllField(class_);
            for (Field fm : fs){
                if (fm.getName().equals(name)){
                    f = fm;
                    break;
                }
            }
            return f;
        }
        public static Object getFieldValue(Field f,Object obj) throws IllegalArgumentException, IllegalAccessException{
            f.setAccessible(true);
            return f.get(obj);
        }
    }
    public static final class JavaScript{
        public static final class Number{
            private final java.lang.Number num;
            private static final String isNaN = "isNaN";
            public Number(java.lang.Number num){
                this.num = num;
            }
            public java.lang.Number getNumber(){return num;}
            public boolean isInt(){
                return getNumber() instanceof java.lang.Integer;
            }
            public boolean isNaN(){
                jdk.nashorn.api.scripting.ScriptObjectMirror func = (jdk.nashorn.api.scripting.ScriptObjectMirror)JavaScript.getScript().getScriptEngine().get(isNaN);
                return (boolean)func.call(null, getNumber());
            }
            public boolean isDouble(){return getNumber() instanceof java.lang.Double;}
            public boolean isLong(){return getNumber() instanceof java.lang.Long;}
            public boolean isFloat(){
                return getNumber() instanceof java.lang.Float;
            }
            private static ScriptObjectMirror IF = null;
            private static ScriptObjectMirror IO = null;
            public boolean isInfinity(){
                boolean ret;
                if (IF == null){
                    JavaScript script = getScript();
                    NashornScriptEngine scriptEngine = script.getScriptEngine();
                    try {
                        IF = (ScriptObjectMirror)scriptEngine.eval("function(thiz){var num = thiz.getNumber();return num == Infinity}");
                    } catch (ScriptException ex) {}
                }
                ret = (boolean)IF.call(null, this);
                return ret;
            }
            public boolean isNegativeInfinity(){
                boolean ret;
                if (IO == null){
                    JavaScript script = getScript();
                    NashornScriptEngine scriptEngine = script.getScriptEngine();
                    try {
                        IO = (ScriptObjectMirror)scriptEngine.eval("function(thiz){var num = thiz.getNumber();return num == -Infinity}");
                    } catch (ScriptException ex) {}
                }
                ret = (boolean)IO.call(null, this);
                return ret;
            }
            private static ScriptObjectMirror NN = null;
            public boolean isNegative(){
                boolean ret;
                if (NN == null){
                    JavaScript script = getScript();
                    NashornScriptEngine scriptEngine = script.getScriptEngine();
                    try {
                        NN = (ScriptObjectMirror)scriptEngine.eval("function(thiz){var num = thiz.getNumber();return num < 0}");
                    } catch (ScriptException ex) {}
                }
                ret = (boolean)NN.call(null, this);
                return ret;
            }
        }
        private ScriptEngine engine;
        private ScriptEngineManager manager;
        private void load() throws Exception{
            this.engine.eval(
                "global = window = this;out = java.lang.System.out;"
            );
        }
        public Object eval(String st) throws ScriptException{
            return engine.eval(st);
        }
        public NashornScriptEngine getScriptEngine(){
            return (NashornScriptEngine) engine;
        }
        public ScriptEngineManager getScriptEngineManager(){
            return manager;
        }
        public Object eval(Reader re) throws ScriptException{
            return engine.eval(re);
        }
        public void reset(){
            manager = new ScriptEngineManager();
            engine = manager.getEngineByName("javascript");
            try {
                load();
            } catch (Exception ex) {
                ex.printStackTrace(System.err);
            }
        }
        private JavaScript(){
            reset();
        }
        private final static JavaScript e = new JavaScript();
        public static JavaScript getScript(){
            return e;
        }
        private static String boolean_ = "Boolean";
        private static String number_ = "Number";
        public Number toNumber(Object num){
            java.lang.Number n = (java.lang.Number)(((jdk.nashorn.api.scripting.ScriptObjectMirror)getScriptEngine().get(number_)).call(null,num));
            return new Number(n);
        }
        public boolean toBoolean(Object o){
            return (boolean)(((jdk.nashorn.api.scripting.ScriptObjectMirror)getScriptEngine().get(boolean_)).call(null,o));
        }
        public static Class getClass(Object object){
            return object==null?null:object.getClass();
        }
        private ScriptObjectMirror CT = null;
        public jdk.internal.dynalink.beans.StaticClass ClassToType(Class clazz){
            Object ret;
            if (CT == null){
                JavaScript script = getScript();
                NashornScriptEngine scriptEngine = script.getScriptEngine();
                try {
                    CT = (ScriptObjectMirror)scriptEngine.eval("function(clazz){var name = clazz.getName();return Java.type(name);}");
                } catch (ScriptException ex) {}
            }
            ret = CT.call(null, clazz);
            return (jdk.internal.dynalink.beans.StaticClass)ret;
        }
    }
    public static final InputStream in_ = System.in;
    public static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public static final Scanner scanner = new Scanner(System.in);
    public static String readConsoleInputLineByScanner() throws IOException{
        return scanner.nextLine();
    }
    public static String readConsoleInputLineByBufferedReader() throws IOException{
        return in.readLine();
    }
    public static String readConsoleInputLine() throws IOException{
        List<java.lang.Integer> l = new ArrayList();
        int i;
        while(true){
            i = in_.read();
            if (i == 10)break;
            else{
                l.add(i);
            }
        }
        char[] chars = new char[l.size()];
        i = 0;
        for (int ii : l){
            chars[i] = (char)ii;
            i++;
        }
        return String.copyValueOf(chars);
    }
}
