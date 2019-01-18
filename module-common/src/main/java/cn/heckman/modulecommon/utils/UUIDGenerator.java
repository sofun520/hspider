package cn.heckman.modulecommon.utils;

import java.util.Random;
import java.util.UUID;

/**
 * Created by heckman on 2018/3/30.
 */
public class UUIDGenerator {

    private static int count=0;
    public static String javaId(){
        count++;
        String resultId=""+System.currentTimeMillis()+""+count;
        if(count>=90){
            count=0;
        }
        return resultId;
    }

    /**
     * 生成UUID 32位
     * @return
     */
    public static String uuid(){
        Random r = new Random();
        String id = new UUID(r.nextLong(), r.nextLong()).toString();
        id = id.replaceAll("-", "");
        return id;
    }

    public static String uuidLong() {

        String[] chars = new String[] {"1","2","3","4","5","6","7","8","9"};
        String uuid[] = new String[32];
        int radix = chars.length;
        for (int i = 0; i < 32; i++) {
            int a = (int) (Math.random()*radix);
            uuid[i] = chars[0 | a];
        }

        return StringUtil.join(uuid);

    }

    /*public static void main(String[] args) {
        System.out.println(uuid());
    }*/

}
