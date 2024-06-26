package gui;
 
 
import log.Logger;
 
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.*;
 
import javax.swing.*;
 
 
/**
 * Что требуется сделать:
 * 1. Метод создания меню перегружен функционалом и трудно читается. 
 * Следует разделить его на серию более простых методов (или вообще выделить отдельный класс).
 *
 */
public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
 
    public MainApplicationFrame() {
        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                screenSize.width  - inset * 2,
                screenSize.height - inset * 2
        );
 
        setContentPane(desktopPane);
 
 
        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);
 
        GameWindow gameWindow = new GameWindow();
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);
 
        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        WindowAdapter windowAdapter = new WindowAdapter() {
            public  void windowClosing(WindowEvent windowEvent) {
               exit();
            }
        };
        addWindowListener(windowAdapter);
    }
 
 
    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());	
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }
 
    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }
 
    private JMenuItem createLookAndFeelMenuItem(DisplayMode displayMode){
        JMenuItem menuItem = new JMenuItem(displayMode.description, KeyEvent.VK_S);
        menuItem.addActionListener((event) -> {
            setLookAndFeel(displayMode.className);
            this.invalidate();
        });
        return menuItem;
    }
 
    private JMenu createLookAndFeelMenu(){
        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(
                "Управление режимом отображения приложения");
 
        lookAndFeelMenu.add(createLookAndFeelMenuItem(DisplayMode.SYSTEM));
        lookAndFeelMenu.add(createLookAndFeelMenuItem(DisplayMode.CROSS_PLATFORM));
        return lookAndFeelMenu;
    }
 
    private JMenu createTestMenu(){
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые команды");
 
        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        testMenu.add(addLogMessageItem);
        return testMenu;
    }
 
    private void exit() {
        if (JOptionPane.showConfirmDialog(desktopPane,
                "Вы уверены, что хотите закрыть это окно?", "Закрыть окно?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == 0) {
            System.exit(0);
        }
    }
 
    private JMenu createExit() {
        JMenu createExit = new JMenu("Выйти");
        JButton exit = new JButton("Выход");
 
        exit.addActionListener(event -> exit());
        createExit.add(exit);
        return  createExit;
    }
 
    private JMenuBar generateMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createLookAndFeelMenu());
        menuBar.add(createTestMenu());
        menuBar.add(createExit());
        return menuBar;
    }
 
    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
               | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}