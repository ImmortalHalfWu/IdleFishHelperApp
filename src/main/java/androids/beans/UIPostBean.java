package androids.beans;

import java.io.File;
import java.util.List;

public class UIPostBean {

    private final String title;
    private final String info;
    private final List<File> imageList;
    private final List<String> imgTag;
    private final String price;
    private final String oldPrice;


    public UIPostBean(String title, String info, List<File> imageList, List<String> imgTag, String price, String oldPrice) {
        this.title = title;
        this.info = info;
        this.imageList = imageList;
        this.imgTag = imgTag;
        this.price = price;
        this.oldPrice = oldPrice;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public List<File> getImageList() {
        return imageList;
    }

    public List<String> getImgTag() {
        return imgTag;
    }

    public String getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "UIPostBean{" +
                "title='" + title + '\'' +
                ", info='" + info + '\'' +
                ", imagCount=" + imageList +
                ", imgTag=" + imgTag +
                ", price='" + price + '\'' +
                '}';
    }

    public String getOldPrice() {
        return oldPrice;
    }
}
