package com.myself.fragmentabhost;

/**
 * @author 小码哥Android学院(520it.com)
 * @time 2016/10/19  9:15
 * @desc ${TODD}
 */
public class ShowTabEvent {
    boolean isShow = false;

    public ShowTabEvent(boolean isShow) {
        this.isShow = isShow;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }
}
