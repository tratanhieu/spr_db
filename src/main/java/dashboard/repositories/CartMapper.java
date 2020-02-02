package dashboard.repositories;

import dashboard.dto.cart.CartDto;
import dashboard.dto.cart.CartForm;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {

    @Select(
        "SELECT " +
            "product_id AS productId, " +
            "product_name AS productName, " +
            "quantity, " +
            "sale_off AS saleOff, " +
            "price, " +
            "total_price AS totalPrice " +
        "FROM cart " +
        "WHERE user_id = #{userId}"
    )
    List<CartDto> getCart(@Param("userId") Long userId);

    @Insert(
        "INSERT INTO " +
            "cart " +
        "VALUES(" +
            "#{userId}, " +
            "#{productId}, " +
            "#{productName}, " +
            "#{quantity}, " +
            "#{saleOff}, " +
            "#{price}, " +
            "#{totalPrice}" +
        ")"
    )
    void addToCart(CartForm cartForm);

    @Update(
        "UPDATE " +
            "cart " +
        "SET " +
            "quantity = #{quantity}, " +
            "total_price = #{totalPrice} " +
        "WHERE " +
            "user_id = #{userId} " +
            "and " +
            "product_id = #{productId}"
    )
    void updateCart(CartForm cartForm);

    @Delete(
        "DELETE FROM cart WHERE user_id = #{userId} and product_id = #{productId}"
    )
    void deleteFromCart(Long userId, Long productId);

}
