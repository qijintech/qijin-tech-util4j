package tech.qijin.util4j.utils.pojo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * @author michealyang
 * @date 2019-11-19
 * @relax: 开始眼保健操 ←_← ↓_↓ →_→ ↑_↑
 */
@Data
@Builder
@ToString
public class HttpResp {
    private Integer code;
    private String data;
}
