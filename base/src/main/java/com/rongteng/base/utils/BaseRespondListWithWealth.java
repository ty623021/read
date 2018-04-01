package com.rongteng.base.utils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/5/24.
 */

public class BaseRespondListWithWealth<T> extends BaseRespondWithWealth{
    private String total;
    private List<T> rows;
    private List<T> datas;

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "BaseRespondListWithWealth{" +
                "total='" + total + '\'' +
                ", rows=" + rows +
                '}';
    }
}
