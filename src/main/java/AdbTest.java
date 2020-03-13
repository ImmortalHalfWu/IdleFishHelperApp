import Utils.FileUtils;
import Utils.MLog;
import Utils.XMLUtil;
import androids.adbs.ADBProcess;
import androids.impls.*;
import androids.interfaces.IFishAppProcess;
import androids.beans.UIPostBean;
import browsers.BrowserUtils;
import browsers.beans.ProductInfoBean;
import com.google.gson.Gson;

import java.io.File;
import java.util.*;

public class AdbTest {

    public static void start() {



        String[] strings = {
                "adb connect 127.0.0.1:62001",
                "adb connect 127.0.0.1:21503",
                "adb connect 127.0.0.1:6555",
                "adb connect 127.0.0.1:53001",
                "adb connect 127.0.0.1:7555",
        };

        FileUtils.mkDir(FileUtils.DIR_PATH_IMG);
        FileUtils.mkDir(FileUtils.DIR_PATH_XML);
        FileUtils.mkDir(FileUtils.DIR_PATH_JSON);
//        FileUtils.mkFile(FileUtils.FILE_PATH_XML);
//        FileUtils.mkFile(FileUtils.FILE_PATH_NEW_PRODUCT_JSON);

        String s1 = FileUtils.readFile("/Users/huangchen/ui.xml");
        XMLUtil.findStringXml(s1);


//        runInCmd("adb shell uiautomator dump /sdcard/ui.xml");
//        runInCmd("adb pull /sdcard/ui.xml ui.xml");


//// 截取目前屏幕上所有控件生成xml
//        adb shell uiautomator dump /sdcard/ui.xml
//
//// 拉取控件信息到本地
//        adb pull /sdcard/ui.xml ui.xml
    }

