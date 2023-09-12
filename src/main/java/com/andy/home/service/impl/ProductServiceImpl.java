package com.andy.home.service.impl;

import com.andy.home.dao.ProductDao;
import com.andy.home.po.Product;
import com.andy.home.po.ProductAvatar;
import com.andy.home.po.dto.ProductDto;
import com.andy.home.po.vo.ProductVo;
import com.andy.home.service.ProductService;
import com.andy.home.util.DateUtil;
import com.andy.home.util.SnowFlakeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Value("${protocol}")
    public String protocol;

    @Value("${domain}")
    public String domain;

    @Value("${ip}")
    public String ip;

    @Value("${defaultImageSrc}")
    public String defaultImageSrc;


    @PostConstruct
    public void getIpAddr() throws UnknownHostException {
        //如果ip是腾讯云地址，则换成域名
        ip = "111.231.14.181".equals(ip) ? domain :ip;
        defaultImageSrc = protocol +ip + defaultImageSrc;
    }

    @Override
    public void addProduct(Product product) {

        //通过雪花算法拿到ID
        long code = SnowFlakeUtil.genaraterId();
        product.setStatus(0);
        product.setCode(code);
        product.setIsProcess(0);
        try {
            product.setProductivityDate(DateUtil.parse(product.getProductivityDate()));
            product.setExpireDate(DateUtil.parse(product.getExpireDate()));
        } catch (ParseException e) {
            log.info("日期转换失败:"+e.getMessage());
        }
        this.productDao.addProduct(product);
    }

    @Override
    public PageInfo<List<ProductVo>> queryAllProduct(ProductDto productDto) {
        PageHelper.startPage(productDto.getPageNum(),productDto.getPageSize());
        PageHelper.orderBy("a.id desc");
        List<ProductVo> productVoList = this.productDao.queryAllProduct(productDto);
        return new PageInfo(productVoList);
    }

    @Override
    public void delProduct(Integer id) {
        this.productDao.delProduct(id,-1);
    }

    @Override
    public void processProduct(Integer id) {
        this.productDao.processProduct(id,-1);
    }

    @Override
    public Integer addFile(ProductAvatar productAvatar) {
        return this.productDao.addFile(productAvatar);
    }

    @Override
    public ProductVo queryProductDetailById(Integer id) {
        ProductVo vo = this.productDao.queryProductDetailById(id);
        if (Objects.nonNull(vo)) {
            String path = Objects.isNull(vo.getFilePath())? defaultImageSrc
                    : protocol +ip+ File.separator + vo.getFilePath();
            vo.setFilePath(path);
        }
        return vo;
    }

    @Override
    public void productUpdate(Product product) {
        this.productDao.productUpdate(product);
    }
}
