package com.myorg;

import software.amazon.awscdk.core.App;

import java.util.Arrays;

public class CdkCodebuildExampleApp {
    public static void main(final String[] args) {
        App app = new App();

        new CdkCodebuildExampleStack(app, "CdkCodebuildExampleStack");

        app.synth();
    }
}
