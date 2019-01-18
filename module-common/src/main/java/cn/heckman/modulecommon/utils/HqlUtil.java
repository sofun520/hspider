package cn.heckman.modulecommon.utils;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 针对hql提供的工具方法
 *
 * @author tantyou
 * @date 2008-1-10
 */
public class HqlUtil {
    public final static String LOGIC_AND="and";	//逻辑与
    public final static String LOGIC_OR="or";	//逻辑或
    public final static String LOGIC_NOT="not";	//逻辑非


    public final static String TYPE_NUMBER="Number";		//数值
    public final static String TYPE_IN="IN";
    public final static String TYPE_NOT_IN="NOT IN";		//
    public final static String TYPE_DATE="Date";			//日期类型
    public final static String TYPE_COLLECTION="Collection";//集合类型
    public final static String TYPE_STRING="String";		//字符串类型
    public final static String TYPE_STRING_LIKE="Like";		//模糊查询的字符串类型
    public static final String TYPE_OBJECT = "Object";		//预编译对象类型


    public final static String COMPARECHAR_LESS="<";			//小于
    public final static String COMPARECHAR_GREAT=">";			//大于
    public final static String COMPARECHAR_EQ="=";				//等于
    public final static String COMPARECHAR_LESS_EQ="<=";		//小于等于
    public final static String COMPARECHAR_GREAT_EQ=">=";		//大于等于
    public final static String COMPARECHAR_NOT_EQ="<>";			//不等于



    /**
     * 在原有的hql的基础之上，添加新的条件
     * @param hql
     * @param conditionMap
     * @return
     */
    public static String addCondition(String hql,Map<String,Object> conditionMap){

        if(conditionMap!=null&&conditionMap.size()>0) {
            hql = hql + " where 1=1";
            for (Map.Entry<String, Object> entry : conditionMap.entrySet()) {
				/*System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());*/
                hql = hql + " and obj." + entry.getKey() + "='" + entry.getValue()+"'";
            }
        }

        return hql;
    }

    /**
     * hql条件:between and功能
     * @param hql
     * @param filedName
     * @param startTimeValue
     * @param endTimeValue
     * @return
     */
    public static String addBetweenCondition(String hql, String filedName, Object startTimeValue, Object endTimeValue) {
        hql = addCondition(hql, filedName, startTimeValue, HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING, HqlUtil.COMPARECHAR_GREAT_EQ);
        return addCondition(hql, filedName, endTimeValue, HqlUtil.LOGIC_AND, HqlUtil.TYPE_STRING, HqlUtil.COMPARECHAR_LESS);
    }



    /**
     * 添加一个逻辑条件
     * @param hql	原hql
     * @param fieldName	条件名称
     * @param value		条件值
     * @param type		字段类型
     * @param sCompareChar	比较方式
     * @param logic		逻辑方式
     * @return	装配好的hql
     */
    public static String addCondition(String hql,String fieldName,Object value,String logic,String type,String sCompareChar){
        StringBuffer sb=new StringBuffer();
        Pattern pWhere = Pattern.compile("where\\s*", Pattern.CASE_INSENSITIVE);
        Pattern pOrder = Pattern.compile("order\\s*by\\s*", Pattern.CASE_INSENSITIVE);
        Matcher mWhere = pWhere.matcher(hql);
        Matcher mOrder = pOrder.matcher(hql);

        boolean bOrder=mOrder.find();
        boolean bWhere=mWhere.find();
        String basePart=null;
        String orgWhereParts=null;
        String orderPart=bOrder?hql.substring(mOrder.end()):"";
        if(bWhere){
            basePart=hql.substring(0,mWhere.start()-1);
            orgWhereParts=bOrder?hql.substring(mWhere.end(),mOrder.start()-1):hql.substring(mWhere.end());
        }else{
            orgWhereParts="";
            basePart=bOrder?hql.substring(0,mOrder.start()-1):hql;
        }

        sb.append(basePart);
        sb.append(" where ");
        //在这里添加新的条件以及逻辑
        String newWhere=toStringInHsql(fieldName, value, type, sCompareChar);
        sb.append(newWhere);

        if(bWhere){
            sb.append(" "+logic);
            sb.append(" ("+orgWhereParts+")");
        }
        if(bOrder){
            sb.append(" order by "+orderPart);
        }
        return sb.toString();
    }

