package cn.heckman.moduleservice.base;


import cn.heckman.modulecommon.utils.DateUtils;
import cn.heckman.moduleservice.exception.MatchMutiEntitiesException;
import cn.heckman.moduleservice.exception.PageSizeTooLargeException;
import cn.heckman.moduleservice.utils.HqlUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.*;
import java.math.BigInteger;
import java.util.*;

/**
 * Created by heckman on 2018/2/9.
 */
@SuppressWarnings({"unchecked", "rawtypes"})
@Transactional
public abstract class AbstractService<T> implements BaseService<T> {

    public static Logger logger = LoggerFactory.getLogger(AbstractService.class);

    public abstract BaseDaoInterface<T, Serializable> getDao();

    @PersistenceContext
    private EntityManager em;

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    @Cacheable(value = "entity", key = "'entity_'+#id", unless = "#result == null")
    public T findById(String id) {
        return (T)getDao().findById(id);
    }

    @Override
    public long count() {
        return getDao().count();
    }

    @Caching(
            evict = {
                    @CacheEvict(value = "entity", key = "'entity_' + #entity.id", beforeInvocation = true)
            }
    )
    public T save(T entity) {
        /*Long sortNo = entity.getSortNo();
        if(sortNo == null){
            entity.setSortNo(0L);
        }
        //更新最后修改时间
        entity.setLastTime(new Date());*/
        return getDao().save(entity);
    }

    /**
     * @param feild    要更改的字段
     * @param value    更改后的值
     * @param idFiled  根据什么字段更改
     * @param idValues in语句条件
     */
    public void updateByArray(String feild, Object value, String idFiled, String[] idValues) {
        Class<T> x = this.getRealClass();
        StringBuffer str = new StringBuffer("");
        String sValue = null;
        if (value instanceof Date) {
            sValue = DateUtils.formatDate((Date) value, "yyyy-MM-dd HH:mm:ss");
        } else {
            sValue = value.toString();
        }
        str.append("update " + x.getName() + " obj set obj." + feild + " = '" + sValue + "' where obj." + idFiled + " in (");
        for (int i = 0; i < idValues.length; i++) {
            if (i == idValues.length - 1) {
                str.append("'" + idValues[i] + "'");
            } else {
                str.append("'" + idValues[i] + "',");
            }
        }
        str.append(" )");
        em.createQuery(str.toString()).executeUpdate();
    }

    /**
     * 批量update 多个属性值方法
     *
     * @param feild    实体类属性数组
     * @param value    值数组
     * @param idFiled  根据什么字段更改
     * @param idValues in语句条件
     */
    public void updateValuesByArrays(String[] feild, Object[] value, String idFiled, String[] idValues) {
        Class<T> x = this.getRealClass();
        StringBuffer str = new StringBuffer("");
        String sValue = null;
        str.append("update " + x.getName() + " obj set ");
        if (feild.length > 0 && value.length > 0 && idValues.length > 0) {
            for (int i = 0; i < feild.length; i++) {

                if (value[i] instanceof Date) {
                    value[i] = DateUtils.formatDate((Date) value[i], "yyyy-MM-dd HH:mm:ss");
                } else {
                    value[i] = value[i].toString();
                }

                if (i == feild.length - 1) {
                    str.append("obj." + feild[i] + " = '" + value[i] + "'");
                } else {
                    str.append("obj." + feild[i] + " = '" + value[i] + "',");
                }
            }
            str.append("where obj." + idFiled + " in (");
            for (int i = 0; i < idValues.length; i++) {
                if (i == idValues.length - 1) {
                    str.append("'" + idValues[i] + "'");
                } else {
                    str.append("'" + idValues[i] + "',");
                }
            }
            str.append(" )");
            em.createQuery(str.toString()).executeUpdate();
        }
    }


    public void updateNativeHql(String hql) {
        em.createNativeQuery(hql).executeUpdate();
    }

    @SuppressWarnings({"unchecked"})
    public List<T> executNativeQuery(String sql, Class clazz) {
        if (logger.isDebugEnabled()) {
            logger.debug("native sql execute :{},{}", clazz.toGenericString(), sql);
        }
        //执行原生SQL
        Query nativeQuery = em.createNativeQuery(sql);
        //指定返回对象类型
        nativeQuery.unwrap(SQLQuery.class).setResultTransformer(Transformers.aliasToBean(clazz));
        //返回对象
        List<T> resultList = nativeQuery.getResultList();
        if (logger.isDebugEnabled()) {
            logger.debug("native sql result size: {}", resultList.size());
        }

        return resultList;
    }

