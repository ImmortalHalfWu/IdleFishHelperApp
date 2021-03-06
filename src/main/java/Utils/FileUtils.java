package Utils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * <p>文件操作工具类<p>
 */
@SuppressWarnings({"resource","unused"})
public class FileUtils {


    private final static String DIR_PATH_ROOT = System.getProperty("user.dir");
    public final static String DIR_PATH_IMG = DIR_PATH_ROOT + File.separator + "imgs" + File.separator;
    public final static String DIR_PATH_XML = DIR_PATH_ROOT + File.separator + "xmls" + File.separator;
    public final static String DIR_PATH_DEVICE = DIR_PATH_ROOT + File.separator + "devices" + File.separator;
    public final static String DIR_PATH_JSON = DIR_PATH_ROOT + File.separator + "jsons" + File.separator;
    public final static String DIR_PATH_WEB = DIR_PATH_ROOT + File.separator + "webs" + File.separator;



    public final static String FILE_NAME_NEW_PRODUCT_JSON = "NewProductJson.json";
    public final static String FILE_NAME_POSTED_PRODUCT_JSON = "PostedProductJson.json";


    public static void init() {
        mkDir(DIR_PATH_IMG);
        mkDir(DIR_PATH_XML);
        mkDir(DIR_PATH_JSON);
        mkDir(DIR_PATH_WEB);
        mkDir(DIR_PATH_DEVICE);
    }

    /**
     * 获取windows/linux的项目根目录
     * @return
     */
    public static String getConTextPath(){
        String fileUrl = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        if("usr".equals(fileUrl.substring(1,4))){
            fileUrl = (fileUrl.substring(0,fileUrl.length()-16));//linux
        }else{
            fileUrl = (fileUrl.substring(1,fileUrl.length()-16));//windows
        }
        return fileUrl;
    }

    /**
     * 字符串转数组
     * @param str 字符串
     * @param splitStr 分隔符
     * @return
     */
    public static String[] StringToArray(String str,String splitStr){
        String[] arrayStr = null;
        if(!"".equals(str) && str != null){
            if(str.contains(splitStr)){
                arrayStr = str.split(splitStr);
            }else{
                arrayStr = new String[1];
                arrayStr[0] = str;
            }
        }
        return arrayStr;
    }






    /**
     * @param deviceId 设备id
     * @return 设备对应的设备数据文件路径
     */
    public static String createDeviceDir(String deviceId) {
        String s = DIR_PATH_DEVICE + deviceId + File.separator;
        mkDir(s);
        return s;
    }

//    /**
//     * @param deviceId 设备id
//     * @return 设备对应的设备数据文件路径
//     */
//    public static String createDeviceXMLDir(String deviceId) {
//        String deviceInfoFile = createDeviceDir(deviceId);
//        String s = deviceInfoFile + "xmls" + File.separator;
//        mkDir(s);
//        return s;
//    }
//
//
//    /**
//     * @param deviceId 设备id
//     * @return 设备对应的新商品数据文件路径
//     */
//    public static String createDeviceJsonDir(String deviceId) {
//        String deviceInfoFile = createDeviceDir(deviceId);
//        String s = deviceInfoFile + "jsons" + File.separator;
//        mkDir(s);
//        return s;
//    }
//
//    /**
//     * @param deviceId 文件名称
//     * @return 设备对应的新商品数据文件路径
//     */
//    public static String createDeviceImgsDir(String deviceId) {
//        String deviceInfoFile = createDeviceDir(deviceId);
//        String s = deviceInfoFile + "imgs" + File.separator;
//        mkDir(s);
//        return s;
//    }


    /**
     * @param deviceId 文件名称
     * @return 设备对应的新商品数据文件路径
     */
    public static String createDeviceConfigFile(String deviceId) {
        String deviceInfoFile = createDeviceDir(deviceId);
        String s = deviceInfoFile + "config" + ".json";
        mkFile(s);
        return s;
    }

    /**
     * @param fileName 文件名称
     * @return 设备对应的设备数据文件路径
     */
    public static String createXMLFile(String fileName) {
        mkFile(DIR_PATH_XML + fileName);
        return DIR_PATH_XML + fileName;
    }


    /**
     * @param fileName 文件名称
     * @return 设备对应的新商品数据文件路径
     */
    public static String createJsonFile(String fileName) {
        mkFile(DIR_PATH_JSON + fileName);
        return DIR_PATH_JSON + fileName;
    }

    /**
     * @param fileName 文件名称
     * @return 设备对应的新商品数据文件路径
     */
    public static String createImgFile(String fileName) {
        mkFile(DIR_PATH_IMG + fileName);
        return DIR_PATH_IMG + fileName;
    }



//    mkDir(DIR_PATH_IMG);
//    mkDir(DIR_PATH_XML);
//    mkDir(DIR_PATH_JSON);
//    mkDir(DIR_PATH_WEB);
//    mkDir(DIR_PATH_DEVICE);














