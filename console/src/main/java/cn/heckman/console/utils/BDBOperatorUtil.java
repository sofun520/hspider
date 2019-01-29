package cn.heckman.console.utils;

import com.sleepycat.je.*;

import java.io.File;

/*
 封装的bdb操作工具栏 集成了增、删、改、查、关闭、同步操作等方法
 */
public class BDBOperatorUtil {
    private String dbEnvFilePath;
    private String databaseName;
    // 环境变量的声明
    private Environment myDbEnvironment = null;
    // 数据库操作的对象声明
    private Database weiboDatabase = null;

    /**
     * bdb操作环境变量和数据库初始化
     *
     * @param dbEnvFilePath
     * @param databaseName
     */
    public BDBOperatorUtil(String dbEnvFilePath, String databaseName) {
        this.dbEnvFilePath = dbEnvFilePath;
        this.databaseName = databaseName;

        /**
         * 初始化数据库参数
         */
        try {
            // 初始化数据存储根目录文件夹
            File f = new File(dbEnvFilePath);
            if (!f.exists()) {
                f.mkdirs();
            }
            // 数据库配置变量初始化
            DatabaseConfig dbConfig = new DatabaseConfig();// 打开数据库
            dbConfig.setAllowCreate(true);
            // 初始化环境配置变量，基于该变量去配置环境变量
            EnvironmentConfig envConfig = new EnvironmentConfig();
            // 当使用的数据库配置变量不存在的时候，就自动创建
            envConfig.setAllowCreate(true);
            // 正式初始化数据库的环境
            myDbEnvironment = new Environment(f, envConfig);
            // 打开一个数据库，如果不存在，则自动创建；第一个参数表示是否是事务
            weiboDatabase = myDbEnvironment.openDatabase(null, databaseName,
                    dbConfig);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将指定的kv存放到bdb当中，并可以选择是否实时同步到磁盘中
     *
     * @param key
     * @param value
     * @param isSync
     * @return
     */
    public boolean put(String key, String value, boolean isSync) {
        // 数据的key
        // 数据的value
        try {
            // 将key和value都封装到DatabaseEntry中
            DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
            DatabaseEntry theData = new DatabaseEntry(value.getBytes("UTF-8"));
            // 写入数据库
            weiboDatabase.put(null, theKey, theData);
            if (isSync) {
                // 数据同步到磁盘
                this.sync();
            }
            // 对该库进行count操作，查看有多少条数据
            System.out.println(weiboDatabase.count());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // 删除bdb中指定的key值
    public boolean delete(String key) {
        DatabaseEntry theKey;
        try {
            theKey = new DatabaseEntry(key.getBytes("UTF-8"));
            weiboDatabase.delete(null, theKey);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    /**
     * 读取bdb的key对应的数据
     *
     * @param key
     * @return
     */
    public String getValue(String key) {
        // 要读取数据的key
        try {
            // 将读取数据的key封装到DatabaseEntry中
            DatabaseEntry theKey = new DatabaseEntry(key.getBytes("UTF-8"));
            // 将读取出来的值以二进制形式放到DatabaseEntry中
            DatabaseEntry theData = new DatabaseEntry();
            // 执行读取操作
            weiboDatabase.get(null, theKey, theData, LockMode.DEFAULT);
            if (theData.getData() == null) {
                return null;
            }
            // 将二进制数据转化成字符串值
            String result = new String(theData.getData(), "utf-8");
            // 打印之
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 同步数据到磁盘当中，相当于让数据实时持久化
     *
     * @return
     */
    public boolean sync() {
        if (myDbEnvironment != null) {
            try {
                myDbEnvironment.sync();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    /**
     * 关闭环境变量数据库
     *
     * @return
     */
    public boolean close() {
        try {
            if (weiboDatabase != null) {
                weiboDatabase.close();
            }
            if (myDbEnvironment != null) {
                myDbEnvironment.sync();
                myDbEnvironment.cleanLog();
                myDbEnvironment.close();
            }
            return true;
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        String dbEnvFilePath = "bdb2";
        String databaseName = "weibo2";
        String key ="self_key_1";
        String value="工具类操作实例";
        BDBOperatorUtil bdUtil=new BDBOperatorUtil(dbEnvFilePath, databaseName);
//		bdUtil.put(key, value, false);
//		bdUtil.sync();
        System.out.println(bdUtil.getValue(key));

    }
}
