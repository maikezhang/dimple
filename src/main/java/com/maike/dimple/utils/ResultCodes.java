package com.maike.dimple.utils;

/**
 * Description:
 * Created with IntelliJ IDEA.
 * User: zhangyingjie
 * Date: 2018/12/1
 * Time: 下午12:31
 */
public final class ResultCodes {

    public static final ResultCode HANDLE_SUCCESS = new ResultCode(0, "请求已成功处理");
    public static final ResultCode HANDLE_FAILED  = new ResultCode(-1, "请求处理失败");
}
