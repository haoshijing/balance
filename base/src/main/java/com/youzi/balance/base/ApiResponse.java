package com.youzi.balance.base;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private int code;
    private String msg;
    private T data;

    public ApiResponse(T data){
        this.code = 200;
        this.msg = "succ";
        this.data = data;
    }

    public static ApiResponse responseError(Exception e) {
        return new ApiResponse(500, e.getMessage(), null);
    }
}
