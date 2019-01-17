package cn.heckman.moduleservice.base;

import cn.heckman.moduleservice.exception.MatchMutiEntitiesException;
import cn.heckman.moduleservice.exception.PageSizeTooLargeException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Collection;
import java.util.List;

/**
 * Created by heckman on 2018/2/9.
 */
public interface BaseService<T> {

    public T findById(String id);

    public long count();

    public T save(T entity);


    public void delete(Serializable id) throws IllegalAccessException, InvocationTargetException;

    public void delete(T entity) throws IllegalAccessException, InvocationTargetException;

    public Iterable<T> list();

    public Page<T> pageList(int pageNo, int pageSize) throws PageSizeTooLargeException, PageSizeTooLargeException;

    @SuppressWarnings("unchecked")
    public Class<T> getRealClass();

    public Iterable<T> list(String hql, Object... objects);

    public Page pageList(String hql, int pageNo, int pageSize, Object... params) throws PageSizeTooLargeException;

    public T findUnique(String hql, Object... params) throws MatchMutiEntitiesException;

    public Iterable<T> save(Iterable<T> list);

    public void update(String id, T obj);

    public Iterable<T> findAll(Collection<String> ids);

    public void updateByArray(String feild, Object value, String idFiled, String[] idValues);

    public void updateValuesByArrays(String[] feild, Object[] value, String idFiled, String[] idValues);

    public void updateNativeHql(String hql);

    public Object getByHql(String hql, Object... params);

    public List<Object> getListBySql(String sql);

    public List<T> getList(String hql);

    public int executeSql(String sql);

    public List<T> executNativeQuery(String sql, Class clazz);

    public BigInteger executNativeTotalQuery(String sql);

    public Page pageNDList(String hql, int pageNo, int pageSize) throws PageSizeTooLargeException;

    public int executeHql(String hql);

}

