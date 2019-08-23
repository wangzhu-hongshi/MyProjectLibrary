package cn.itcast.travel.domain;

import java.util.List;

/**
 * 分页对象

 */
public class PageBean<T> {
    private int totaCout;//总记录数
    private int totaPage;//总页码
    private int turrentPage;//当前页码
    private int pageSize;//每页显示的条数
    private List<T> list;//每页展示的数据集合

    public int getTotaCout() {
        return totaCout;
    }

    public void setTotaCout(int totaCout) {
        this.totaCout = totaCout;
    }

    public int getTotaPage() {
        return totaPage;
    }

    public void setTotaPage(int totaPage) {
        this.totaPage = totaPage;
    }

    public int getTurrentPage() {
        return turrentPage;
    }

    public void setTurrentPage(int turrentPage) {
        this.turrentPage = turrentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totaCout=" + totaCout +
                ", totaPage=" + totaPage +
                ", turrentPage=" + turrentPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
