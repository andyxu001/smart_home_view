package com.andy.home.mapper;

import com.andy.home.po.Product;
import com.andy.home.po.ProductAvatar;
import com.andy.home.po.dto.ProductDto;
import com.andy.home.po.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductMapper {

    void addProduct(Product product);

    List<ProductVo> queryAllProduct(ProductDto productDto);

    void delProduct(@Param("id") Integer id, @Param("status") Integer status);

    Integer addFile(ProductAvatar productAvatar);

    ProductVo queryProductDetailById(Integer id);

    void processProduct(@Param("id") Integer id, @Param("status") Integer status);

    void productUpdate(Product product);
}
