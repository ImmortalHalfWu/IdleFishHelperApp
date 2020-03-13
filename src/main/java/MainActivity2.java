import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.MyBrowser;
import browsers.beans.ProductInfoBean;
import com.google.gson.Gson;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.dom.By;
import com.teamdev.jxbrowser.chromium.dom.DOMDocument;
import com.teamdev.jxbrowser.chromium.dom.DOMElement;
import com.teamdev.jxbrowser.chromium.dom.DOMNode;
import com.teamdev.jxbrowser.chromium.events.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import static com.teamdev.jxbrowser.chromium.dom.DOMNodeType.ElementNode;

public class MainActivity2 {
//
//    static {
//        try {
//            Class claz = null;
//            //6.5.1版本破解 兼容xp
////            claz =  Class.forName("com.teamdev.jxbrowser.chromium.aq");
//            //6.21版本破解 默认使用最新的6.21版本
//            claz =  Class.forName("com.teamdev.jxbrowser.chromium.ba");
//
//            Field e = claz.getDeclaredField("e");
//            Field f = claz.getDeclaredField("f");
//
//
//            e.setAccessible(true);
//            f.setAccessible(true);
//            Field modifersField = Field.class.getDeclaredField("modifiers");
//            modifersField.setAccessible(true);
//            modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
//            modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
//            e.set(null, new BigInteger("1"));
//            f.set(null, new BigInteger("1"));
//            modifersField.setAccessible(false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void main(String[] args) {
//        EventQueue.invokeLater(() -> {
//            try {
//                new MainActivity2();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//
//    private final String home = System.getProperty("user.home");
//    private final String jsonFilePath = home + File.separator + "Documents" + File.separator + "Java.txt";
//    private final static String URL_MAIN = "http://baicai.manmanbuy.com/";
//    private Browser browser;
//    private final java.util.List<ProductInfoBean> productInfoBeans = new ArrayList<>();
//
//    private MainActivity2() {
////        AdbTest.start2();
////        AdbTest.jsonProduct();
////        AdbTest.fenciTest();
////        AdbTest.tagTest();
//
////        String jsonString = BrowserUtils.FileUtils.readFile(jsonFilePath);
////        browsers.config.ProductInfoBean[] productInfoBeans = new Gson().fromJson(jsonString, browsers.config.ProductInfoBean[].class);
////        this.productInfoBeans.addAll(Arrays.asList(productInfoBeans));
//        init();
//    }
//
//    private void init() {
//
//        JFrame frame = new JFrame();
//        frame.getContentPane().setEnabled(false);
//        final String title = "title";
//
//
//        //不显示标题栏,最大化,最小化,退出按钮
//        //frame.setUndecorated(true)；
//
//        //窗体大小
//        frame.setSize(750, 650);
//        // 固定大小不可改
//        frame.setResizable(false);
//        // 标题
//        frame.setTitle(title);
//        frame.getContentPane().setLayout(null);
//
//        frame.setExtendedState(JFrame.MAXIMIZED_VERT);
//        frame.setLocationByPlatform(true);
//
//
//
//        browser = MyBrowser.init().getBrowser();
////        browser.setPopupHandler(popupParams -> {
////            browser.loadURL(popupParams.getURL());
////            return null;
////        });
//        BrowserView view = new BrowserView(browser);
//
//        view.setBounds(0, 0, 600, 550);
//
//        frame.getContentPane().add(view);
////        browser.loadURL(URL_MAIN);
//
//
//        JTextArea jTextArea = new JTextArea();
//        jTextArea.setBounds(600, 300, 100, 50);
//        jTextArea.setLineWrap(true);
//        jTextArea.setWrapStyleWord(true);
//        jTextArea.setText(URL_MAIN);
//
//        JScrollPane jScrollPane = new JScrollPane();
//        jScrollPane.setBounds(600, 300, 100, 50);
//        jScrollPane.setViewportView(jTextArea);
//        frame.getContentPane().add(jScrollPane);
//
//
//        JButton jButton = new JButton("加载");
//        jButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                browser.loadURL(jTextArea.getText());
//            }
//        });
//        jButton.setBounds(600, 350, 100, 50);
//        frame.getContentPane().add(jButton);
//
//
//        JButton startButton = new JButton("开始");
//        startButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadManManBuy();
//            }
//        });
//
//        startButton.setBounds(600, 400, 100, 50);
//        frame.getContentPane().add(startButton);
//
//
//        JButton debugButton = new JButton("断点");
//        debugButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String html = browser.getHTML();
//            }
//        });
//        debugButton.setBounds(600, 450, 100, 50);
//        frame.getContentPane().add(debugButton);
//
//
//        JButton reloadButton = new JButton("刷新");
//        reloadButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                browser.reload();
//            }
//        });
//        reloadButton.setBounds(600, 500, 100, 50);
//        frame.getContentPane().add(reloadButton);
//
//        JButton backButton = new JButton("返回");
//        backButton.addActionListener(new AbstractAction() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                browser.goBack();
//            }
//        });
//        backButton.setBounds(600, 550, 100, 50);
//        frame.getContentPane().add(backButton);
//
//        configBrowserView();
//
//        frame.setVisible(true);
//    }
//
//    private int pageiIndex = 0;
//    private void loadManManBuy() {
//        browser.loadURL("http://baicai.manmanbuy.com/Default.aspx?PageID=" + ++pageiIndex);
//    }
//
//    private void configBrowserView() {
//
//        // 全网过滤
////        browser.getContext().getNetworkService().setNetworkDelegate(new DefaultNetworkDelegate(){
////            @Override
////            public void onBeforeURLRequest(BeforeURLRequestParams params) {
////                super.onBeforeURLRequest(params);
////                if (params.getURL().equals("http://baicai.manmanbuy.com/")) {
//////                    log("Before：" + params.getURL());
////                }
////            }
////
////            @Override
////            public void onDataReceived(DataReceivedParams params) {
////                super.onDataReceived(params);
////                if (params.getURL().startsWith("http://baicai.manmanbuy.com/") && params.getMimeType().equals("text/html")) {
//////                    log(request.get(params.getURL()));
////                }
////            }
////
////            @Override
////            public void onResponseStarted(ResponseStartedParams params) {
////                super.onResponseStarted(params);
////            }
////
////            @Override
////            public void onCompleted(RequestCompletedParams params) {
////                super.onCompleted(params);
//////                log(params);
////            }
////
////            @Override
////            public void onDestroyed(RequestParams params) {
////                super.onDestroyed(params);
////            }
////        });
////        // 网页加载进度
////        browser.addLoadListener(loadListener);
//    }
//
//    private LoadListener loadListener = new LoadAdapter() {
//        @Override
//        public void onStartLoadingFrame(StartLoadingEvent event) {
//            super.onStartLoadingFrame(event);
//        }
//
//        @Override
//        public void onDocumentLoadedInFrame(FrameLoadEvent event) {
//            super.onDocumentLoadedInFrame(event);
//        }
//
//        @Override
//        public void onDocumentLoadedInMainFrame(LoadEvent event) {
//            super.onDocumentLoadedInMainFrame(event);
//        }
//
//        @Override
//        public void onFailLoadingFrame(FailLoadingEvent event) {
//            super.onFailLoadingFrame(event);
//            log(event);
//        }
//
//        @Override
//        public void onFinishLoadingFrame(FinishLoadingEvent event) {
//            super.onFinishLoadingFrame(event);
//            String validatedURL = event.getValidatedURL();
//            if (validatedURL == null || validatedURL.length() == 0) {
//                return;
//            }
//
//            String html = event.getBrowser().getHTML();
//
////            log(html);
//            if (validatedURL.contains("rate.tmall.com/list_detail_rate")) {
////                log(html);
//            }
//
//            // 商品基础信息
//            if (validatedURL.startsWith("http://baicai.manmanbuy.com")) {
//                findProductInfos(browser);
//                return;
//            }
//
//            // 商品优惠券时间及商品详情URL
//            if (validatedURL.startsWith("https://uland.taobao.com/coupon/")) {
//                findProductYHQTime(browser);
//                return;
//            }
//
//            // 去天猫抓去商品大图
//            if (validatedURL.startsWith("https://detail.tmall.com") || validatedURL.startsWith("https://detail.yao.95095.com")) {
//                new Thread(){
//                    @Override
//                    public void run() {
//                        DOMDocument document = browser.getDocument();
////                        DOMElement contentElement = document.findElement(By.className("content ke-post"));
//
//                        int scrollIndex = 365;
//                        do {
//                            try {
//                                scrollIndex += 50;
//                                browser.executeJavaScriptAndReturnValue("window.scrollTo({top: " + scrollIndex +",behavior: \"smooth\" });");
//                                sleep(4000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        } while (document.findElement(By.className("content ke-post")).getInnerText().contains("描述加载"));
//
//                        findProductOrderPicTianMao(browser);
//
//                        super.run();
//                    }
//                }.start();
//            }
//
//            // 去淘宝抓去商品大图
//            if (validatedURL.startsWith("https://item.taobao.com/item.htm")) {
//
//                new Thread(){
//                    @Override
//                    public void run() {
//                        DOMDocument document = browser.getDocument();
//                        DOMElement contentElement = document.findElement(By.id("J_DivItemDesc")).findElement(By.tagName("p"));
//
//                        int scrollIndex = 395;
//                        do {
//                            try {
//                                scrollIndex += 50;
//                                browser.executeJavaScriptAndReturnValue("window.scrollTo({top: " + scrollIndex +",behavior: \"smooth\" });");
//                                sleep(4000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        } while (contentElement.getChildren().size() == 0);
//
//                        findProductOrderPicTaoBao(browser);
//
//                        super.run();
//                    }
//                }.start();
//            }
//
//            if (validatedURL.startsWith("https://login.taobao.com")) {
//                closeLoginDialog(browser);
//            }
//
//            if (validatedURL.startsWith("https://ai.taobao.com")) {
//                browser.goBack();
//            }
//
//        }
//
//        private void closeLoginDialog(Browser browser) {
//            DOMDocument document = browser.getDocument();
//            document.findElement(By.id("sufei-dialog-close")).click();
//        }
//
//        private int orderPicIndex = 0;
//        private void findProductOrderPicTianMao(Browser browser) {
//            // content ke-post  详情图
//            // J_AttrUL  产品参数，用于标签
//            DOMElement documentElement = browser.getDocument().getDocumentElement();
//            ProductInfoBean productInfoBean = productInfoBeans.get(orderPicIndex);
//
//            // 详情图
//            DOMElement element = documentElement.findElement(By.className("content ke-post"));
//            java.util.List<DOMElement> imgs = element.findElements(By.tagName("img"));
//
//            int size = imgs.size();
//            for (int i = size <= 5 ? 0 : size / 2; i < size; i++) {
//
//                DOMElement domElement = imgs.get(i);
//                String src = "";
//                if (domElement.hasAttribute("data-ks-lazyload")) {
//                    src = domElement.getAttribute("data-ks-lazyload");
//                } else if (domElement.hasAttribute("src")) {
//                    src = domElement.getAttribute("src");
//                }
//                src = src.startsWith("http") ? src : "http:" + src;
//                productInfoBean.addImgSrcUrl(src);
//
//            }
//
//            // 产品标签
////            DOMElement j_attrUL = documentElement.findElement(By.id("J_AttrUL"));
////            List<DOMElement> lis = j_attrUL.findElements(By.tagName("li"));
////            for (DOMElement domElement : lis) {
////                if (domElement.hasAttribute("title")) {
////                    String title = domElement.getAttribute("title");
////                    if (title.indexOf("&nbsp;") == title.lastIndexOf("&nbsp;")) {
////                        productInfoBean.addTag(title.replace("&nbsp;", ""));
////                    }
////                }
////            }
//
//            if (++orderPicIndex < productInfoBeans.size()) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                browser.loadURL(productInfoBeans.get(orderPicIndex).getBuyUrl());
//            } else {
//                FileUtils.fileLinesWrite(jsonFilePath, new Gson().toJson(productInfoBeans), false);
//                log(productInfoBeans);
//            }
//        }
//
//        private void findProductOrderPicTaoBao(Browser browser) {
//            // content ke-post  详情图
//            // J_AttrUL  产品参数，用于标签
//            DOMElement documentElement = browser.getDocument().getDocumentElement();
//            ProductInfoBean productInfoBean = productInfoBeans.get(orderPicIndex);
//
//            // 详情图
//            DOMElement element = documentElement.findElement(By.id("J_DivItemDesc")).findElement(By.tagName("p"));
//            java.util.List<DOMElement> imgs = element.findElements(By.tagName("img"));
//
//            int size = imgs.size();
//            for (int i = size <= 5 ? 0 : size / 3; i < size; i++) {
//
//                DOMElement domElement = imgs.get(i);
//                String src = "";
//                if (domElement.hasAttribute("data-ks-lazyload")) {
//                    src = domElement.getAttribute("data-ks-lazyload");
//                } else if (domElement.hasAttribute("src")) {
//                    src = domElement.getAttribute("src");
//                }
//                src = src.startsWith("http") ? src : "http:" + src;
//                productInfoBean.addImgSrcUrl(src);
//
//            }
//
//            // 产品标签
////            DOMElement j_attrUL = documentElement.findElement(By.className("attributes-list"));
////            List<DOMElement> lis = j_attrUL.findElements(By.tagName("li"));
////            for (DOMElement domElement : lis) {
////                if (domElement.hasAttribute("title")) {
////                    String title = domElement.getAttribute("title");
////                    if (title.indexOf("&nbsp;") == title.lastIndexOf("&nbsp;")) {
////                        productInfoBean.addTag(title.replace("&nbsp;", ""));
////                    }
////                }
////            }
//
//            if (++orderPicIndex < productInfoBeans.size()) {
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                browser.loadURL(productInfoBeans.get(orderPicIndex).getBuyUrl());
//            } else {
//                FileUtils.fileLinesWrite(jsonFilePath, new Gson().toJson(productInfoBeans), false);
//                log(productInfoBeans);
//            }
//        }
//
//        private int yhqIndex = 0;
//        private int maxYHQCount = 200;
//        private LinkedHashSet<ProductInfoBean> yhqTimeRemoveList = new LinkedHashSet<>();
//        /**
//         * 获取优惠券信息
//         */
//        private void findProductYHQTime(Browser browser) {
//            DOMDocument document = browser.getDocument();
//
//            ProductInfoBean productInfoBean = productInfoBeans.get(yhqIndex);
////            browsers.config.ProductInfoBean productInfoBean = new browsers.config.ProductInfoBean();
//
//            if (document.findElement(By.className("coupons-data")) != null) {
//                DOMElement element1 = document.findElement(By.className("coupons-data"));
//                long innerText = timeToStamp(element1.getInnerText().split("-")[1]);              // 优惠券到期时间
//
//                // 2019-08-17 这部分的超时过滤太夸张
//                if (innerText > timeTomorrowBegin()) {
//                    DOMElement element = document.findElement(By.className("item-wrap"));
//                    DOMElement a = element.findElement(By.tagName("a"));                                    // 淘宝链接
//                    String href = a.getAttribute("href");
//                    String name = a.findElement(By.className("title")).getInnerText().replace("\n", "");
//                    name = formatProductName(name);
//
//                    productInfoBean.setYhqEndTime(innerText);
//                    productInfoBean.setBuyUrl(href);
//                    productInfoBean.setTags(AdbTest.findFengCi(name));
//                    productInfoBean.setProductName(name);
//
//                    log(innerText + "\t" + href);
//                } else {
//                    log(productInfoBean.getProductName() +"优惠券超时" + innerText);
//                    yhqTimeRemoveList.add(productInfoBean);
//                    ++maxYHQCount;
//                }
//
//            } else {
//                log(productInfoBean.getProductName() + "\t优惠券异常");
//                yhqTimeRemoveList.add(productInfoBean);
//                ++maxYHQCount;
//            }
//
//            if (++yhqIndex < maxYHQCount && yhqIndex < productInfoBeans.size()) {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                browser.loadURL(productInfoBeans.get(yhqIndex).getYhqUrl());
//
//            } else {
//                java.util.List<ProductInfoBean> productInfoBeansTemp = new ArrayList<>(productInfoBeans);
//                productInfoBeansTemp.removeAll(yhqTimeRemoveList);
//                productInfoBeans.clear();
//                productInfoBeans.addAll(productInfoBeansTemp.subList(0, productInfoBeansTemp.size() < 200 ? productInfoBeansTemp.size() : 200));
//
//                FileUtils.fileLinesWrite(jsonFilePath, new Gson().toJson(productInfoBeans), false);
//
//                browser.loadURL(productInfoBeans.get(orderPicIndex).getBuyUrl());
//            }
//        }
//
//
//        // 买卖   赠   旗舰  半价  件   送   0-9
//        private String[] nameFilter = new String[]{"第2件10元", "买", "卖", "赠", "旗舰", "半价", "件", "双", "送", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
//        private LinkedHashSet<ProductInfoBean> productInfoBeanLinkedHashSet = new LinkedHashSet<>();
//        /**
//         * 获取商品列表
//         */
//        private void findProductInfos(Browser browser) {
//            DOMElement container = browser.getDocument().findElement(By.id("container"));
//
//            if (container.getInnerText().contains("没有搜到您要的商品")) {
//                productInfoBeans.clear();
//                productInfoBeans.addAll(productInfoBeanLinkedHashSet);
//                log(productInfoBeans);
//                log("over=========================================================");
//                log("over=========================================================");
//                log("over=========================================================");
//                sortProductForPriceAndOrderNum(productInfoBeans);
////                productInfoBeans = this.productInfoBeans.subList(productInfoBeans.size() < 150 ? 0 : productInfoBeans.size() - 150, productInfoBeans.size());
//                log(productInfoBeans);
//
//                FileUtils.fileLinesWrite(jsonFilePath, new Gson().toJson(productInfoBeans), false);
//
//                browser.loadURL(productInfoBeans.get(yhqIndex).getYhqUrl());
//
////                findProductYHQTime(browser);
//                return;
//            }
//
//            java.util.List<DOMNode> children = container.getChildren();
//            for (DOMNode node :
//                    children) {
//
//                if (node.getNodeType() == ElementNode) {
//                    DOMElement picElement = node.findElement(By.className("pic"));
//                    DOMElement imgElement = picElement.findElement(By.tagName("img"));
//                    String imgSrcUrl = "";
//                    String yhqUrl = "";
//                    String productInfo = "";
//                    String priceCurrent = "";
//                    String priceOld = "";
//                    String orderNum = "";
//
//                    try {
//
//                        // ============================商品图像======================================
////                        if (imgElement.hasAttribute("src")) {
////                            imgSrcUrl = imgElement.getAttributes().get("src");                           // 商品图像
////                            if (!imgSrcUrl.startsWith("https:")) {
////                                imgSrcUrl = "https:" + imgSrcUrl;
////                            }
////                        }
//
//
//                        // ============================优惠券链接======================================
//                        DOMElement goBuyElement = node.findElement(By.className("gobuy"));
//                        DOMElement goBuyAElement = goBuyElement.findElement(By.tagName("a"));
//                        if ((yhqUrl = goBuyAElement.getAttributes().get("href")).contains("ProductDetail.aspx")) {
//                            throw new IllegalArgumentException("优惠卷URL错误, 放弃此商品" + node.toString());
//                        }
//
//
//                        // ============================商品名称======================================
//                        DOMElement titElement = node.findElement(By.className("tit"));
//                        DOMElement titAElement = titElement.findElement(By.tagName("a"));
//                        productInfo = formatProductName(titAElement.getInnerText());
//
//
//
//                        // ============================两种价格======================================
//                        try {
////
//                            DOMElement priceCurrentElement = node.findElement(By.className("price-current"));
//                            priceCurrentElement = priceCurrentElement == null ? node.findElement(By.className("price-current-hui")) : priceCurrentElement;
//                            priceCurrent = priceCurrentElement.getInnerText().replace("¥", "").replace("券后价", "");   // 劵后价
//                            priceOld = node.findElement(By.className("price-old")).getInnerText().replace("¥", "");           // 原价
//                        } catch (NullPointerException e) {
//                            e.printStackTrace();
//                        }
//
//                        // ============================销量======================================
//                        DOMElement yhqleft2 = node.findElement(By.className("yhqleft2"));
//                        if (yhqleft2 == null) {
//                            orderNum = "10";
//                        } else {
//                            orderNum = yhqleft2.getInnerText().replace("月销", "").replace("笔", "");           // 销量
//                        }
//
//                        ProductInfoBean productInfoBean = new ProductInfoBean();
//                        productInfoBean.setYhqUrl(yhqUrl);
////                        productInfoBean.addImgSrcUrl(imgSrcUrl);
//                        productInfoBean.setOrderNum(Integer.parseInt(orderNum));
//                        productInfoBean.setPriceCurrent(Double.parseDouble(priceCurrent));
//                        productInfoBean.setPriceOld(Double.parseDouble(priceOld));
//                        productInfoBean.setProductInfo("【全新包邮】" + productInfo);
//                        productInfoBeanLinkedHashSet.add(productInfoBean);
////                        log( productName +"\t" + "\t"+ priceCurrent +"\t" + "\t"+ priceOld +"\t" + "\t"+ orderNum);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        log(imgSrcUrl +"\t" + "\t"+ yhqUrl +"\t" + "\t"+ productInfo +"\t" + "\t"+ priceCurrent +"\t" + "\t"+ priceOld +"\t" + "\t"+ orderNum);
//                    }
//
//                }
//            }
//
////            sortProductForPriceAndOrderNum(productInfoBeans);
//            loadManManBuy();
//        }
//
//        @Override
//        public void onProvisionalLoadingFrame(ProvisionalLoadingEvent event) {
//            super.onProvisionalLoadingFrame(event);
//            log(event);
//        }
//
//    };
//
//    private void sortProductForPriceAndOrderNum(List<ProductInfoBean> productInfoBeans) {
//
//        BrowserUtils.sortProductForPriceAndOrderNum(productInfoBeans);
//
//    }
//
//    private static Gson gson = new Gson();
//    private static final String TAG = "MainActivity";
//    private static void log(Object info) {
//        System.out.println(info.toString());
//    }
//
//    /* //日期转换为时间戳 */
//    public static long timeToStamp(String timers) {
//        return BrowserUtils.timeToStamp(timers);
//    }
//
//
//    /**
//     * @return 当天零点时间戳
//     */
//    public static long getTodayZeroTime() {
//        return BrowserUtils.getTodayZeroTime();
//    }
//
//    public static long timeTomorrowBegin() {
//        return BrowserUtils.timeTomorrowBegin();
//    }
//
//    public static String formatProductName(String productNameT) {
//        return BrowserUtils.formatProductName(productNameT);
//    }
//

}
