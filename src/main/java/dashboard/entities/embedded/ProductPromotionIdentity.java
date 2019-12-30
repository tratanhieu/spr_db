package dashboard.entities.embedded;

import dashboard.entities.product.Product;
import dashboard.entities.promotion.ProductPromotion;
import dashboard.entities.promotion.Promotion;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProductPromotionIdentity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "promotion_Id")
    private Promotion promotion;

    @OneToMany
    @JoinColumn(name = "product_Id")
    private Product product;

    public ProductPromotionIdentity(){}

    public ProductPromotionIdentity(Promotion promotion, Product product){

        this.promotion = promotion;
        this.product = product;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPromotionIdentity that = (ProductPromotionIdentity) o;
        return promotion.equals(that.promotion) &&
                product.equals(that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(promotion, product);
    }
}
