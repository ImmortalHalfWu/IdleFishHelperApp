import Utils.FileUtils;
import browsers.MyBrowser;
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
                new MainActivity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private final static String URL_MAIN = "http://baicai.manmanbuy.com/Default.aspx?PageID=0";
    private Browser browser;

    private MainActivity() {
        init();
    }

    private void init() {

        JFrame frame = new JFrame();
        frame.getContentPane().setEnabled(false);
        final String title = "title";



        //窗体大小
        frame.setSize(750, 650);
        // 固定大小不可改
        frame.setResizable(false);
        // 标题
        frame.setTitle(title);
        frame.getContentPane().setLayout(null);

        frame.setExtendedState(JFrame.MAXIMIZED_VERT);
        frame.setLocationByPlatform(true);



        browser = MyBrowser.instance().getBrowser();
        BrowserView view = new BrowserView(browser);

        view.setBounds(0, 0, 600, 550);

        frame.getContentPane().add(view);

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