    /**
     * @param deviceId 设备id
     * @return 设备对应的web文件路径
     */
    public static String createWebCacheDir(String deviceId) {
        String s = DIR_PATH_WEB + deviceId + File.separator;
        mkDir(s);
        return s;
    }

    /**
     * @param deviceId 设备id
     * @return 设备对应的已经发布商品数据文件路径
     */
    public static String createPostedProductInfoFile(String deviceId) {
        String s = DIR_PATH_JSON + deviceId + FILE_NAME_POSTED_PRODUCT_JSON;
        mkFile(s);
        return s;
    }

    /**
     * 读取文件
     *
     * @param Path
     * @return
     */
    public static String readFile(String Path) {
        BufferedReader reader = null;
        String laststr = "";
        try {
            FileInputStream fileInputStream = new FileInputStream(Path);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
            reader = new BufferedReader(inputStreamReader);
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                laststr += tempString;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return laststr;
    }

    /**
     * 获取文件夹下所有文件的名称 + 模糊查询（当不需要模糊查询时，queryStr传空或null即可）
     * 1.当路径不存在时，map返回retType值为1
     * 2.当路径为文件路径时，map返回retType值为2，文件名fileName值为文件名
     * 3.当路径下有文件夹时，map返回retType值为3，文件名列表fileNameList，文件夹名列表folderNameList
     * @param folderPath 路径
     * @param queryStr 模糊查询字符串
     * @return
     */
    public static HashMap<String, Object> getFilesName(String folderPath , String queryStr) {
        HashMap<String, Object> map = new HashMap<>();
        List<String> fileNameList = new ArrayList<>();//文件名列表
        List<String> folderNameList = new ArrayList<>();//文件夹名列表
        File f = new File(folderPath);
        if (!f.exists()) { //路径不存在
            map.put("retType", "1");
        }else{
            boolean flag = f.isDirectory();
            if(flag==false){ //路径为文件
                map.put("retType", "2");
                map.put("fileName", f.getName());
            }else{ //路径为文件夹
                map.put("retType", "3");
                File fa[] = f.listFiles();
                queryStr = queryStr==null ? "" : queryStr;//若queryStr传入为null,则替换为空（indexOf匹配值不能为null）
                for (int i = 0; i < fa.length; i++) {
                    File fs = fa[i];
                    if(fs.getName().indexOf(queryStr)!=-1){
                        if (fs.isDirectory()) {
                            folderNameList.add(fs.getName());
                        } else {
                            fileNameList.add(fs.getName());
                        }
                    }
                }
                map.put("fileNameList", fileNameList);
                map.put("folderNameList", folderNameList);
            }
        }
        return map;
    }

    /**
     * 以行为单位读取文件，读取到最后一行
     * @param filePath
     * @return
     */
    public static List<String> readFileContent(String filePath) {
        BufferedReader reader = null;
        List<String> listContent = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(filePath));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                listContent.add(tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return listContent;
    }

    /**
     * 读取指定行数据 ，注意：0为开始行
     * @param filePath
     * @param lineNumber
     * @return
     */
    public static String readLineContent(String filePath,int lineNumber){
        BufferedReader reader = null;
        String lineContent="";
        try {
            reader = new BufferedReader(new FileReader(filePath));
            int line=0;
            while(line<=lineNumber){
                lineContent=reader.readLine();
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        return lineContent;
    }

    /**
     * 读取从beginLine到endLine数据（包含beginLine和endLine），注意：0为开始行
     * @param filePath
     * @param beginLineNumber 开始行
     * @param endLineNumber 结束行
     * @return
     */
    public static List<String> readLinesContent(String filePath,int beginLineNumber,int endLineNumber){
        List<String> listContent = new ArrayList<>();
        try{
            int count = 0;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String content = reader.readLine();
            while(content !=null){
                if(count >= beginLineNumber && count <=endLineNumber){
                    listContent.add(content);
                }
                content = reader.readLine();
                count++;
            }
        } catch(Exception e){
        }
        return listContent;
    }

    /**
     * 读取若干文件中所有数据
     * @param listFilePath
     * @return
     */
    public static List<String> readFileContent_list(List<String> listFilePath) {
        List<String> listContent = new ArrayList<>();
        for(String filePath : listFilePath){
            File file = new File(filePath);
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(file));
                String tempString = null;
                int line = 1;
                // 一次读入一行，直到读入null为文件结束
                while ((tempString = reader.readLine()) != null) {
                    listContent.add(tempString);
                    line++;
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e1) {
                    }
                }
            }
        }
        return listContent;
    }

