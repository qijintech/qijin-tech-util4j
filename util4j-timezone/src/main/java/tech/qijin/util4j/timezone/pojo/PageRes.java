package tech.qijin.util4j.timezone.pojo;

import lombok.Data;

/**
 * @author michealyang
 * @date 2019/7/24
 * 开始做眼保健操：←_← ↑_↑ →_→ ↓_↓
 **/
@Data
public class PageRes<T> {
    private int total;
    private int currPageNo;
    private int pageSize;
    private T data;

    public static PageRes instance(int total, int currPageNo, int pageSize, Object data) {
        PageRes pageRes = new PageRes();
        pageRes.setTotal(total);
        pageRes.setCurrPageNo(currPageNo);
        pageRes.setPageSize(pageSize);
        pageRes.setData(data);
        return pageRes;
    }
}
