package ru.netology.util;

import com.codeborne.selenide.Screenshots;
import com.codeborne.selenide.ex.UIAssertionError;
import com.codeborne.selenide.impl.ScreenShotLaboratory;
import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.util.Optional;

import static com.codeborne.selenide.WebDriverRunner.driver;
import static com.codeborne.selenide.ex.ErrorMessages.screenshot;

/**
 * Use this class to automatically take screenshots in case of ANY errors in tests (not only Selenide errors) and send them to ReportPortal.
 *
 * @see com.codeborne.selenide.junit5.ScreenShooterExtension
 */
public class ScreenShooterReportPortalExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private static final Logger log = LoggerFactory.getLogger(ScreenShooterReportPortalExtension.class);

    private final boolean captureSuccessfulTests;

    public ScreenShooterReportPortalExtension() {
        this(false);
    }

    public ScreenShooterReportPortalExtension(final boolean captureSuccessfulTests) {
        this.captureSuccessfulTests = captureSuccessfulTests;
    }

    @Override
    public void beforeTestExecution(final ExtensionContext context) {
        final Optional<Class<?>> testClass = context.getTestClass();
        final String className = testClass.map(Class::getName).orElse("EmptyClass");

        final Optional<Method> testMethod = context.getTestMethod();
        final String methodName = testMethod.map(Method::getName).orElse("emptyMethod");

        Screenshots.startContext(className, methodName);
    }

// uncomment if needed screenshots only error
//    @Override
//    public void afterTestExecution(final ExtensionContext context) {
//        if (captureSuccessfulTests) {
//            log.info(screenshot(driver()));
//        } else {
//            context.getExecutionException().ifPresent(error -> {
//                if (!(error instanceof UIAssertionError)) {
//                    File screenshot = ScreenShotLaboratory.getInstance().takeScreenShotAsFile(driver());
//                    if (screenshot != null) {
//                        LoggingUtils.log(screenshot, "Attached screenshot");
//                    }
//                }
//            });
//        }
//        Screenshots.finishContext();
//    }

// commented this method if needed screenshots only error
    @Override
    public void afterTestExecution(final ExtensionContext context) {
        log.info(screenshot(driver()));
        File screenshot = ScreenShotLaboratory.getInstance().takeScreenShotAsFile(driver());
        LoggingUtils.log(screenshot, "Attached screenshot");

        Screenshots.finishContext();
    }
}