package com.xite.core;

import com.codeborne.selenide.ElementsCollection;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;

import java.util.Arrays;
import java.util.List;

//public class CustomConditions {
//
//    public static ExpectedCondition<WebElement> listElementWithCssClass(final ElementsCollection elementsListLocator, final String cssClass) {
//
//        return new ExpectedCondition<WebElement>() {
//            private List<WebElement> elementsList;
//
//            public WebElement apply(WebDriver driver) {
//                elementsList = driver.findElements(elementsListLocator);
//                for (int i = 0; i < elementsList.size(); i++) {
//                    if (Arrays.asList(StringUtils.split(elementsList.get(i).getAttribute("class"))).contains(cssClass)) {
//                        return elementsList.get(i);
//                    }
//                }
//                return null;
//            }
//
//            public String toString() {
//                return String.format("\nFor list with locator %s \nlooking for CSS class: %s", elementsListLocator, cssClass);
//            }
//        };
//    }
//}
