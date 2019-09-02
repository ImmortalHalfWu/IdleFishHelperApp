package browsers.impls.yhqAndPic;

import browsers.beans.ProductInfoBean;

import java.util.*;

public class TaoBaoTmallYHQImagManager {

    private static TaoBaoTmallYHQImagManager instance;

    public static TaoBaoTmallYHQImagManager getInstance() {
        if (instance == null) {
            synchronized (TaoBaoTmallYHQImagManager.class) {
                if (instance == null) {
                    instance = new TaoBaoTmallYHQImagManager();
                }
            }
        }
        return instance;
    }


    private Map<Object, List<TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener>> findYHQPicMap;

    private TaoBaoTmallYHQImagManager() {
        this.findYHQPicMap = Collections.synchronizedMap(new HashMap<>());
    }

    public void startFindYHQAndPIC(List<ProductInfoBean> productInfoBeans, Object tag, TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener listener) {

        if (findYHQPicMap.containsKey(tag)) {
            findYHQPicMap.get(tag).add(listener);
        } else {
            List<TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener> taoBaoTmallYHQImagListeners = new ArrayList<>();
            taoBaoTmallYHQImagListeners.add(listener);
            findYHQPicMap.put(tag, taoBaoTmallYHQImagListeners);
            TaoBaoTmallYHQImagProcess.startFindYHQAndPIC(productInfoBeans, tag, taoBaoTmallYHQImagListener);
        }

    }

    private TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener taoBaoTmallYHQImagListener = (allProduct, tag) -> {

        if (findYHQPicMap.containsKey(tag)) {

            List<TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener> taoBaoTmallYHQImagListeners = findYHQPicMap.get(tag);

            for (TaoBaoTmallYHQImagProcess.TaoBaoTmallYHQImagListener listener : taoBaoTmallYHQImagListeners) {
                try {
                    listener.loadOver(allProduct, tag);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };

}
