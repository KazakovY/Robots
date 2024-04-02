package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;
// import java.awt.event.ComponentAdapter;
// import java.awt.event.ComponentEvent;
// import java.io.File;
// import java.io.FileInputStream;
// import java.io.FileOutputStream;
// import java.io.IOException;
// import java.util.Properties;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;

import log.LogChangeListener;
import log.LogEntry;
import log.LogWindowSource;


public class LogWindow extends JInternalFrame implements LogChangeListener {
    private LogWindowSource m_logSource;
    private TextArea m_logContent;
    // private Properties windowProperties;

    public LogWindow(LogWindowSource logSource) {
        super("Протокол работы", true, true, true, true);
        m_logSource = logSource;
        m_logSource.registerListener(this);
        m_logContent = new TextArea("");
        m_logContent.setSize(200, 500);

        // loadWindowProperties();
        // addComponentListener(new ComponentAdapter() {
        //     @Override
        //     public void componentMoved(ComponentEvent e) {
        //         saveWindowProperties();
        //     }

        //     @Override
        //     public void componentResized(ComponentEvent e) {
        //         saveWindowProperties();
        //     }

        //     @Override
        //     public void componentShown(ComponentEvent e) {
        //         saveWindowProperties();
        //     }

        //     @Override
        //     public void componentHidden(ComponentEvent e) {
        //         saveWindowProperties();
        //     }
        // });

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_logContent, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
        // restoreWindowProperties();
        updateLogContent();
    }

    private void updateLogContent() {
        StringBuilder content = new StringBuilder();
        for (LogEntry entry : m_logSource.all()) {
            content.append(entry.getMessage()).append("\n");
        }
        m_logContent.setText(content.toString());
        m_logContent.invalidate();
    }

    // private void loadWindowProperties() {
    //     windowProperties = new Properties();
    //     try {
    //         File file = new File(System.getProperty("user.home"), "window.properties");
    //         if (file.exists()) {
    //             FileInputStream fis = new FileInputStream(file);
    //             windowProperties.load(fis);
    //             fis.close();
    //         }
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private void saveWindowProperties() {
    //     windowProperties.setProperty("x", String.valueOf(getX()));
    //     windowProperties.setProperty("y", String.valueOf(getY()));
    //     windowProperties.setProperty("width", String.valueOf(getWidth()));
    //     windowProperties.setProperty("height", String.valueOf(getHeight()));
    //     windowProperties.setProperty("isMaximized", String.valueOf(isMaximum()));

    //     try {
    //         File file = new File(System.getProperty("user.home"), "window.properties");
    //         FileOutputStream fos = new FileOutputStream(file);
    //         windowProperties.store(fos, "Window Properties");
    //         fos.close();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    // }

    // private void restoreWindowProperties() {
    //     if (windowProperties != null) {
    //         int x = Integer.parseInt(windowProperties.getProperty("x", "0"));
    //         int y = Integer.parseInt(windowProperties.getProperty("y", "0"));
    //         int width = Integer.parseInt(windowProperties.getProperty("width", "200"));
    //         int height = Integer.parseInt(windowProperties.getProperty("height", "500"));
    //         // boolean isMaximized = Boolean.parseBoolean(windowProperties.getProperty("isMaximized", "false"));

    //         setLocation(x, y);
    //         setSize(width, height);
    //         // if (isMaximized) {
    //         //     setMaximum(true);
    //         // }
    //     }
    // }
    @Override
    public void onLogChanged() {
        EventQueue.invokeLater(this::updateLogContent);
    }
}