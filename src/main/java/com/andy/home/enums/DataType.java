package com.andy.home.enums;

public enum DataType {

    PRODUCT_TYPE("product_type","物品类型"),
    UNIT_TYPE("unit_type","单位");

    private String value;
    private String description;

    private DataType(String value,String description) {
        this.value = value;
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static DataType get(String v){
        for (DataType dataType : DataType.values()) {
            if (dataType.value.equals(v)) {
                return dataType;
            }
        }
        return null;
    }
}
