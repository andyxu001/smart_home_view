package com.andy.home.mapper;

import com.andy.home.po.DataDictionary;
import com.andy.home.po.dto.DictionaryDto;

import java.util.List;

public interface DataDictionaryMapper {

    void addDictionary(DataDictionary dataDictionary);

    void updateDataDictionary(DataDictionary dataDictionary);

    List<DataDictionary> queryDictionary(DictionaryDto param);

    DataDictionary queryDictionaryById(Integer id);

    List<DataDictionary> queryAll(Integer userId);
}
