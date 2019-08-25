package androids.adbs;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ADBBuilder implements IADBBuilder {


    private final List<ADBMessageBean> adbMessageBeans;

    public ADBBuilder() {
        adbMessageBeans = new LinkedList<>();
    }

    @Override
    public IADBBuilder addClick(Point point) {
        if (point != null) {
            adbMessageBeans.add(ADBMessageBean.instanceClick(point));
        }
        return this;
    }

    @Override
    public IADBBuilder addClick(Point point, int offsetX, int offsetY) {
        if (point != null) {
            point.x += offsetX;
            point.y += offsetY;
        }
        return addClick(point);
    }

    @Override
    public IADBBuilder addClick(Point[] points) {
        for (Point point :
                points) {
            if (point != null) {
                adbMessageBeans.add(ADBMessageBean.instanceClick(point));
            }

        }
        return this;
    }

    @Override
    public IADBBuilder addSwipe(Point start, Point end, int time) {
        if (start != null && end != null) {
            adbMessageBeans.add(ADBMessageBean.instsanceSwipe(start, end, time));
        }
        return this;
    }

    @Override
    public IADBBuilder addText(String text) {
        if (text != null) {
            adbMessageBeans.add(ADBMessageBean.instanceText(text));
        }
        return this;
    }

    @Override
    public IADBBuilder addBackClick() {
        adbMessageBeans.add(ADBMessageBean.instanceBackClick());
        return this;
    }

    @Override
    public IADBBuilder addCallBack(Runnable callBack) {
        if (callBack != null) {
            adbMessageBeans.add(ADBMessageBean.instanceCallBack(callBack));
        }
        return this;
    }

    @Override
    public void send(String deviceId, IADBProcess iadbProcess) throws Exception {
        for (ADBMessageBean adbMessage :
                adbMessageBeans) {
            switch (adbMessage.mType) {
                case ADBMessageBean.TYPE_BACK_CLICK:
                    iadbProcess.adbSendBackKeyEvent(deviceId);
                    break;
                case ADBMessageBean.TYPE_CLICK:
                    iadbProcess.adbInputTap(deviceId, adbMessage.pointStart.x, adbMessage.pointStart.y);
                    break;
                case ADBMessageBean.TYPE_SWIPE:
                    iadbProcess.adbSwipe(
                            deviceId,
                            adbMessage.pointStart.x, adbMessage.pointStart.y,
                            adbMessage.pointEnd.x, adbMessage.pointEnd.y,
                            adbMessage.swipeTime
                    );
                    break;
                case ADBMessageBean.TYPE_TEXT:
                    iadbProcess.adbInputText(deviceId, adbMessage.text);
                    break;
                case ADBMessageBean.TYPE_CALL_BACK:
                    try {
                        adbMessage.callback.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    private static final class ADBMessageBean {

        final static int TYPE_CLICK = 1;
        final static int TYPE_SWIPE = 2;
        final static int TYPE_TEXT = 3;
        final static int TYPE_BACK_CLICK = 4;
        final static int TYPE_CALL_BACK = 5;

        private int mType;

        private Point pointStart;
        private Point pointEnd;
        private int swipeTime;

        private String text;

        private Runnable callback;



        private ADBMessageBean(Runnable run) {
            this(TYPE_CALL_BACK, null, null, 0);
            this.callback = run;
        }
        private ADBMessageBean(String text) {
            this(TYPE_TEXT, null, null, 0);
            this.text = text;
        }
        private ADBMessageBean(Point point) {
            this(TYPE_CLICK, point, null, 0);
        }
        private ADBMessageBean(int type, Point pointStart, Point pointEnd, int swipeTime) {
            mType = type;
            this.pointStart = pointStart;
            this.pointEnd = pointEnd;
            this.swipeTime = swipeTime;
        }

        static ADBMessageBean instanceClick(Point point) {
            return new ADBMessageBean(point);
        }

        static ADBMessageBean instsanceSwipe(Point pointStart, Point pointEnd, int swipeTime) {
            return new ADBMessageBean(TYPE_SWIPE, pointStart, pointEnd, swipeTime);
        }

        static ADBMessageBean instanceText(String text) {
            return new ADBMessageBean(text);
        }

        static ADBMessageBean instanceBackClick() {
            return new ADBMessageBean(TYPE_BACK_CLICK, null, null, 0);
        }

        static ADBMessageBean instanceCallBack(Runnable runnable) {
            return new ADBMessageBean(runnable);
        }

    }

}
