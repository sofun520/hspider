package cn.heckman.moduleredis.service;

import java.util.List;
import java.util.Set;

public interface RedisService {

    boolean set(final String key, Object value);

    boolean set(final String key, Object value, Long expireTime);

    void remove(final String... keys);

    void removePattern(final String pattern);

    void remove(final String key);

    boolean exists(final String key);

    Object get(final String key);

    void hmSet(String key, Object hashKey, Object value);

    Object hmGet(String key, Object hashKey);

    void lPush(String k, Object v);

    List<Object> lRange(String k, long l, long l1);

    void add(String key, Object value);

    Set<Object> setMembers(String key);

    void zAdd(String key, Object value, double scoure);

    Set<Object> rangeByScore(String key, double scoure, double scoure1);

    long incr(String key, Long growthLength);
}
