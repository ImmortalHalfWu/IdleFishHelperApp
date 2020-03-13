package browsers.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductInfoBean {

    private List<String> imgSrcUrls = new ArrayList<>();
    private List<String> tags = new ArrayList<>();
    private String yhqUrl = "";
    private String productName = "";
    private String productInfo = "";
    private double priceCurrent = 0.0;
    private double priceOld = 0.0;
    private int orderNum = 0;
    private Long yhqEndTime = 0L;
    private String buyUrl = "";

    public List<String> getImgSrcUrls() {
        return imgSrcUrls;
    }

    public void addImgSrcUrl(String imgSrcUrls) {
        this.imgSrcUrls.add(imgSrcUrls);
    }

    public void setImgSrcUrls(List<String> imgSrcUrls) {
        this.imgSrcUrls = imgSrcUrls;
    }

    public String getYhqUrl() {
        return yhqUrl;
    }

    public void setYhqUrl(String yhqUrl) {
        this.yhqUrl = yhqUrl;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPriceCurrent() {
        return priceCurrent;
    }

    public void setPriceCurrent(double priceCurrent) {
        this.priceCurrent = priceCurrent;
    }

    public double getPriceOld() {
        return priceOld;
    }

    public void setPriceOld(double priceOld) {
        this.priceOld = priceOld;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public Long getYhqEndTime() {
        return yhqEndTime;
    }

    public void setYhqEndTime(Long yhqEndTime) {
        this.yhqEndTime = yhqEndTime;
    }

    public String getBuyUrl() {
        return buyUrl;
    }

    public void setBuyUrl(String buyUrl) {
        this.buyUrl = buyUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void addTag(String tag) {
        tags.add(tag);
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return
                buyUrl + '\t' +
                yhqUrl + '\t' +
                productName + '\t' + '\t' + priceCurrent + '\t' + '\t' + priceOld + '\t' + '\t' + yhqEndTime + '\t' + '\t' +
                        orderNum + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductInfoBean)) return false;
        ProductInfoBean that = (ProductInfoBean) o;
        return yhqUrl.equals(that.yhqUrl) &&
                productName.equals(that.productName) &&
                productInfo.equals(that.productInfo) &&
                buyUrl.equals(that.buyUrl);
    }
/*
http://t00img.yangkeduo.com/goods/images/2020-02-08/a1b11b83-eaaa-4b45-8d48-54be6a5e23d6.jpg?imageMogr2/quality/70
http://t00img.yangkeduo.com/goods/images/2020-02-08/c072ea15-93cc-42aa-a5b8-3d115141b363.jpg?imageMogr2/quality/70
http://t00img.yangkeduo.com/goods/images/2020-02-08/c072ea15-93cc-42aa-a5b8-3d115141b363.jpg?imageMogr2/quality/70
http:////t22img.yangkeduo.com/review3/review/2020-03-11/692c13fd-42e1-4197-8abb-55f7962eb3d2.jpg?imageMogr2/strip%7CimageView2/2/w/1300/q/80
 */
    @Override
    public int hashCode() {
        return Objects.hash(yhqUrl, productName, productInfo, buyUrl);
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }
}
