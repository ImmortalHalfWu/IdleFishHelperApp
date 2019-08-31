import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.queues.*;
import com.teamdev.jxbrowser.chromium.*;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigInteger;


public class MainActivity {

    static {
        try {
            Class claz = null;
            //6.5.1版本破解 兼容xp
//            claz =  Class.forName("com.teamdev.jxbrowser.chromium.aq");
            //6.21版本破解 默认使用最新的6.21版本
             claz =  Class.forName("com.teamdev.jxbrowser.chromium.ba");

            Field e = claz.getDeclaredField("e");
            Field f = claz.getDeclaredField("f");


            e.setAccessible(true);
            f.setAccessible(true);
            Field modifersField = Field.class.getDeclaredField("modifiers");
            modifersField.setAccessible(true);
            modifersField.setInt(e, e.getModifiers() & ~Modifier.FINAL);
            modifersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
            e.set(null, new BigInteger("1"));
            f.set(null, new BigInteger("1"));
            modifersField.setAccessible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                FileUtils.init();
//                AndroidManager.init();
                new MainActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private final static String URL_MAIN = "http://baicai.manmanbuy.com/Default.aspx?PageID=0";
    private Browser browser;

    private MainActivity() {
//        QueueTest.test();

//        华为   上2030    下2230   左840  右1040
//        咸鱼             下1299   左1140
//        String s = "5ENDU19214004179";

//        browsers.beans.ProductInfoBean[] productInfoBeans = new Gson().fromJson(
//                FileUtils.readFile(FileUtils.createNewProductInfoFile("123")),
//                browsers.beans.ProductInfoBean[].class);
//        System.out.println(productInfoBeans.length);

//
//        for (int j = 0; j < 1; j++) {
//            ProductInfoBean bean = productInfoBeans[j];
//
//            java.util.List<String> imgSrcUrls = bean.getImgSrcUrls();
//            int size = imgSrcUrls.size() > 10 ? 10 : imgSrcUrls.size();
//            List<File> imagFiles = new ArrayList<>(size);
//
////            for (int i = 0; i < size; i++) {
//            for (int i = size - 1; i >= 0; i--) {
//                String imgSrc = imgSrcUrls.get(i);
//                String[] split = imgSrc.split("/");
//
//                imagFiles.add(FileUtils.downloadPicture(
//                        imgSrc,
//                        FileUtils.DIR_PATH_IMG + i + split[split.length - 1]
//                ));
//
//
////                for (File imagePath :
////                        imagFiles) {
////                    MLog.logi("发布商品完成，删除图片：" + s + "__" + imagePath.getName());
////                    ADBProcess.getInstance().adbDeleteFile(s, imagePath.getName());
////                }
//            }
//
//            double price = (bean.getPriceOld() - bean.getPriceCurrent()) / 2;
//            price = price > 20 ? 20 : price;
//            UIPostBean uiPostBean = new UIPostBean(bean.getProductName(),
//                    bean.getProductInfo().replace("【", "[").replace("】", "]"),
//                    imagFiles,
//                    bean.getTags(),
//                    bean.getPriceCurrent() + price + "",
//                    bean.getPriceOld() + "");
//
//            AndroidManager.init().postProduct(
//                    s, uiPostBean
//            );
//
////            new FishPostProductChoiceImgProcess(ADBProcess.getInstance(), s, FileUtils.DIR_PATH_XML)
////                    .postProduct(ADBProcess.getInstance(), s, uiPostBean);
//
//        }
//
//        // 查询发布的商品
////        List<String> postedProduct = AndroidManager.init().findPostedProduct("5ENDU19214004179");
//        //删除商品
//        for (int i = 0; i < 1; i++) {
//            ProductInfoBean bean = productInfoBeans[i];
//            AndroidManager.init().deleteProduct(s, Collections.singletonList(bean.getProductName()));
//        }

//        int size = postedProduct.size();
        init();
    }

    private void init() {

        JFrame frame = new JFrame();
        frame.getContentPane().setEnabled(false);
        final String title = "title";



        //窗体大小
        frame.setSize(1500, 800);
        // 固定大小不可改
        frame.setResizable(false);
        // 标题
        frame.setTitle(title);
        frame.getContentPane().setLayout(null);

        frame.setExtendedState(JFrame.MAXIMIZED_VERT);
        frame.setLocationByPlatform(true);

        MLoadFinishAdapter.registerStaticLoadHtmlProcee(AiTaoBaoLoadProcess.getInstance());
        MLoadFinishAdapter.registerStaticLoadHtmlProcee(LoginLoadProcess.getInstance());

        for (int i = 0; i <= 10; i++) {
            BrowserContextParams params = new BrowserContextParams(FileUtils.createNewWebCacheFile(i + ""));
            BrowserContext context = new BrowserContext(params);
            Browser browser1 = new Browser(BrowserType.LIGHTWEIGHT,context);
            browser1.setDialogHandler(new DialogHandler() {
                @Override
                public void onAlert(DialogParams dialogParams) {
                    BrowserUtils.log("onAlert");
                }

                @Override
                public CloseStatus onConfirmation(DialogParams dialogParams) {
                    BrowserUtils.log("onConfirmation");
                    return null;
                }

                @Override
                public CloseStatus onPrompt(PromptDialogParams promptDialogParams) {
                    BrowserUtils.log("onPrompt");
                    return null;
                }

                @Override
                public CloseStatus onFileChooser(FileChooserParams fileChooserParams) {
                    BrowserUtils.log("onFileChooser");
                    return null;
                }

                @Override
                public CloseStatus onBeforeUnload(UnloadDialogParams unloadDialogParams) {
                    BrowserUtils.log("onBeforeUnload");

                    return null;
                }

                @Override
                public CloseStatus onSelectCertificate(CertificatesDialogParams certificatesDialogParams) {
                    BrowserUtils.log("onSelectCertificate");

                    return null;
                }

                @Override
                public CloseStatus onReloadPostData(ReloadPostDataParams reloadPostDataParams) {
                    BrowserUtils.log("onReloadPostData");

                    return null;
                }

                @Override
                public CloseStatus onColorChooser(ColorChooserParams colorChooserParams) {
                    BrowserUtils.log("onColorChooser");

                    return null;
                }
            });
            BrowserView view1 = new BrowserView(browser1);
            view1.setBounds(i * 10, 0, 10, 10);
            frame.getContentPane().add(view1);
//            browser1.loadURL("www.baidu.com");

            browser1.setPopupHandler(popupParams -> {
                browser1.loadURL(popupParams.getURL());
                return null;
            });
            BrowserQueue.instance().put(browser1);

        }

//        NewLoadHtmlRequestQueue.instance().put(new NewLoadHtmlRequestQueue.LoadHtmlProcess() {
//            @Override
//            public String getUrl() {
//                return "https://uland.taobao.com/coupon/edetail?e=sYED98OCwHoGQASttHIRqR2CjuSDi%2FFj%2BtrJjxWNCKLx9B8gNStCk%2FY53z6DJT1AVFbcss2PR2z%2B3CZs4frrDDEhJpUUrcnYSVNZJ6IP%2BBuN5rPifjd8eXY9x3IctcCWLspxGy3zBjY8IeN8lvhRA2lzrR4%2Bfrcbz%2F6VGMQg8XmWUr9yffI9Muib2QyKSVw%2F7hHfa%2F83Y2I%3D&traceId=0b17545e15670678979565381e&union_lens=lensId:0b156441_0be0_16cdc868089_b4fb&xId=ovXCd2APTbzf62COVc7xQo5AwmqfusO59mkS4XW99d3JQr5ooCYHvpTWlvqcY1TmjzSUfu2CyuNE8Saz7AC86u&v=yingxiao";
//            }
//
//            @Override
//            public boolean canProcess(String url) {
//                return true;
//            }
//
//            @Override
//            public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
//                if (domDocument.findElement(By.className("item-info-con")) != null && !domDocument.getDocumentElement().getInnerHTML().contains("优惠券已失效")) {
//                    DOMElement element1 = domDocument.findElement(By.className("coupon-date"));
//                    String endTime = element1.getInnerText().split("-")[1];
//                    long innerText = BrowserUtils.timeToStamp(endTime);              // 优惠券到期时间
//
//                    // TODO: 2019-08-17 这部分的超时过滤太夸张
//                    if (innerText >= BrowserUtils.timeTomorrowBegin()) {
//                        DOMElement a = domDocument.findElement(By.className("item-info-con")).findElement(By.tagName("a"));                                    // 淘宝链接
//                        String href = a.getAttribute("href");
//                        String name = a.findElement(By.className("title")).getInnerText().replace(" ", "").replace("\n", "");
//                        name = BrowserUtils.formatProductName(name);
//
//                        BrowserUtils.log("有效优惠券信息：" + name + "_" + endTime);
//                    } else {
//                        BrowserUtils.log("优惠券过期:");
//                    }
//
//                } else {
//                    BrowserUtils.log("未查询到优惠券信息");
//                    try {
//                        if (domDocument.getDocumentElement().getInnerHTML().contains("滑动一下马上回来")) {
//                            Thread.sleep(60000);
//                        } else {
//                        }
//
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                return false;
//            }
//        });

//        browsers.beans.ProductInfoBean[] productInfoBeans = new Gson().fromJson(
//                FileUtils.readFile(FileUtils.createNewProductInfoFile("123")),
//                browsers.beans.ProductInfoBean[].class);
//        System.out.println(productInfoBeans.length);
//
//        ManManBuyModel.instance().addManManBuyProducts(Arrays.asList(productInfoBeans));
        NewLoadHtmlRequestQueue.instance().put(new ManManBuyAllStartPageProcess());

//        ProductInfoBean[] productInfoBeans = new Gson().fromJson(
//                FileUtils.readFile(FileUtils.createNewProductInfoFile("123")),
//                ProductInfoBean[].class);
//        int size = productInfoBeans.length;
//        for (int i = 0; i < size - 1; i++) {
//            ProductInfoBean productInfoBean = productInfoBeans[i];
//            NewLoadHtmlRequestQueue.instance().put(new ProductPicLoadProcess(productInfoBean));
//        }
//        NewLoadHtmlRequestQueue.instance().put(new ProductPicLoadEndProcess(productInfoBeans[size - 1]));

//        for (ProductInfoBean bean :
//                productInfoBeans) {
//
//        }

//        browser = MyBrowser.instance()
//                .registerBrowserLoadListener(
//                        new MBrowserLoadListener() {
//
//                            @Override
//                            public boolean onFinishLoadingFrame(FinishLoadingEvent event, String url, DOMDocument domDocument, BrowsersInterface browserInter) {
//                                String html = browser.getHTML();
//
//                                DOMElement ctl00_contentPlaceHolder1_divpage = domDocument.findElement(By.id("ctl00_ContentPlaceHolder1_divpage"));
//                                String innerText = ctl00_contentPlaceHolder1_divpage.getInnerText();
//                                String page = innerText.split("/")[1].split("页")[0];
//
//
//                                return false;
//                            }
//                        }
//                )
//                .getBrowser();

//        BrowserView view = new BrowserView(browser);
//        view.setVisible(true);
//        view.setBounds(0, 0, 100, 100);
//        frame.getContentPane().add(view);
//        browser.loadURL("https://www.baidu.com/s?ie=UTF-8&wd=reterfit");


        JTextArea jTextArea = new JTextArea();
        jTextArea.setBounds(600, 300, 100, 50);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        jTextArea.setText(URL_MAIN);

        JScrollPane jScrollPane = new JScrollPane();
        jScrollPane.setBounds(600, 300, 100, 50);
        jScrollPane.setViewportView(jTextArea);
        frame.getContentPane().add(jScrollPane);


        JButton jButton = new JButton("加载");
        jButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.loadURL(jTextArea.getText());
            }
        });
        jButton.setBounds(600, 350, 100, 50);
        frame.getContentPane().add(jButton);


        JButton startButton = new JButton("开始");
        startButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.loadURL(URL_MAIN);
            }
        });

        startButton.setBounds(600, 400, 100, 50);
        frame.getContentPane().add(startButton);


        JButton debugButton = new JButton("断点");
        debugButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String html = browser.getHTML();
            }
        });
        debugButton.setBounds(600, 450, 100, 50);
        frame.getContentPane().add(debugButton);


        JButton reloadButton = new JButton("刷新");
        reloadButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.reload();
            }
        });
        reloadButton.setBounds(600, 500, 100, 50);
        frame.getContentPane().add(reloadButton);

        JButton backButton = new JButton("返回");
        backButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser.goBack();
            }
        });
        backButton.setBounds(600, 550, 100, 50);
        frame.getContentPane().add(backButton);


        frame.setVisible(true);
    }

}