    public static void start2() {

//        ADBProcess.getInstance().adbInputTap("5ENDU19214004179", 540, 2149);


        List<UIPostBean> uiPostBeans = new ArrayList<>();

        ArrayList<File> imags = new ArrayList();
        imags.add(new File(FileUtils.DIR_PATH_IMG + "123.jpg"));

        String[] strings = {
                "【抖音爆款】迷你蓝牙音箱播报器",
                "新宠之康 宠物狗狗猫咪妈咪奶粉280g",
                "【送挂件香水瓶】车用车品车载香水补充液",
                "5大包【碧C】手口屁湿巾带盖80抽*5包",
                "红豆薏米菊花三清茶10g*10袋",
                "科榜纯钛防辐射防蓝光近视眼镜",
                "【黛维莉】香水沐浴露550ml",
                "【独立包装】黑全麦面粉500g*5袋",
                "怀梦时光 MM021-3 女士内裤 4条装",
                "【一拖三伸缩版】苹果安卓多数据线",};

        for (int i = 0; i < 1; i++) {

            List<String> tags = findFengCi(strings[i]);
//            tags.add("电动");
//            tags.add("可乐");
//            tags.add("牙刷");
//            tags.add("连衣裙");
//            tags.add("14");
//            tags.add("恐龙");
//            tags.add("背心");

            long l1 = System.currentTimeMillis();
            long l = l1 % 100;

            UIPostBean uiPostBean = new UIPostBean(
                    strings[i],
                    "这里是Info",
                    imags,
                    tags,
                    l / 2 + "",
                    l + "");
            uiPostBeans.add(uiPostBean);
        }


        String deviceId = "5ENDU19214004179";
        ADBProcess adbProcess = new ADBProcess();
        IFishAppProcess iFishAppProcess = new FishAppProcess();

//        for (UIPostBean uiPostBean : uiPostBeans) {
//            iFishAppProcess.startApp(adbProcess, deviceId)
//                    .toMainActivity(adbProcess, deviceId)
//                    .toPostProductActivity(adbProcess, deviceId)
//                    .postProductByOrder(adbProcess, deviceId, uiPostBean);
//        }

        // 删除指定名称商品
//        iFishAppProcess.toMainActivity(adbProcess, deviceId)
//                .toMyPage(adbProcess, deviceId)
//                .toMyPostedActivity(adbProcess, deviceId)
//                .deleteProductsByName(adbProcess, deviceId, Collections.singletonList("【抖音爆款】迷你蓝牙音箱播报器"));

//        IFishHomeActivityProcess iFishHomeActivityProcess = iFishAppProcess
//                .startApp(new ADBProcess(), deviceId)
//                .toMainActivity(ADBProcess.getInstance(), deviceId);

//        IFishHomeMyPageProcess iFishHomeMyPageProcess = iFishHomeActivityProcess.toMyPage(ADBProcess.getInstance(), deviceId);
//        String postedNum = iFishHomeMyPageProcess.getPostedNum(ADBProcess.getInstance(), deviceId);
        String s = "";

        // 闲鱼App
//        iFishAppProcess
//                .startApp(new ADBProcess(), deviceId)
//                .toMainActivity(ADBProcess.getInstance(), deviceId);

        // 我卖出的
//        new FishMyPostedActivityProcess(
//                new ADBProcess(),
//                "5ENDU19214004179",
//                FileUtils.DIR_PATH_XML
//        );

        // 咸鱼首页
//        new FishHomeActivityProcess(
//                new ADBProcess(),
//                "5ENDU19214004179",
//                FileUtils.DIR_PATH_XML
//        ).toMyPage(new ADBProcess(), "5ENDU19214004179")
//        .toMyPostedActivity(
//                                ADBProcess.getInstance(), "5ENDU19214004179"
//        )
//        .toMyOrderSucActivity(
//                ADBProcess.getInstance(), "5ENDU19214004179"
//        )


        // 选择交易方式，免费还是买卖
//        new FishPostProductTypeChoiceProcess(
//                new ADBProcess(),
//                "5ENDU19214004179",
//                FileUtils.DIR_PATH_XML
//        ).postProductByOrder(ADBProcess.getInstance(), "5ENDU19214004179", uiPostBean);


        // 选择标签
//        new FishPostProductChoiceTagProcess(
//                new ADBProcess(),
//                "5ENDU19214004179",
//                FileUtils.DIR_PATH_XML
//        )
//                .choiceTag(
//                        new ADBProcess(),
//                        "5ENDU19214004179",
//                        "电动电动电动电动电动电动电动电动"
//                );


        // 添加标签
//        new FishPostProductAddTagProcess(
//                new ADBProcess(),
//                "5ENDU19214004179",
//                FileUtils.DIR_PATH_XML
//        )
//                .postProduct(
//                        new ADBProcess(),
//                        "5ENDU19214004179",
//                        uiPostBean
//                );


//          图片选择
//        new FishPostProductChoiceImgProcess(
//                new ADBProcess(),
//                "5ENDU19214004179",
//                FileUtils.DIR_PATH_XML
//        )
//                .postProduct(
//                        new ADBProcess(),
//                        "5ENDU19214004179",
//                        uiPostBean
//                );


    }

//    public static void start3() {
//
//        ArrayList<String> imags = new ArrayList();
//        imags.add(FileUtils.DIR_PATH_IMG + "123.jpg");
//        UIPostBean uiPostBean = new UIPostBean("Nike 运动背心", "这里是Info", imags, new ArrayList<>(), "20.1", "330.3");
//
//        new FishPostProductActivityProcess(
//                new ADBProcess(),
//                "5ENDU19214004179",
//                FileUtils.DIR_PATH_XML
//        )
//                .postProduct(
//                        new ADBProcess(),
//                        "5ENDU19214004179",
//                        uiPostBean
//                );
//
//
//    }

    public static List<String> findFengCi(String title) {
        return BrowserUtils.findFengCi(title);
    }

