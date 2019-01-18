package cn.heckman.modulecommon.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

/**
 * Created by heckman on 2018/9/4.
 */
@SuppressWarnings("all")
public class JWTUtils {

    /**
     * APP登录Token的生成和解析
     *
     */

    /**
     * token秘钥，请勿泄露，请勿随便修改 backups:JKKLJOoasdlfj
     */
    public static final String SECRET = "JKKLJOoasdlfj";
    /**
     * token 过期时间: 10天
     */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 10;

    /**
     * 生成token
     *
     * @param params
     * @return
     */
    public static String createToken(Map<String, String> params) {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        JWTCreator.Builder build = JWT.create();

        build = build.withHeader(map).withClaim("iss", "Service") // payload
                .withClaim("aud", "APP");

        Set<String> sets = params.keySet();
        for (String str : sets) {
            build = build.withClaim(str, params.get(str));
        }

        String token = build.withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * JWT生成Token.<br/>
     * <p>
     * JWT构成: header, payload, signature
     *
     * @param userId 登录成功后用户user_id, 参数user_id不可传空
     */
    public static String createToken(String userId, String tenantCode) throws Exception {
        Date iatDate = new Date();
        // expire time
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        // build token
        // param backups {iss:Service, aud:APP}
        String token = JWT.create().withHeader(map) // header
                .withClaim("iss", "Service") // payload
                .withClaim("aud", "APP")

                .withClaim("userId", userId)
                .withClaim("tenantCode", tenantCode)
                .withIssuedAt(iatDate) // sign time
                .withExpiresAt(expiresDate) // expire time
                .sign(Algorithm.HMAC256(SECRET)); // signature

        return token;
    }

    /**
     * 解密Token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (Exception e) {
            // e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
        }
        return jwt.getClaims();
    }

    /**
     * 根据Token获取user_id
     *
     * @param token
     * @return user_id
     */
    public static String getTokenValue(String token, String key) {
        Map<String, Claim> claims = verifyToken(token);
        Claim user_id_claim = claims.get(key);
        return user_id_claim.asString();
    }

    /*public static void main(String args[]) {
        try {
            String token = createToken("10000l", "JZ");
            System.out.println(token);
            System.out.println(getTokenValue(token, "userId"));
            System.out.println(getTokenValue(token, "tenantCode"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }*/

}
