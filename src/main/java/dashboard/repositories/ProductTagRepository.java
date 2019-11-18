package dashboard.repositories;

import dashboard.entities.product.ProductTag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductTagRepository extends CrudRepository<ProductTag, Long> {
}
