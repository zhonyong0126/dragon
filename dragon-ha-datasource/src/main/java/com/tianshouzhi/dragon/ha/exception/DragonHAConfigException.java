package com.tianshouzhi.dragon.ha.exception;

import com.tianshouzhi.dragon.common.exception.DragonConfigException;

/**
 * Created by tianshouzhi on 2017/8/3.
 */
public class DragonHAConfigException extends DragonHAException {
    public DragonHAConfigException(String reason) {
        super(reason);
    }

    public DragonHAConfigException(InterruptedException e) {
        super(e);
    }

    public DragonHAConfigException(String message, Exception e) {
        super(message, e);
    }
}