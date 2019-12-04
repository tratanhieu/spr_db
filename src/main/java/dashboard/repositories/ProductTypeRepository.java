package dashboard.repositories;

import dashboard.entities.product.ProductType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTypeRepository extends CrudRepository<ProductType, Long> {

    @Query("SELECT pt " +
            "FROM ProductType pt JOIN pt.productTypeGroup pc " +
            "WHERE pt.status != 'DELETED'")
    Page<ProductType> findWithPageable(Pageable pageable);

}