    public static void fenciTest() {
        String[] strings = {
                "【抖音爆款】迷你蓝牙音箱播报器",
                "新宠之康 宠物狗狗猫咪妈咪奶粉280g",
                "【送挂件香水瓶】车用车品车载香水补充液",
                "5大包【碧C】手口屁湿巾带盖80抽*5包",
                "红豆薏米菊花三清茶10g*10袋",
                "科榜纯钛防辐射防蓝光近视眼镜",
                "【黛维莉】香水沐浴露550ml",
                "【独立包装】黑全麦面粉500g*5袋",
                "怀梦时光 MM021-3 女士内裤 4条装",
                "【一拖三伸缩版】苹果安卓多数据线",};
        /*
"【抖音爆款】迷你蓝牙音箱播报器",
"新宠之康 宠物狗狗猫咪妈咪奶粉280g",
"【送挂件香水瓶】车用车品车载香水补充液",
"5大包【碧C】手口屁湿巾带盖80抽*5包",
"红豆薏米菊花三清茶10g*10袋",
"科榜纯钛防辐射防蓝光近视眼镜",
"【黛维莉】香水沐浴露550ml",
"【独立包装】黑全麦面粉500g*5袋",
"怀梦时光 MM021-3 女士内裤 4条装",
"【一拖三伸缩版】苹果安卓多数据线",
         */

        for (String s : strings) {
//            List<Term> terms = ToAnalysis.parse(s).getTerms();
//            for(Term term : terms) {
//                if (term.getNatureStr().startsWith("n") || term.getNatureStr().startsWith("a")) {
//                    System.out.print(term.getName() + "\t");
//                }
////                System.out.print(term);
//            }
            System.out.println();
        }


    }

    public static void jsonProduct() {


        String deviceId = "5ENDU19214004179";
        ADBProcess adbProcess = new ADBProcess();
        IFishAppProcess iFishAppProcess = new FishAppProcess();

//        adb push /Users/huangchen/ideaProducts/imgs/O1CN01bBOmBh1XTEdS18d1W_\!\!2329842924.jpg /sdcard/1231.jpg
//        am broadcast -a android.intent.action.MEDIA_SCANNER_SCAN_FILE -d file:///sdcard/1231.jpg

        String jsonString = Utils.FileUtils.readFile(FileUtils.createJsonFile(deviceId));
        ProductInfoBean[] productInfoBeans = new Gson().fromJson(jsonString, ProductInfoBean[].class);

//        for (browsers.config.ProductInfoBean bean :
//                productInfoBeans) {
        ProductInfoBean bean = productInfoBeans[0];
            List<String> imgSrcUrls = bean.getImgSrcUrls();
            List<File> newImagSrcUrls = new ArrayList<>();
            for (String imageSrc :
                    imgSrcUrls) {
                String[] split = imageSrc.split("/");
                File imageFile = FileUtils.downloadPicture(
                        imageSrc,
                        FileUtils.DIR_PATH_IMG + split[split.length - 1]
                );
                if (imageFile != null) {
                    newImagSrcUrls.add(imageFile);
                }

            }

            UIPostBean uiPostBean = new UIPostBean(
                    bean.getProductName(),
                    "",
                    newImagSrcUrls,
                    findFengCi(bean.getProductName()),
                    bean.getPriceCurrent() + (bean.getPriceOld() - bean.getPriceCurrent()) / 2 + "",
                    bean.getPriceOld() + "");


        iFishAppProcess.toMainActivity(adbProcess, deviceId)
                .toPostProductActivity(adbProcess, deviceId)
                .postProductByOrder(adbProcess, deviceId, uiPostBean);

//        }


    }


    public static void tagTest() {
        String jsonFilePath = System.getProperty("user.home") + File.separator + "Documents" + File.separator + "Java.txt";

        String jsonString = Utils.FileUtils.readFile(jsonFilePath);
        ProductInfoBean[] productInfoBeans = new Gson().fromJson(jsonString, ProductInfoBean[].class);
        List<ProductInfoBean> productInfoBeans1 = Arrays.asList(productInfoBeans);
        for (ProductInfoBean bean :
                productInfoBeans1) {
            List<String> fengCi = findFengCi(bean.getProductName());
            MLog.logi(bean.getProductName() + "____" + fengCi);
        }
    }
}