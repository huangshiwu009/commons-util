package com.perfecton.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description:
 * Author: HuangShiwu
 * Date:   2019/9/2 20:25
 */
public class Main {

    private final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("Hello, {}", "Info");
        logger.warn("Hello, {}", "Warn");
        logger.error("Hello, {}", "Error");
    }
}
