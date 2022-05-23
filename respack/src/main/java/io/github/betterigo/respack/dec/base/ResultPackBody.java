/*
 * Copyright (c) 2021. All Rights Reserved.
 * ProjectName:  demo1
 * ClassName: ResultPackBody
 * Author: hdl
 * Date: 2021/12/9 下午3:12
 */

package io.github.betterigo.respack.dec.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;

/**
 * @author hdl
 * @since 2021/12/9 15:12
 */
public class ResultPackBody<T> implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//默认0
    private int status;

    //默认errorCode = 0
    private int err_code;

    //结果对象
    private T data;

    //一些额外的描述信息
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getErr_code() {
        return err_code;
    }

    public void setErr_code(int err_code) {
        this.err_code = err_code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
