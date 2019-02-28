package com.maike.dimple.utils;

import com.maike.dimple.common.Base;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/12/1
 * Time: 下午12:32
 */
public class ResultCode extends Base {

    //错误码
    private int resultCode;

    //错误描述
    private String resultMsg;

    public ResultCode(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }
}
