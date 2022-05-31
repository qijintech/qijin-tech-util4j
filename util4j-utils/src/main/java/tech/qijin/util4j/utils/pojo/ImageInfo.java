package tech.qijin.util4j.utils.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageInfo {
    private String fileName;
    private int width;
    private int height;
    private long size;
}