    public BigInteger executNativeTotalQuery(String sql) {
        return (BigInteger) em.createNativeQuery(sql).getSingleResult();
    }

    public Object executNativeSumQuery(String sql) {
        return em.createNativeQuery(sql).getSingleResult();
    }


    @Override
    @CacheEvict(value = "entity", key = "'entity_' + #id", beforeInvocation = true)
    public void delete(Serializable id) throws IllegalAccessException, InvocationTargetException {
        this.logicDelete(id);
    }

    @CacheEvict(value = "entity", key = "'entity_' + #entity.id", beforeInvocation = true)
    @Override
    public void delete(T entity) throws IllegalAccessException, InvocationTargetException {

        this.logicDelete(entity);
    }

    @Override
    public Iterable<T> list() {
        return this.getDao().findAll();
    }

    @Override
    public Page<T> pageList(int pageNo, int pageSize) throws PageSizeTooLargeException {
        String hql = "from " + this.getRealClass().getName() + " obj";
        return pageList(hql, pageNo, pageSize);
    }

    /**
     * 根据id逻辑删除实体对象
     *
     * @param id
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @CacheEvict(value = "entity", key = "'entity_' + #id", beforeInvocation = true)
    public void logicDelete(Serializable id) throws IllegalAccessException, InvocationTargetException {
        T obj = (T)this.getDao().findById(id);
        this.logicDelete(obj);
    }

    /**
     * 逻辑删除实体对象,将对象属性delete 设置为true
     *
     * @param obj
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @CacheEvict(value = "entity", key = "'entity_' + #obj.id", beforeInvocation = true)
    public void logicDelete(T obj) throws IllegalAccessException, InvocationTargetException {
        if (obj != null) {
            //设置删除时间
            //obj.setDeleted(true);
            this.save(obj);
            this.getEm().flush();
//			this.getEm().detach(obj);
        }
    }


    /**
     * 根据条件执行逻辑删除
     *
     * @param property
     * @param value
     */
    public void logicDeleteByCondition(String property, Object value) {
        Class<T> x = this.getRealClass();
        String hql = "update " + x.getName() + " as obj set obj.deleted=true";
        hql = HqlUtil.addCondition(hql, property, value, HqlUtil.LOGIC_AND, HqlUtil.TYPE_OBJECT);
        Query query = this.getEm().createQuery(hql);
        query.setParameter(property.replace(".", ""), value);
        int count = query.executeUpdate();
        if (logger.isDebugEnabled()) {
            logger.debug("hql:" + hql);
            logger.debug("result:" + count);
        }
    }


