package dashboard.controllers.responses;

import java.util.List;

import dashboard.controllers.responses.base.BaseResponse;
import dashboard.entities.ProductCategory;

public class ProductCategoryResponse extends BaseResponse{
	
	// List product category
	private List<ProductCategory> productCategoryList;

	/**
	 * @return the data
	 */
	public List<ProductCategory> getProductCategoryList() {
		return productCategoryList;
	}

	/**
	 * @param data the data to set
	 */
	public void setProductCategoryList(List<ProductCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
	
	
}
