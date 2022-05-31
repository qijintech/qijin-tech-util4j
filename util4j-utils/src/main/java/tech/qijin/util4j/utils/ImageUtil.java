package tech.qijin.util4j.utils;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import sun.misc.IOUtils;
import tech.qijin.util4j.utils.pojo.ImageInfo;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

@Slf4j
public class ImageUtil {
    /**
     * 获取图片信息
     *
     * @param image
     * @return
     */
    public static ImageInfo getImageInfo(File image) {
        if (image == null) return null;
        try {
            BufferedImage bufferedImage = ImageIO.read(image);
            return ImageInfo.builder()
                    .fileName(image.getName())
                    .width(bufferedImage.getWidth())
                    .height(bufferedImage.getHeight())
                    .size(image.length())
                    .build();
        } catch (IOException e) {
            log.error("ImageUtil getImageInfo exception", e);
        }
        return null;
    }

    /**
     * 按照宽高压缩普通
     * 300 x 400 在 with=200, height=200的情况下，会压缩成 150 x 200
     *
     * @param image
     * @param w
     * @param h
     * @param force    为true时，图片会被压扁或者拉伸，宽高都会严格等于指定的参数
     * @return
     */
    public static File compressByWidthAndHeight(File image, int w, int h, boolean force) {
        if (image == null) return null;
        try {
            File output = new File(image.getName());
            Thumbnails.Builder builder = Thumbnails.of(image);
            if (force) {
                builder.forceSize(w, h);
            } else {
                builder.size(w, h);
            }
            builder.toFile(output);
            return output;
        } catch (Exception e) {
            log.error("ImageUtil compressBySize exception", e);
            return null;
        }
    }

    /**
     * 按照系数压缩图片
     *
     * @param image
     * @param fileName
     * @param factor
     * @return
     */
    public static File compressByFactor(File image, String fileName, double factor) {
        if (image == null) return null;
        try {
            File output = new File(fileName);
            Thumbnails.of(image)
                    .scale(factor)
                    .toFile(output);
            return output;
        } catch (Exception e) {
            log.error("ImageUtil compressBySize exception", e);
            return null;
        }
    }

    public static void main(String[] args) throws IOException {
        File image = new File("/Users/qijin/Downloads/WechatIMG633.jpeg");
        File newImage = compressByWidthAndHeight(image, 800, 800, false);
    }
}
