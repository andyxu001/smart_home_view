package com.andy.home.service;

import com.andy.home.po.Product;
import com.andy.home.po.ProductAvatar;
import com.andy.home.po.dto.ProductDto;
import com.andy.home.po.vo.ProductVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductService {

    void addProduct(Product product);

    PageInfo<List<ProductVo>> queryAllProduct(ProductDto productDto);

    void delProduct(Integer id);

    void processProduct(Integer id);

    Integer addFile(ProductAvatar productAvatar);

    ProductVo queryProductDetailById(Integer id);

    void productUpdate(Product product);
}