    private static String toStringInHsql(String fieldName,Object value,String type,String compareChar){
        String sResult="";

        if(value!=null){
            if(type.equals(TYPE_NUMBER)){
                //1.日期型或者日期型
                sResult="obj."+fieldName+compareChar+value;
            }
            if(type.equals(TYPE_DATE) || type.equals(TYPE_STRING)){
                sResult="obj."+fieldName+compareChar+"'"+value+"'";
            }
            if(type.equals(TYPE_STRING_LIKE)){
                //1.模糊查询
                sResult="obj."+fieldName+" like '%"+value+"%'";
            }
            if(type.equals(TYPE_IN)){
                sResult="obj."+fieldName+" IN ("+value+")";
            }
            if(type.equals(TYPE_NOT_IN)){
                sResult="obj."+fieldName+" NOT IN ("+value+")";
            }

            if(type.equals(TYPE_OBJECT)){
                sResult="obj."+fieldName+""+compareChar+":"+fieldName.replace(".", "");
            }
        }else{
            sResult="obj."+fieldName+" is null";
        }
        return sResult;
    }
    /**
     * 默认比较方式为相等模式的添加条件
     * @param hql	原hql
     * @param fieldName	条件名称
     * @param value		条件值
     * @param type		字段类型
     * @param logic		逻辑方式
     * @return
     */
    public static String addCondition(String hql,String fieldName,Object value,String logic,String type){
        return addCondition(hql,fieldName,value,logic,type,HqlUtil.COMPARECHAR_EQ);
    }
    /**
     * 默认类型为字符串，比较方式为相等方式
     * @param hql	原hql
     * @param fieldName	条件名称
     * @param value		条件值
     * @param logic		逻辑方式
     * @return
     */
    public static String addCondition(String hql,String fieldName,Object value,String logic){
        return addCondition(hql,fieldName,value,logic,HqlUtil.TYPE_STRING);
    }

    /**
     * 默认逻辑方式为 and 类型为字符串，比较方式为相等
     * @param hql	原hql
     * @param fieldName	条件名称
     * @param value		条件值
     * @return
     */
    public static String addCondition(String hql,String fieldName,Object value){
        return addCondition(hql,fieldName,value,HqlUtil.LOGIC_AND);
    }

    /**
     * 添加排序方式
     * @param hql
     * @param orderField
     * @param dir
     * @return
     */
    public static String addOrder(String hql,String orderField,String dir){
        StringBuffer sb=new StringBuffer(hql);
        Pattern p = Pattern.compile("order\\s*by", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);

        if(m.find()){
            sb.append(",");
        }else{
            sb.append(" order by ");
        }
        sb.append(orderField+" "+dir);
        return sb.toString();
    }