    @Override
    @SuppressWarnings("unchecked")
    public Class<T> getRealClass() {
        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<T>) params[0];
    }

    @Override
    public List list(String hql, Object... params) {
        Query query = this.getEm().createQuery(hql);
        for (int i = 0; i < params.length; i++) {
            Object object = params[i];
            query.setParameter(i + 1, object);
        }
        return query.getResultList();
    }

    public List<T> getList(String hql) {
        List<T> list = new ArrayList<T>();
        Iterator<T> iterator = this.list(hql).iterator();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    @Override
    public Page pageList(String hql, int pageNo, int pageSize, Object... params) throws PageSizeTooLargeException {
        if (pageSize > 1000) {
            throw new PageSizeTooLargeException("分页数据太大");
        }
        return this.findByCustom(hql, false, pageNo, pageSize, params);
    }

    public Page pageNDList(String hql, int pageNo, int pageSize) throws PageSizeTooLargeException {
        if (pageSize > 1000) {
            throw new PageSizeTooLargeException("分页数据太大");
        }
        return this.findByCustom(hql, false, pageNo, pageSize);
    }

    @Override
    public T findUnique(String hql, Object... params)
            throws MatchMutiEntitiesException {
        List<T> results = findByCustomWithParams(hql, params);
        if (results.size() == 0)
            return null;
        if (results.size() > 1)
            throw new MatchMutiEntitiesException();
        else {
            return results.get(0);
        }
    }


    /**
     * 自定义查询方法，不带分页,默认排除掉deleted数据
     *
     * @param jpql
     * @param params
     * @return
     */
    public List findByCustomWithParams(String jpql, Object... params) {
        //return this.findByCustomWithParams(jpql, true, params);
        return this.findByCustomWithParams(jpql, false, params);
    }

    /**
     * from LoginLog ll where ll.personId='' order by ll.dt desc
     */
    public List findByCustomWithParams(String jpql, boolean excludeDeleted, Object... params) {
        if (excludeDeleted)
            jpql = HqlUtil.addCondition(jpql, "deleted", 0, HqlUtil.LOGIC_AND, HqlUtil.TYPE_NUMBER);
        Query query = this.em.createQuery(jpql);
        for (int i = 0; i < params.length; i++) {
            Object object = params[i];
            query.setParameter(i + 1, object);
        }
        List loglist = query.getResultList();
        return loglist;
    }

    /**
     * 自定义查询方法，带分页
     * 默认排除deleted
     */
    public Page findByCustom(String jpql, int pageNo, int pageSize) {
        return this.findByCustom(jpql, true, pageNo, pageSize);
    }

    /**
     * from LoginLog ll where ll.personId='' order by ll.dt desc
     */
    public Page findByCustom(String jpql, boolean excludeDeleted, int pageNo, int pageSize, Object... params) {

        long totalCount = countByCustom(jpql, excludeDeleted, params);
        pageNo--;
        List list = getPageList(jpql, excludeDeleted, pageNo, pageSize, params);
        Page page = new Page((pageNo) * pageSize + 1, totalCount, pageSize, list);
        return page;
    }

    /**
     * 获取分页里面的数据,排除删除的数据
     *
     * @return
     */
    public List getPageList(String jpql, int pageNo, int pageSize, Object... params) {
        return getPageList(jpql, true, pageNo, pageSize, params);
    }

    /**
     * 获取分页里面的数据 注：传进来的 pageNo 要先减去 1
     *
     * @return
     */
    public List getPageList(String jpql, boolean excludeDeleted, int pageNo, int pageSize, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug("findPageList:" + jpql);
        }
        if (excludeDeleted) {
            jpql = HqlUtil.addCondition(jpql, "deleted", 0, HqlUtil.LOGIC_AND, HqlUtil.TYPE_NUMBER);
        }
        Query query = this.em.createQuery(jpql);
        for (int i = 0; i < params.length; i++) {
            Object object = params[i];
            query.setParameter(i + 1, object);
        }
        query.setMaxResults(pageSize);
        query.setFirstResult(pageNo * pageSize);
        return query.getResultList();
    }

    public List getTopList(String jpql, boolean excludeDeleted, int top, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug("getTopList:" + jpql);
        }
        if (excludeDeleted) {
            jpql = HqlUtil.addCondition(jpql, "deleted", 0, HqlUtil.LOGIC_AND, HqlUtil.TYPE_NUMBER);
        }
        Query query = this.em.createQuery(jpql);
        for (int i = 0; i < params.length; i++) {
            Object object = params[i];
            query.setParameter(i + 1, object);
        }
        query.setMaxResults(top);
        query.setFirstResult(0);
        return query.getResultList();
    }
