package main;

import Utils.FileUtils;
import Utils.ThreadPoolManager;
import controls.DeviceDataControl;

public class InitApplication implements Runnable {

    private static InitApplication initApplication;

    public static InitApplication instance() {
        if (initApplication == null) {
            synchronized (InitApplication.class) {
                if (initApplication == null) {
                    initApplication = new InitApplication();
                }
            }
        }
        return initApplication;
    }


    private InitListener listener;

    @Override
    public void run() {

        FileUtils.init();
        ThreadPoolManager.init();
        DeviceDataControl.init();

        if (listener != null) {
            listener.applicationInitSuc();
        }
    }

    public InitApplication setListener(InitListener listener) {
        this.listener = listener;
        return this;
    }

    public interface InitListener {
        void applicationInitSuc();
    }

}
