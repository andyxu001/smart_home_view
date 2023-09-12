package com.andy.home.dao;

import com.andy.home.mapper.DataDictionaryMapper;
import com.andy.home.po.DataDictionary;
import com.andy.home.po.dto.DictionaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class DataDictionaryDao {

    @Autowired
    private DataDictionaryMapper dataDictionaryMapper;

    public void addDictionary(DataDictionary dataDictionary){
        this.dataDictionaryMapper.addDictionary(dataDictionary);
    }

    public void updateDictionary(DataDictionary dataDictionary){
        this.dataDictionaryMapper.updateDataDictionary(dataDictionary);
    }

    public List<DataDictionary> queryDictionary (DictionaryDto param) {
        return dataDictionaryMapper.queryDictionary(param);
    }

    public List<DataDictionary> queryAll(Integer userId) {
        return dataDictionaryMapper.queryAll(userId);
    }
}
