package com.zhuzhi.spring.cloud.alibaba.freeswitch.common;

/**
 * 分页响应
 *
 * @param
 * @ClassName: PageResponse
 */
public class PageResponse<T> extends BaseResponse<T> {

    /**
     *
     */
    private static final long serialVersionUID = 1061533769615819544L;
    /**
     * @Fields totalCount : 总数
     */
    private long count;
    /**
     * @Fields totalPages : 总页码
     */
    private long pages;

    public PageResponse() {

    }

    /***
     * 成功，带分页数据返回
     *
     * @param data
     *            数据
     * @return
     */
    public static <T> PageResponse<T> successPageData(T data, long totalCount, long totalPages) {
        PageResponse<T> res = new PageResponse<T>();
        res.setCode(C2cConst.SUCCESS_CODE);
        res.setMsg("success.");
        res.setData(data);
        res.setTotalCount(totalCount);
        res.setTotalPages(totalPages);
        return res;
    }

    public static <T> PageResponse<T> emptyPageData() {
        PageResponse<T> res = new PageResponse<T>();
        res.setCode(C2cConst.SUCCESS_CODE);
        res.setMsg("success.");
        res.setData(null);
        res.setTotalCount(0);
        res.setTotalPages(1);
        return res;
    }

    public static <T> PageResponse<T> fusing() {
        PageResponse<T> res = new PageResponse<T>();
        res.setCode(C2cConst.FAILED_CODE);
        res.setMsg("fusing.");
        res.setData(null);
        res.setTotalCount(0);
        res.setTotalPages(1);
        return res;
    }

    public long getCount() {
        return count;
    }

    public void setTotalCount(long count) {
        this.count = count;
    }

    public long getPages() {
        return pages;
    }

    public void setTotalPages(long pages) {
        this.pages = pages;
    }

}
