package com.psu.testclient;

import org.apache.log4j.Logger;

public class Launcher {
    private static final Logger log = Logger.getLogger(Launcher.class);

    public static void main(String[] args) {
        log.info("App started");

        ClientApplication.main(args);
    }
}
