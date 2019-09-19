package dashboard.controllers.responses.base;

import java.util.List;

public class ListEntityResponse<T> {
	// Current page
	private int page;
	// Number of record display in one page
	private int pageSize;
	// Total page
	private int totalPage;
	// The response
	private Class<T> data;
	// The list to response
	private List<T> listData;

	public ListEntityResponse(Class<T> data) {
		this.data = data;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}
	
	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}
	
	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}
	
	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public List<T> getListData() {
		return listData;
	}

	public void setListData(List<T> listData) {
		this.listData = listData;
	}
}
