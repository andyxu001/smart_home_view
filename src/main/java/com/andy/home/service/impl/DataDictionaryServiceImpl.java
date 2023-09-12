package com.andy.home.service.impl;


import com.andy.home.constant.KeyConstant;
import com.andy.home.dao.DataDictionaryDao;
import com.andy.home.enums.DataType;
import com.andy.home.po.DataDictionary;
import com.andy.home.po.dto.DictionaryDto;
import com.andy.home.service.DataDictionaryService;
import com.andy.home.util.SnowFlakeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class DataDictionaryServiceImpl implements DataDictionaryService {

    @Autowired
    private DataDictionaryDao dataDictionaryDao;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加数据字典前先验证，避免重复添加
     * @param dataDictionary
     */
    @Override
    public void addDictionary(DataDictionary dataDictionary) {
        //通过雪花算法拿到ID
        long code = SnowFlakeUtil.genaraterId();
        dataDictionary.setStatus(0);
        dataDictionary.setCode(code);

        String key = dataDictionary.getType()+"::"+dataDictionary.getUserId();
        String keyStr = key + KeyConstant.SUFFIX;
        List<String> result = redisTemplate.opsForList().range(keyStr,0,-1);
        if (Objects.nonNull(result) && result.size() > 0 && result.contains(dataDictionary.getName())) {
           return;
        }
        redisTemplate.opsForList().leftPush(keyStr,dataDictionary.getName());
        redisTemplate.opsForList().leftPush(key,dataDictionary);
        List<DataDictionary> results = redisTemplate.opsForList().range(key,0,-1);
        log.info(results.toString());
        this.dataDictionaryDao.addDictionary(dataDictionary);
    }

    @Override
    public void updateDictionary(DataDictionary dataDictionary) {
        this.dataDictionaryDao.updateDictionary(dataDictionary);
    }

    @Override
    public PageInfo<List<DataDictionary>> queryDictionary(DictionaryDto param) {
        PageHelper.startPage(param.getPageNum(),param.getPageSize());
        List<DataDictionary> dictionaryList = dataDictionaryDao.queryDictionary(param);
        return new PageInfo(dictionaryList);
    }

    @Override
    public List<DataDictionary> queryAll(Integer userId) {
        return this.dataDictionaryDao.queryAll(userId);
    }

    @Override
    public void synDataToRedisByMysql(Integer userId) {
        DictionaryDto param = new DictionaryDto();
        List<DataDictionary> dictionaryList = dataDictionaryDao.queryDictionary(param);
        DataType[] values = DataType.values();
        Arrays.asList(values).stream().forEach((v) ->{
            //首先清除原来的数据
            this.redisTemplate.delete(v.getValue()+"::"+userId);
            this.redisTemplate.delete(v.getValue()+"::"+userId + KeyConstant.SUFFIX);
        });

        log.info("用户id:("+userId+")开始同步数据");
        //开始同步数据
        dictionaryList.stream().forEach((dataDictionary)->{
            String key = dataDictionary.getType()+"::"+dataDictionary.getUserId();
            String keyStr = key + KeyConstant.SUFFIX;
            redisTemplate.opsForList().leftPush(keyStr,dataDictionary.getName());
            redisTemplate.opsForList().leftPush(key,dataDictionary);
        });
        log.info("用户id:("+userId+")同步数据完成");
    }


}
