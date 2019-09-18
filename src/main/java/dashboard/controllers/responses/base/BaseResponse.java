package dashboard.controllers.responses.base;

import java.util.List;

public class BaseResponse<T> {
	// Current page
	private int page;
	// Number of record display in one page
	private int pageSize;
	// Total page
	private int totalPage;
	// The message when response success
	private String message;
	// The response
	private Class<T> data;
	// The list to response
	private List<T> listData;

	public BaseResponse(Class<T> data) {
		this.data = data;
	}

	public BaseResponse() {
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
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public Class<T> getData() {
		return data;
	}

	public void setData(Class<T> data) {
		this.data = data;
	}

	public List<T> getListData() {
		return listData;
	}

	public void setListData(List<T> listData) {
		this.listData = listData;
	}
}
