package net.edshin09.browser;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.print.DocFlavor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class HelloApplication extends JFrame {
    private int pX, pY;
    private JTextField addressBar;
    private WebEngine webEngine;

    public HelloApplication() {
        setIconImage(new ImageIcon("src/main/resources/images/KIWOOM_SPECIAL.png").getImage());
        setTitle("Edshin Browser");
        // 타이틀 바 제거
        setUndecorated(true);


        // 크롬 느낌의 디자인 적용
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        contentPane.setBackground(Color.WHITE);
        setContentPane(contentPane);

        // 커스텀 타이틀 바 생성
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new BoxLayout(titleBar, BoxLayout.X_AXIS));
        titleBar.setBackground(Color.DARK_GRAY);
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        // 드래그로 창 이동 가능하게 설정
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                pX = e.getX();
                pY = e.getY();
            }
        });

        titleBar.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                setLocation(getLocation().x + e.getX() - pX, getLocation().y + e.getY() - pY);
            }
        });

        // 주소 입력창
        addressBar = new JTextField("https://www.example.com");
        addressBar.setPreferredSize(new Dimension(400, 30));
        addressBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadAddress();
            }
        });

        // 닫기 버튼
        JButton closeButton = createTitleBarButton("",Color.RED,Color.getHSBColor(0F,1,0.6F));
        closeButton.addActionListener(e -> System.exit(0));

        // 최소화 버튼
        JButton minimizeButton = createTitleBarButton("",Color.ORANGE,Color.getHSBColor(20F,1,0.8F));
        minimizeButton.addActionListener(e -> setState(JFrame.ICONIFIED));

        // 최대화/복구 버튼
        JButton maximizeButton = createTitleBarButton("",Color.GREEN,Color.getHSBColor(131F,1,0.6F));
        maximizeButton.addActionListener(e -> {
            if (getExtendedState() != JFrame.MAXIMIZED_BOTH) {
                setExtendedState(JFrame.MAXIMIZED_BOTH);
            } else {
                setExtendedState(JFrame.NORMAL);
            }
        });

        titleBar.add(Box.createHorizontalGlue());
        titleBar.add(addressBar);
        titleBar.add(closeButton);
        titleBar.add(minimizeButton);
        titleBar.add(maximizeButton);



        contentPane.add(titleBar, BorderLayout.NORTH);

        // JavaFX WebView 사용
        // JavaFX WebView 사용
        JFXPanel jfxPanel = new JFXPanel();
        contentPane.add(jfxPanel, BorderLayout.CENTER);

        Platform.runLater(() -> {
            WebView webView = new WebView();
            webEngine = webView.getEngine();
            webEngine.load(addressBar.getText());

            webEngine.titleProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    SwingUtilities.invokeLater(() -> setTitle(newValue+"-Edshin Browser"));
                }
            });


            Scene scene = new Scene(webView);
            jfxPanel.setScene(scene);
        });

        // 프레임 크기 및 기본 종료 동작 설정
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void loadAddress() {
        Platform.runLater(() -> {
            if(addressBar.getText().equals("https://vesta.net") || addressBar.getText().equals("http://vesta.net") || addressBar.getText().equals("vesta.net")) {
                webEngine.load("172.30.1.93/page/page.html");
            } else if(addressBar.getText().equals("hjjsadjeqw8udsdhwb") || addressBar.getText().equals("hweweedsdacttp://vesta.net") || addressBar.getText().equals("vesta.asdnetesesd")) {
                //EDIT
            } else {
                webEngine.load(addressBar.getText());
            }

        });
    }

    private JButton createTitleBarButton(String text, Color defaultColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(defaultColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(30, 30));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(defaultColor);
            }
        });
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            HelloApplication frame = new HelloApplication();
            frame.setVisible(true);
        });
    }
}