    /**
     * 文件数据写入（如果文件夹和文件不存在，则先创建，再写入）
     * @param filePath
     * @param content
     * @param flag true:如果文件存在且存在内容，则内容换行追加；false:如果文件存在且存在内容，则内容替换
     */
    public static String fileLinesWrite(String filePath,String content,boolean flag){
        String filedo = "write";
        FileWriter fw = null;
        try {
            File file=new File(filePath);
            //如果文件夹不存在，则创建文件夹
            if (!file.getParentFile().exists()){
                file.getParentFile().mkdirs();
            }
            if(!file.exists()){//如果文件不存在，则创建文件,写入第一行内容
                file.createNewFile();
                fw = new FileWriter(file);
                filedo = "create";
            }else{//如果文件存在,则追加或替换内容
                fw = new FileWriter(file, flag);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter pw = new PrintWriter(fw);
        pw.println(content);
        pw.flush();
        try {
            fw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filedo;
    }


    /*写入Text文件操作*/
    public static void writeText(String filePath, String content,boolean isAppend) {
        FileOutputStream outputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = new FileOutputStream(filePath,isAppend);
            outputStreamWriter = new OutputStreamWriter(outputStream);
            bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(bufferedWriter != null){
                    bufferedWriter.close();
                }
                if (outputStreamWriter != null){
                    outputStreamWriter.close();
                }
                if (outputStream != null){
                    outputStream.close();
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 写文件
     * @param ins
     * @param out
     */
    public static void writeIntoOut(InputStream ins, OutputStream out) {
        byte[] bb = new byte[10 * 1024];
        try {
            int cnt = ins.read(bb);
            while (cnt > 0) {
                out.write(bb, 0, cnt);
                cnt = ins.read(bb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.flush();
                ins.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断list中元素是否完全相同（完全相同返回true,否则返回false）
     * @param list
     * @return
     */
    private static boolean hasSame(List<? extends Object> list){
        if(null == list)
            return false;
        return 1 == new HashSet<Object>(list).size();
    }

    /**
     * 判断list中是否有重复元素（无重复返回true,否则返回false）
     * @param list
     * @return
     */
    private static boolean hasSame2(List<? extends Object> list){
        if(null == list)
            return false;
        return list.size() == new HashSet<Object>(list).size();
    }

    /**
     * 增加/减少天数
     * @param date
     * @param num
     * @return
     */
    public static Date DateAddOrSub(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
    //https://www.cnblogs.com/chenhuan001/p/6575053.html
    /**
     * 递归删除文件或者目录
     * @param file_path
     */
    public static void deleteEveryThing(String file_path) {
        try{
            File file=new File(file_path);
            if(!file.exists()){
                return ;
            }
            if(file.isFile()){
                file.delete();
            }else{
                File[] files = file.listFiles();
                for(int i=0;i<files.length;i++){
                    String root=files[i].getAbsolutePath();//得到子文件或文件夹的绝对路径
                    deleteEveryThing(root);
                }
                file.delete();
            }
        } catch(Exception e) {
            System.out.println("删除文件失败");
        }
    }
    /**
     * 创建目录
     * @param dir_path
     */
    public static void mkDir(String dir_path) {
        File myFolderPath = new File(dir_path);
        try {
            if (!myFolderPath.exists()) {
                myFolderPath.mkdir();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 创建目录
     */
    public static void mkFile(String file_path) {
        File myFolderPath = new File(file_path);
        try {
            if (!myFolderPath.exists()) {
                boolean newFile = myFolderPath.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("新建目录操作出错");
            e.printStackTrace();
        }
    }

    /**
     * 判断指定的文件是否存在。
     *
     * @param fileName
     * @return
     */
    public static boolean isFileExist(String fileName) {
        return new File(fileName).isFile();
    }

    /**
     * 得到文件后缀名
     * @param fileName
     * @return
     */
    public static String getFileExt(String fileName) {
        int point = fileName.lastIndexOf('.');
        int length = fileName.length();
        if (point == -1 || point == length - 1) {
            return "";
        } else {
            return fileName.substring(point + 1, length);
        }
    }

    /**
     * 删除文件夹及其下面的子文件夹
     *
     * @param dir
     * @throws IOException
     */
    public static void deleteDir(File dir) throws IOException {
        if (dir.isFile())
            throw new IOException("IOException -> BadInputException: not a directory.");
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (file.isFile()) {
                    file.delete();
                } else {
                    deleteDir(file);
                }
            }
        }
        dir.delete();
    }

    /**
     * 复制文件
     *
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copy(File src, File dst) throws Exception {
        int BUFFER_SIZE = 4096;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst), BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            int len = 0;
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                in = null;
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out = null;
            }
        }
    }

    //链接url下载图片
    public static File downloadPicture(String urlList,String path) {
        URL url = null;
        try {
            url = new URL(urlList);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());

            File file = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ByteArrayOutputStream output = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int length;

            while ((length = dataInputStream.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
            fileOutputStream.write(output.toByteArray());
            dataInputStream.close();
            fileOutputStream.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

        public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

}