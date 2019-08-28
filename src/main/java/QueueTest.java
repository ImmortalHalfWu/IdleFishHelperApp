import com.teamdev.jxbrowser.chromium.Browser;

import java.util.concurrent.LinkedBlockingDeque;

public class QueueTest {

    static LinkedBlockingDeque<Runnable> browsers = new LinkedBlockingDeque<>();
    static LinkedBlockingDeque<Runnable> runnables = new LinkedBlockingDeque<>();

    public static void test() {


        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("查找Browsers");
                        Runnable take = browsers.take();
                        System.out.println("找到Browsers并运行");
                        take.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        System.out.println("查找runnables");
                        Runnable run = runnables.take();
                        System.out.println("找到runnables并运行");
                        run.run();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();


        new Thread() {
            @Override
            public void run() {
                super.run();

                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        System.out.println("添加runnable" + i);
                        int finalI = i;
                        runnables.put(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("runnable运行" + finalI);
                                try {
                                    System.out.println("添加browser" + finalI);
                                    browsers.put(new Runnable() {
                                        @Override
                                        public void run() {
                                            System.out.println("browser运行" + finalI);
                                        }
                                    });
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();

    }




}
