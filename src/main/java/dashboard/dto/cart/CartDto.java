package dashboard.dto.cart;

public class CartDto {

    private Long productId;

    private String productName;

    private int quantity;

    private int saleOff;

    private long price;

    private long totalPrice;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSaleOff() {
        return saleOff;
    }

    public void setSaleOff(int saleOff) {
        this.saleOff = saleOff;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getTotalPrice() {
        if(totalPrice == 0) {
            setTotalPrice();
        }
        return totalPrice;
    }

    public void setTotalPrice() {
        this.totalPrice = (this.price - (this.price * this.saleOff / 100)) * this.quantity;
    }
}
