package com.linfeng.zouky.liibrary.communication;

/**
 * Created by :Zouky
 * Time :2017/10/18
 * Description:
 */

public interface Operate<T> {

    void success(T body);

    void fail(String failMessage);

}