package gui;

import javax.swing.*;

enum DisplayMode {
    CROSS_PLATFORM(UIManager.getSystemLookAndFeelClassName(), "Универсальная схема"),
    SYSTEM(UIManager.getCrossPlatformLookAndFeelClassName(), "Системная схема");

    public final String className;
    public final String description;

    DisplayMode(String className, String description) {
        this.className = className;
        this.description = description;
    }
}