    /**
     * 去除hql的orderby 子句，用于pagedQuery.
     *
     */
    public static String removeOrders(String hql) {
        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    /**
     * 去除hql的select 子句，未考虑union的情况,用于pagedQuery.
     *
     */
    public static String removeSelect(String hql) {
        int beginPos = hql.toLowerCase().indexOf("from");
        return hql.substring(beginPos);
    }

//	public static void main(String[] args) {
//	String h0="from P2G as p2g where a='a' order by aa desc";
//	String h2="from P2G as p2g where a='a'";
//	String h1="from P2G as p2g order by aa desc";
//
//	String h3="from P2G as p2g";
////	hql=addOrder(hql,"aaaa","asc");hql=addOrder(hql,"aaaa","asc");
//	String h=addCondition(h1, "bbbbb", "aaaa");
//	String sss=addCondition(h, "ccc","bbbb");
//	String bbbb=addCondition(sss, "userName", "tanchang", LOGIC_OR, TYPE_STRING_LIKE,null);
//	String ccc=addCondition(bbbb, "birthday", "2007-1-1", LOGIC_AND, TYPE_DATE, COMPARECHAR_GREAT);
//	System.out.println(ccc);
//
//}

    /**
     * 添加条件
     */
    public static void addCondition(StringBuffer hql,String fieldName,Object value,String logic,String type,String sCompareChar){
        String condition=toStringInHsql(fieldName, value, type, sCompareChar);
        Pattern pWhere = Pattern.compile("where\\s*", Pattern.CASE_INSENSITIVE);
        Pattern pOrder = Pattern.compile("order\\s*by\\s*", Pattern.CASE_INSENSITIVE);
        Matcher mWhere = pWhere.matcher(hql);
        Matcher mOrder = pOrder.matcher(hql);
        boolean bOrder=mOrder.find();
        boolean bWhere=mWhere.find();

        if(bWhere){
            hql.insert(bOrder?mOrder.start()-1:hql.length(), ")");
            hql.insert(mWhere.end(),""+condition+" "+logic+" (");
        }else{
            if(bOrder){
                hql.insert(mOrder.start()-1, " where "+condition);
            }else{
                hql.append(" where "+condition);
            }
        }
    }
    /**
     * 添加条件
     * @param hql
     * @param fieldName
     * @param value
     * @param logic
     * @param type
     */
    public static void addCondition(StringBuffer hql,String fieldName,Object value,String logic,String type){
        addCondition(hql, fieldName,value,logic,type,COMPARECHAR_EQ);
    }

    /**
     * 添加条件
     * @param hql
     * @param fieldName
     * @param value
     * @param logic
     */
    public static void addCondition(StringBuffer hql,String fieldName,Object value,String logic){
        addCondition(hql, fieldName,value,logic,TYPE_STRING,COMPARECHAR_EQ);
    }
    /**
     * 添加条件
     * @param hql
     * @param fieldName
     * @param value
     */
    public static void addCondition(StringBuffer hql,String fieldName,Object value){
        addCondition(hql, fieldName,value,LOGIC_AND,TYPE_STRING,COMPARECHAR_EQ);
    }
    /**
     * 添加条件 完整的where条件
     * @param hql
     */
    public static void addWholeCondition(StringBuffer hql,String whereHql,String logic){
        String condition=whereHql;
        Pattern pWhere = Pattern.compile("where\\s*", Pattern.CASE_INSENSITIVE);
        Pattern pOrder = Pattern.compile("order\\s*by\\s*", Pattern.CASE_INSENSITIVE);
        Matcher mWhere = pWhere.matcher(hql);
        Matcher mOrder = pOrder.matcher(hql);
        boolean bOrder=mOrder.find();
        boolean bWhere=mWhere.find();

        if(bWhere){
            hql.insert(bOrder?mOrder.start()-1:hql.length(), ")");
            hql.insert(mWhere.end(),""+condition+" "+logic+" (");
        }else{
            if(bOrder){
                hql.insert(mOrder.start()-1, " where "+condition);
            }else{
                hql.append(" where "+condition);
            }
        }
    }
    /**
     * 添加条件 完整的where条件
     * @param hql
     */
    public static void addWholeCondition(StringBuffer hql,String whereHql){
        addWholeCondition(hql,whereHql,LOGIC_AND);
    }
    /**
     * 添加排序
     * @param hql
     * @param orderField
     * @param dir
     */
    public static void addOrder(StringBuffer hql,String orderField,String dir,boolean isAppend){
        Pattern p = Pattern.compile("order\\s*by", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        boolean bOrder=m.find();
        if(isAppend){
            if(bOrder){
                hql.append(","+orderField+" "+dir);
            }else{
                hql.append(" order by "+orderField+" "+dir);
            }
        }else{
            if(bOrder){
                hql.insert(m.end(), " "+orderField+" "+dir+",");
            }else{
                hql.append(" order by "+orderField+" "+dir);
            }
        }
    }
    /**
     * 添加排序
     * @param hql
     * @param orderField
     * @param dir
     */
    public static void addOrder(StringBuffer hql,String orderField,String dir){
        addOrder(hql, orderField, dir,true);
    }
//	public static void main(String[] args) {
//		StringBuffer sb=new StringBuffer();
//		sb.append("from ");
//		sb.append("P2G");
//		sb.append(" as obj");
//		sb.append(" where ");
//		sb.append("obj.1='x' and boj.x='y'");
//		sb.append(" order by ");
//		sb.append("obj.x desc");
//		addCondition(sb, "name","tanchang18");
//
//		System.out.println(sb);
//	}
}
