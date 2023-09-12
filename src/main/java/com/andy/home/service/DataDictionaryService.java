package com.andy.home.service;

import com.andy.home.po.DataDictionary;
import com.andy.home.po.dto.DictionaryDto;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DataDictionaryService {

    void addDictionary(DataDictionary dataDictionary);

    void updateDictionary(DataDictionary dataDictionary);

    PageInfo<List<DataDictionary>> queryDictionary(DictionaryDto param);

    List<DataDictionary> queryAll(Integer userId);

    void synDataToRedisByMysql(Integer userId);
}
