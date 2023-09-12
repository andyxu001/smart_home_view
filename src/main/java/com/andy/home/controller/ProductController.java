package com.andy.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.andy.home.constant.KeyConstant;
import com.andy.home.po.DataDictionary;
import com.andy.home.po.Product;
import com.andy.home.po.ProductAvatar;
import com.andy.home.po.ResponseEntity;
import com.andy.home.po.dto.ProductDto;
import com.andy.home.po.vo.ProductVo;
import com.andy.home.service.DataDictionaryService;
import com.andy.home.service.ProductService;
import com.andy.home.util.SnowFlakeUtil;
import com.andy.home.util.UserUntils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Api(tags = "ProductController",description = "物品管理")
@Controller
@RequestMapping("product")
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @Value("${FILE_PATH_DIR}")
    public String FILE_PATH_DIR;

    @Value("${windows.file.path}")
    private String windowsFilePath;

    @Value("${linux.file.path}")
    private String linuxFilePath;

    private static final String DOT = ".";
    private String path;
    private SimpleDateFormat sdf;

    @PostConstruct
    public void init(){
        String os = System.getProperty("os.name");
        sdf = new SimpleDateFormat("yyyy-MM-dd");
        //实在windows操作系统下
        if (os != null && os.indexOf("Windows") >= 0) {
            path = windowsFilePath;
        } else {
            path = linuxFilePath;
        }
    }
    /**
     * 物品管理查询
     * @param model
     * @return
     */
    @RequestMapping("/product.do")
    public String product(@ModelAttribute ProductDto productDto, Model model, HttpServletRequest request){
        Integer userId = UserUntils.getUserId(request);
        productDto.setUserId(userId);
        List<DataDictionary> list = dataDictionaryService.queryAll(userId);
        List<DataDictionary> productTypes = list.stream().filter(a -> a.getType().equals(KeyConstant.PRODUCT_TYPE_KEY)).collect(Collectors.toList());
        PageInfo<List<ProductVo>> listPageInfo = productService.queryAllProduct(productDto);
        model.addAttribute("listPageInfo",listPageInfo);
        model.addAttribute("productTypes",productTypes);
        model.addAttribute("productDto",productDto);
        return "product";
    }

    /**
     * 新增物品
     * @param product
     * @param model
     * @return
     */
    @RequestMapping("/productAdd.do")
    @ResponseBody
    public String productAdd(@RequestBody Product product, Model model,HttpServletRequest request){
        Integer userId = UserUntils.getUserId(request);
        product.setUserId(userId);
        productService.addProduct(product);
        return JSONObject.toJSONString("success");
    }

    /**
     * 处理物品
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/processProduct.do")
    @ResponseBody
    public String processProduct(String id, Model model){
        productService.processProduct(Integer.valueOf(id));
        return JSONObject.toJSONString("success");
    }

    /**
     * 获取物品的处理状态
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/getProductProcessStatus.do")
    @ResponseBody
    public Integer getProductProcessStatus(String id, Model model){
        ProductVo vo = productService.queryProductDetailById(Integer.valueOf(id));
        return Objects.isNull(vo.getIsProcess())? 0 : vo.getIsProcess();
    }

    /**
     * 删除物品
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/delProduct.do")
    @ResponseBody
    public String delProduct(String id, Model model){
        productService.delProduct(Integer.valueOf(id));
        return JSONObject.toJSONString("success");
    }

    @RequestMapping("/productAddShow.do")
    public String productAddShow(Model model,HttpServletRequest request){
        Integer userId =  UserUntils.getUserId(request);
        List<DataDictionary> list = dataDictionaryService.queryAll(userId);

        List<DataDictionary> productTypes = list.stream().filter(a -> {
            return a.getType().equals(KeyConstant.PRODUCT_TYPE_KEY);
        }).collect(Collectors.toList());

        List<DataDictionary> unitTypes = list.stream().filter(a -> {
            return a.getType().equals(KeyConstant.UNIT_TYPE_KEY);
        }).collect(Collectors.toList());

        model.addAttribute("productTypes",productTypes);
        model.addAttribute("unitTypes",unitTypes);
        return "productAdd";
    }

    /**
     * 查看物品详情
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/productDetail.do")
    public String productDetail(Integer id,Model model){
        ProductVo vo = productService.queryProductDetailById(id);
        model.addAttribute("productVo",vo);
        return "productDetail";
    }

    /**
     * 跳转到更新物品页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/productUpdateShow.do")
    public String productUpdateShow(Integer id,Model model,HttpServletRequest request){
        Integer userId = UserUntils.getUserId(request);
        List<DataDictionary> list = dataDictionaryService.queryAll(userId);
        ProductVo vo = productService.queryProductDetailById(id);

        List<DataDictionary> productTypes = list.stream().filter(a -> {
            return a.getType().equals(KeyConstant.PRODUCT_TYPE_KEY);
        }).collect(Collectors.toList());

        List<DataDictionary> unitTypes = list.stream().filter(a -> {
            return a.getType().equals(KeyConstant.UNIT_TYPE_KEY);
        }).collect(Collectors.toList());

        model.addAttribute("productTypes",productTypes);
        model.addAttribute("unitTypes",unitTypes);
        model.addAttribute("productVo",vo);
        return "productUpdate";
    }

    /**
     * 更新物品
     * @param product
     * @param model
     * @return
     */
    @RequestMapping("/productUpdate.do")
    @ResponseBody
    public String productUpdate(@RequestBody Product product, Model model){
        productService.productUpdate(product);
        return JSONObject.toJSONString("success");
    }

    /**
     * 上传物品头像
     * @param avatar
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/uploadFile.do")
    public String uploadFile(MultipartFile avatar) throws IOException {
        String date = sdf.format(new Date());
        String filename = avatar.getOriginalFilename();
        String newFileName = "";
        int index = filename.lastIndexOf(DOT);
        String fileType = filename.substring(index + 1);
        String uuid = UUID.randomUUID().toString();
        newFileName = uuid + DOT + fileType;

        File storePath = new File(date+File.separator+fileType);
        File targetFilePath = new File(path+File.separator+storePath);
        if (!targetFilePath.exists()) {
            targetFilePath.mkdirs();
        }
        File newFile = new File(targetFilePath,newFileName);
        avatar.transferTo(newFile);

        //插入到数据库
        Long code = SnowFlakeUtil.genaraterId();
        ProductAvatar productAvatar = new ProductAvatar();
        productAvatar.setCode(code);
        productAvatar.setOriginalName(filename);
        productAvatar.setFileType(fileType);
        productAvatar.setStatus(0);
        productAvatar.setFilePath(FILE_PATH_DIR+File.separator+storePath.getPath()+File.separator+newFileName);
        log.info(productAvatar.toString());
        this.productService.addFile(productAvatar);
        return JSONObject.toJSONString(code.toString());
    }

    //**********************************************以下为接口*************************
    @ApiOperation("添加数据字典")
    @RequestMapping(value = "/addDictionary", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> addDictionary(@RequestBody Product product) {
        productService.addProduct(product);
        return ResponseEntity.ok("添加成功!").build();
    }



}
