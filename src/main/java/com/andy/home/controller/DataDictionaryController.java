package com.andy.home.controller;

import com.alibaba.fastjson.JSONObject;
import com.andy.home.constant.KeyConstant;
import com.andy.home.enums.DataType;
import com.andy.home.po.DataDictionary;
import com.andy.home.po.ResponseEntity;
import com.andy.home.po.dto.DictionaryDto;
import com.andy.home.service.DataDictionaryService;
import com.andy.home.util.UserUntils;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Api(tags = "DataDictionaryController",description = "数据字典管理")
@Controller
@RequestMapping("dataDictionary")
@Slf4j
public class DataDictionaryController {

    @Autowired
    private DataDictionaryService dataDictionaryService;

    @RequestMapping("/dictionaryManage.do")
    public String dictionaryManage(String dicType, Model model, HttpServletRequest request){
        Integer userId = UserUntils.getUserId(request);
        DictionaryDto dto = new DictionaryDto();
        dto.setType(Objects.isNull(dicType) ? KeyConstant.PRODUCT_TYPE_KEY : dicType);
        dto.setUserId(userId);
        PageInfo<List<DataDictionary>> pageInfo = dataDictionaryService.queryDictionary(dto);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("dicType",dicType);
        return "dictionaryManage";
    }

    @RequestMapping("/dictionaryAddShow.do")
    public String dictionaryAddShow(Model model){
        List<DataType> types = Arrays.asList(DataType.values());
        model.addAttribute("types",types);
        return "dictionaryAdd";
    }

    @RequestMapping("/dictionaryAdd.do")
    @ResponseBody
    public String dictionaryAdd(String dicName,String dicType,Model model,HttpServletRequest request){
        Integer userId = UserUntils.getUserId(request);
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setUserId(userId);
        dataDictionary.setName(dicName);
        dataDictionary.setType(dicType);
        dataDictionary.setOrderBy(1);
        this.dataDictionaryService.addDictionary(dataDictionary);
        return JSONObject.toJSONString("success");
    }

    /**
     * 启用or作废
     * @param id
     * @param status
     * @param model
     * @return
     */
    @RequestMapping("/reUseOrCancel.do")
    @ResponseBody
    public String reUseOrCancel(String id,String status,Model model){
        DataDictionary dataDictionary = new DataDictionary();
        dataDictionary.setId(Integer.parseInt(id));
        dataDictionary.setStatus(Integer.parseInt(status));
        this.dataDictionaryService.updateDictionary(dataDictionary);
        return JSONObject.toJSONString("success");
    }

    /**
     * 一键同步数据到redis
     * @param model
     * @return
     */
    @RequestMapping("/synDataToRedisByMysql.do")
    @ResponseBody
    public String synDataToRedisByMysql(Model model,HttpServletRequest request){
        Integer userId = UserUntils.getUserId(request);
        this.dataDictionaryService.synDataToRedisByMysql(userId);
        return JSONObject.toJSONString("success");
    }


    //***********************************以下为api接口**********************

    @ApiOperation("添加数据字典")
    @RequestMapping(value = "/addDictionary", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> addDictionary(@RequestBody DataDictionary dataDictionary) {
        dataDictionaryService.addDictionary(dataDictionary);
        return ResponseEntity.ok("添加成功!").build();
    }

    @ApiOperation("查询数据字典")
    @RequestMapping(value = "/queryDictionary", method = RequestMethod.POST)
    public ResponseEntity<PageInfo<List<DataDictionary>>> queryDictionary(@RequestBody DictionaryDto param) {
        PageInfo<List<DataDictionary>> info = dataDictionaryService.queryDictionary(param);
        return ResponseEntity.ok("查询成功",info).build();
    }

}
