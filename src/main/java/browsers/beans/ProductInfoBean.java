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
//                imgSrcUrls + '\t' +
                yhqUrl + '\t' +
                productName + '\t' + '\t' + priceCurrent + '\t' + '\t' + priceOld + '\t' + '\t' +
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
