package com.sysytem.manager.common;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

/**
 * 分页工具类
 * 
 * @author chenshun
 */
public class PageUtils implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 总记录数
	 */
	private long totalCount;
	/**
	 * 每页记录数
	 */
	private int pageSize;
	/**
	 * 总页数
	 */
	private int totalPage;
	/**
	 * 当前页数
	 */
	private int currPage;
	/**
	 * 列表数据
	 */
	private List<?> list;

	/**
	 * 分页
	 * 
	 * @param list
	 *            列表数据
	 * @param totalCount
	 *            总记录数
	 * @param pageSize
	 *            每页记录数
	 * @param currPage
	 *            当前页数
	 */
	public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
		this.list = list;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.currPage = currPage;
		this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
	}

	public PageUtils() {
	}

	/**
	 * 默认分页信息 默认按照 createDate desc 排序
	 * 
	 * @param pageSize
	 *            每页显示记录数
	 * @param currPage
	 *            当前页 从1开始
	 */
	public PageUtils(int pageSize, int currPage) {

		this.pageSize = pageSize;
		this.currPage = currPage;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

	public PageUtils(Page<?> page) {
		this.list = page.getContent();
		this.totalCount = page.getTotalElements();
		this.pageSize = page.getSize();
		this.currPage = page.getNumber() + 1;
		this.totalPage = page.getTotalPages();
	}

	public long getTotalCount() {
		return totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public int getCurrPage() {
		return currPage;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}
}
