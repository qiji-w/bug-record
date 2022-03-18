package com.bug.bugrecordbackend.dto.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseData<T> implements Serializable {
    private Integer code;
    private String message;
    private  T data;
    private Long total;

    public static final int SUCCESS_CODE=0;
    public static final int FAILURE_CODE=999;

    public static ResponseData success(){
        ResponseData responseData = new ResponseData();
        responseData.setCode(0);
        responseData.setMessage("操作成功。");

        return responseData;
    }

    public static ResponseData success(String message){
        ResponseData responseData = new ResponseData();
        responseData.setCode(0);
        responseData.setMessage(message);

        return responseData;
    }

    public static<T> ResponseData success(T data){
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setCode(0);
        responseData.setMessage("操作成功。");
        responseData.setData(data);

        return responseData;
    }

    public static<T> ResponseData success(String message, T data){
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setCode(0);
        responseData.setMessage(message);
        responseData.setData(data);

        return responseData;
    }

    public static<T> ResponseData success(T data, Long total){
        ResponseData<T> responseData = new ResponseData<>();
        responseData.setCode(0);
        responseData.setMessage("操作成功。");
        responseData.setData(data);
        responseData.setTotal(total);

        return responseData;
    }

    public static<T> ResponseData success(String message, T data, Long total){
        ResponseData<T> responseData = new ResponseData<T>();
        responseData.setCode(0);
        responseData.setMessage(message);
        responseData.setData(data);
        responseData.setTotal(total);

        return responseData;
    }


    public static ResponseData failure(){
        ResponseData responseData = new ResponseData();
        responseData.setCode(999);
        responseData.setMessage("操作失败。");

        return responseData;
    }

    public static ResponseData failure(String errorMessage){
        ResponseData responseData = new ResponseData();
        responseData.setCode(999);
        responseData.setMessage(errorMessage);

        return responseData;
    }
}
