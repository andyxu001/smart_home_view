package com.andy.home.dao;

import com.andy.home.mapper.ProductMapper;
import com.andy.home.po.Product;
import com.andy.home.po.ProductAvatar;
import com.andy.home.po.dto.ProductDto;
import com.andy.home.po.vo.ProductVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDao {

    @Autowired
    private ProductMapper mapper;

    public void addProduct(Product product){
        this.mapper.addProduct(product);
    }

    public List<ProductVo> queryAllProduct(ProductDto productDto) {
        return this.mapper.queryAllProduct(productDto);
    }

    public void delProduct(Integer id, Integer status) {
        this.mapper.delProduct(id,status);
    }

    public Integer addFile(ProductAvatar productAvatar) {
        this.mapper.addFile(productAvatar);
        return productAvatar.getId();
    }

    public ProductVo queryProductDetailById(Integer id) {
        return this.mapper.queryProductDetailById(id);
    }

    public void processProduct(Integer id, int status) {
        this.mapper.processProduct(id,status);
    }

    public void productUpdate(Product product) {
        this.mapper.productUpdate(product);
    }
}