//
//    /**
//     * 根据条件查询总数
//     *
//     * @param jpql
//     * @param params 参数
//     * @return
//     */
//    public long countByCustom(String jpql, Object... params) {
//        return this.countByCustom(jpql, true, params);
//    }

    /**
     * 根据条件查询总数
     *
     * @param jpql
     * @param excludeDeleted 是否排除已删除的
     * @param params         参数
     * @return
     */
    public long countByCustom(String jpql, boolean excludeDeleted, Object... params) {
        if (logger.isDebugEnabled()) {
            logger.debug("countByCustom:" + jpql);
        }
        if (excludeDeleted) {
            jpql = HqlUtil.addCondition(jpql, "deleted", 0, HqlUtil.LOGIC_AND, HqlUtil.TYPE_NUMBER);
        }
        jpql = jpql.replaceAll("fetch", "");
        String countJpql = " select count(1) " + HqlUtil.removeOrders(HqlUtil.removeSelect(jpql));
        Query query = this.em.createQuery(countJpql);
        for (int i = 0; i < params.length; i++) {
            Object object = params[i];
            query.setParameter(i + 1, object);
        }
        Object obj = query.getSingleResult();
        long totalCount = (Long) obj;
        return totalCount;
    }

    public Iterable<T> save(Iterable<T> list) {
//        return getDao().save(list);
        return null;
    }

    /***
     * 更新实体类，只更新不为null的属性，用于代替save更新实体类，save是全部更新，并发下会导致更新被覆盖
     * PS:只能修改当前类的属性,不能修改父类(IdEntity)的属性
     * @param id
     * @param obj
     */
    @Override
    @CacheEvict(value = "entity", key = "'entity_' + #id", beforeInvocation = true)
    public void update(String id, T obj) {
        if (id == null || obj == null) {
            return;
        }
        boolean update = false;
        String className = obj.getClass().getSimpleName();
        StringBuilder hql = new StringBuilder("update " + className + " a set");
        Set<Map.Entry<String, Object>> fields = attrValues(obj).entrySet();
        List<String> colums = new ArrayList<String>();
        for (Map.Entry<String, Object> field : fields) {
            colums.add(" a." + field.getKey() + "=:" + field.getKey());
            update = true;
        }
        if (update) {
            hql.append(StringUtils.join(colums, ","));
            Query query = this.em.createQuery(hql.append(" where a.id=:id").toString());
            query.setParameter("id", id);
            for (Map.Entry<String, Object> field : fields) {
                query.setParameter(field.getKey(), field.getValue());
            }
            query.executeUpdate();
        }
    }

    private Map<String, Object> attrValues(T obj) {
        Map<String, Object> values = Maps.newHashMap();
        Set<String> fields = getFields(obj.getClass());
        for (String field : fields) {
            Object attrValue = getAttrValue(obj, field);
            if (attrValue != null) {
                values.put(field, attrValue);
            }
        }
        return values;
    }

    private Object getAttrValue(T obj, String attr) {
        try {
            Field privateField = obj.getClass().
                    getDeclaredField(attr);

            privateField.setAccessible(true);

            Object fieldValue = privateField.get(obj);
            return fieldValue;
        } catch (Exception e) {
            logger.info("反射获取属性值失败obj={},{}", obj, e);
            throw new IllegalArgumentException(e);
        }
    }

    private static Set<String> getFields(Class c) {
        Set<String> fields = new HashSet<String>();
        try {
            Method[] methods = c.getDeclaredMethods();
            Field[] attrs = c.getDeclaredFields();
            for (Method method : methods) {
                //判断方法上是否存在Column这个注解
                if (method.isAnnotationPresent(Column.class)) {
                    String field = method.getName();
                    field = field.replace("get", "").replace("set", "");
                    field = field.substring(0, 1).toLowerCase()
                            .concat(field.substring(1));
                    fields.add(field);
                }
            }
            for (Field field : attrs) {
                //判断方法上是否存在Column这个注解
                if (field.isAnnotationPresent(Column.class)) {
                    fields.add(field.getName());
                }
            }
        } catch (Throwable e) {
            logger.info("反射获取实体类属性失败class={},{}", c, e);
            throw new IllegalArgumentException(e);
        }
        return fields;
    }

    public Iterable<T> findAll(Collection<String> ids) {
        if (ids == null || ids.size() == 0) {
            return null;
        }
//        return this.getDao().findAll(new ArrayList<Serializable>(ids));
        return null;
    }
    /*public static void main(String[] args) {
        System.out.println(getFields(Tenant.class));
    }*/

    @Override
    public Object getByHql(String hql, Object... params) {
        Query query = this.getEm().createQuery(hql);
        Object obj = null;
        for (int i = 0; i < params.length; i++) {
            Object object = params[i];
            query.setParameter(i + 1, object);
        }
        try {
            obj = query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("have no found any entity with hql: {}", hql);
        }
        return obj;
    }


    @Override
    public List<Object> getListBySql(String sql) {
        Query query = this.getEm().createNativeQuery(sql);
        return query.getResultList();
    }

    @Override
    public int executeSql(String sql) {
        Query query = this.getEm().createNativeQuery(sql);
        int result = query.executeUpdate();
        return result;
    }

    @Override
    public int executeHql(String hql) {
        Query query = this.getEm().createQuery(hql);
        int result = query.executeUpdate();
        return result;
    }
}

