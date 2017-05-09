package com.example.administrator.mytest2.HTTPRequest;

/**
 * Created by Administrator on 2017/05/08.
 */

public interface HttpCallBackListener  {
    void onFinish(String response);
    void onError(Exception e);
}
