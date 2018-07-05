public class Singleton {  
    private static Singleton instance = new Singleton();  
    private Singleton (){}  
    public static Singleton getInstance() {  
    return instance;  
    }  
}


// 双检锁
public class Singleton{
    private volatile static Singleton Singleton;
    private Singleton () {}
    public static Singleton getSingleton(){
        if(singleton == null){
            synchronized (Singleton.class){
                if (singleton == null){
                    singleton = new Singleton();
                }
            }
        }
        return singleton;
    }
}


// 登记式/静态内部类
public class Singleton {
    private static class SingletonHolder{
        private static final Singleton INSTANCE = new Singleton();
    }
    private Singleton () {}
    public static final Singleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}



// 枚举
public enum Singleton {
    INSTANCE;
    public void whateverMethod() {}
}







