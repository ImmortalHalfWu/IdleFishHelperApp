import Utils.FileUtils;
import browsers.BrowserUtils;
import browsers.impls.manHot.ManManBuyHotStartPageProcess;
import browsers.impls.yhqAndPic.AiTaoBaoLoadProcess;
import browsers.impls.yhqAndPic.LoginLoadProcess;
import browsers.queues.MLoadFinishAdapter;
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
//                FileUtils.readFile(FileUtils.createNewProductInfoFile("ALL")),
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
        frame.setSize(1000, 400);
        // 固定大小不可改
        frame.setResizable(false);
        // 标题
        frame.setTitle(title);
        frame.getContentPane().setLayout(null);

        frame.setExtendedState(JFrame.MAXIMIZED_VERT);
        frame.setLocationByPlatform(true);

        MLoadFinishAdapter.registerStaticLoadHtmlProcee(AiTaoBaoLoadProcess.getInstance());
        MLoadFinishAdapter.registerStaticLoadHtmlProcee(LoginLoadProcess.getInstance());

        for (int i = 0; i < 10; i++) {
            BrowserContextParams params = new BrowserContextParams(FileUtils.createNewWebCacheFile(i + ""));
            BrowserContext context = new BrowserContext(params);
            Browser browser1 = new Browser(BrowserType.LIGHTWEIGHT,context);
            if (browser == null) {
                browser = browser1;
            }
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
            view1.setBounds(i * 100, 0, 100, 400);
            frame.getContentPane().add(view1);
//            browser1.loadURL("www.baidu.com");

            browser1.setPopupHandler(popupParams -> {
                browser1.loadURL(popupParams.getURL());
                return null;
            });
            BrowserQueue.instance().put(browser1);

        }

//        browsers.beans.ProductInfoBean[] productInfoBeans = new Gson().fromJson(
//                FileUtils.readFile(FileUtils.createNewProductInfoFile("ALL")),
//                browsers.beans.ProductInfoBean[].class);
//        System.out.println(productInfoBeans.length);
//        for (browsers.beans.ProductInfoBean product : productInfoBeans
//                ) {
//            int size = product.getImgSrcUrls().size();
//            if (size < 2) {
//                String buyUrl = product.getBuyUrl();
//                System.out.println(product.getBuyUrl());

//        browser.addLoadListener(new LoadAdapter() {
//            @Override
//            public void onFinishLoadingFrame(FinishLoadingEvent event) {
//                System.out.println(event.getValidatedURL());
//                super.onFinishLoadingFrame(event);
//            }
//        });
        // http://baicai.manmanbuy.com/Default_New.aspx?PageID=1&protype=jiukai&orderby=id
//                NewLoadHtmlRequestQueue.instance().put(new NewLoadHtmlRequestQueue.LoadHtmlProcess() {
//                    @Override
//                    public String getUrl() {
//                        return "http://baicai.manmanbuy.com/cl_0_jiukai.aspx";
//                    }
//
//                    @Override
//                    public boolean canProcess(String url) {
//                        return url.equals("http://baicai.manmanbuy.com/cl_0_jiukai.aspx");
//                    }
//
//                    @Override
//                    public boolean process(FinishLoadingEvent event, String resultUrl, DOMDocument domDocument, BrowsersInterface browser) {
//                        // content ke-post  详情图
//                        // J_AttrUL  产品参数，用于标签
//                        return true;
//                    }
//                });
//
//
//            }
//        }

//        ManManBuyAllModel.instance().addManManBuyProducts(Arrays.asList(productInfoBeans));
//        TaoBaoTmallYHQImagProcess.startFindYHQAndPIC();
        NewLoadHtmlRequestQueue.instance().put(new ManManBuyHotStartPageProcess());

//        ProductInfoBean[] productInfoBeans = new Gson().fromJson(
//                FileUtils.readFile(FileUtils.createNewProductInfoFile("ALL")),
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
