package cn.heckman.moduleservice.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象. 包含当前页数据及分页信息如总记录数.
 *
 * @author ajax
 * @author tantyou
 */
public class Page<T> implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private static int DEFAULT_PAGE_SIZE = 20;

    private int pageSize = DEFAULT_PAGE_SIZE; // 每页的记录数

    private long startIndex; // 当前页第一条数据在List中的位置,从0开始

    private List<T> data; // 当前页中存放的记录,类型一般为List

    private long totalCount; // 总记录数

    private long totalPageCount;//总页数

    private long currentPageNo;

    private boolean hasNextPage;

    private boolean hasPreviousPage;


    /**
     * 构造方法，只构造空页.
     */
    @SuppressWarnings("rawtypes")
    public Page() {
        this(0, 0, DEFAULT_PAGE_SIZE, new ArrayList<T>());
    }

    /**
     * 默认构造方法.
     *
     * @param start     本页数据在数据库中的起始位置
     * @param totalSize 数据库中总记录条数
     * @param pageSize  本页容量
     * @param data      本页包含的数据
     */
    public Page(long start, long totalSize, int pageSize, List<T> data) {
        this.pageSize = pageSize;
        this.startIndex = start;
        this.totalCount = totalSize;
        this.data = data;

        if (totalCount % pageSize == 0) {
            this.totalPageCount = totalCount / pageSize;
        } else {
            this.totalPageCount = totalCount / pageSize + 1;
        }

        if (pageSize == 1 && start > 0) {
            this.currentPageNo = start / pageSize;
        } else {
            this.currentPageNo = start / pageSize + 1;
        }

        this.hasNextPage = this.getCurrentPageNo() <= this.getTotalPageCount() - 1;
        this.hasPreviousPage = this.getCurrentPageNo() > 1;


    }

    /**
     * 取总记录数.
     */
    public long getTotalCount() {
        return this.totalCount;
    }

    /**
     * 取总页数.
     */
    public long getTotalPageCount() {
        return this.totalPageCount;
    }

    /**
     * 取每页数据容量.
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 取得开始取数的索引位置
     *
     * @return
     */
    public long getStartIndex() {
        return this.startIndex;
    }

    /**
     * 取当前页中的记录.
     */
    public List<T> getResult() {
        return data;
    }

    /**
     * 设置当前页中的记录.
     */
    public void setResult(List data) {
        this.data = data;
    }

    /**
     * 取该页当前页码,页码从1开始.
     */
    public long getCurrentPageNo() {
        return currentPageNo;
    }

    /**
     * 该页是否有下一页.
     */
    public boolean hasNextPage() {
        return hasNextPage;
    }

    /**
     * 该页是否有上一页.
     */
    public boolean hasPreviousPage() {
        return hasPreviousPage;
    }

//	/**
//	 * 获取任一页第一条数据在数据集的位置，每页条数使用默认值.
//	 *
//	 * @see #getStartOfPage(int,int)
//	 */
//	protected static int getStartOfPage(int pageNo) {
//		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
//	}

//	/**
//	 * 获取任一页第一条数据在数据集的位置.
//	 *
//	 * @param pageNo   从1开始的页号
//	 * @param pageSize 每页记录条数
//	 * @return 该页第一条数据
//	 */
//	public static int getStartOfPage(int pageNo, int pageSize) {
//		return (pageNo - 1) * pageSize;
//	}

}