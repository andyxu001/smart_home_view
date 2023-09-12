package com.andy.home.po;

import org.springframework.http.HttpStatus;

public class ResponseEntity<T> {

    private Integer status;
    private String msg;
    private Object obj;
    private Builder builder = new Builder();

    private ResponseEntity(){}

    private ResponseEntity(Integer status, String msg, Object obj){
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    private ResponseEntity(Integer status, String msg, Object obj, Builder b){
        this.status = status;
        this.msg = msg;
        this.obj = obj;
        this.builder = b;
    }

    public static ResponseEntity.Builder builder() {
        return new ResponseEntity().builder;
    }

    public static ResponseEntity.Builder ok() {
        return status(HttpStatus.OK.value());
    }

    private static ResponseEntity.Builder status(Integer status) {
        return builder().setStatus(status);
    }

    private static ResponseEntity.Builder status(Integer status,Object object) {
        return status(status).setObj(object);
    }

    public static ResponseEntity.Builder ok(String msg) {
        return ok().setMsg(msg);
    }

    public static ResponseEntity.Builder ok(String msg, Object obj) {
        return ok().setMsg(msg).setObj(obj);
    }

    public static ResponseEntity error(String msg) {
        return new ResponseEntity(500, msg, null);
    }

    public static ResponseEntity error(String msg, Object obj) {
        return new ResponseEntity(500, msg, obj);
    }

    public Integer getStatus() {
        return status;
    }

    public ResponseEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ResponseEntity setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public ResponseEntity setObj(Object obj) {
        this.obj = obj;
        return this;
    }

    /**
     * 内部类
     */
    public static class Builder<T>{
        private Integer status;
        private String msg;
        private Object obj;

        public Integer getStatus() {
            return status;
        }

        public Builder<T> setStatus(Integer status) {
            this.status = status;
            return this;
        }

        public String getMsg() {
            return msg;
        }

        public Builder<T> setMsg(String msg) {
            this.msg = msg;
            return this;
        }

        public Object getObj() {
            return obj;
        }

        public Builder<T> setObj(Object obj) {
            this.obj = obj;
            return this;
        }

        public ResponseEntity build(){
            return new ResponseEntity(this.status,this.msg,this.obj,this);
        }
    }
}
