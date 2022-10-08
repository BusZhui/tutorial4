package main.com.buszh;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.swing.*;

public class TextFrame extends JFrame {
    public TextFrame() {
        super("Test Editor");

        JMenuBar mb = new JMenuBar();
        //在JFrame等容器中设置菜单栏对象，即将菜单栏添加到框架容器中
        this.setJMenuBar(mb);

        //创建菜单对象
        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");
        JMenu search = new JMenu("Search");
        JMenu view = new JMenu("View");
        JMenu manage = new JMenu("Manage");
        //将菜单添加到菜单栏中
        mb.add(file);
        mb.add(search);
        mb.add(view);
        mb.add(manage);
        mb.add(help);


        JTextArea workArea = new JTextArea();
        JScrollPane imgScrollPane = new JScrollPane(workArea);
        //add(workArea);
        add(imgScrollPane, BorderLayout.CENTER);

        //定义打开和保存对话框
        FileDialog openDia;
        FileDialog saveDia;
        //默认模式为 FileDialog.LOAD
        openDia = new FileDialog(this, "Open", FileDialog.LOAD);
        saveDia = new FileDialog(this, "Save", FileDialog.SAVE);

        //
        JMenuItem item1_1 = new JMenuItem("New");
        item1_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                workArea.setText("");//清空文本
            }
        });
        JMenuItem item1_2 = new JMenuItem("Open");
        item1_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openDia.setVisible(true);

                String dirPath = openDia.getDirectory();//获取文件路径
                String fileName = openDia.getFile();//获取文件名称

                //如果打开路径 或 目录为空 则返回空
                if (dirPath == null || fileName == null) {
                    return;
                }

                workArea.setText("");//清空文本

                File fileO = new File(dirPath, fileName);

                try {
                    BufferedReader bufr = new BufferedReader(new FileReader(fileO));

                    String line = null;

                    while ((line = bufr.readLine()) != null) {
                        workArea.append(line + "\r\n");
                    }

                    bufr.close(); //关闭文本
                } catch (IOException er1) {
                    throw new RuntimeException("read failure");
                }

            }
        });
        JMenuItem item1_3 = new JMenuItem("Save");
        item1_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File fileS = null;
                if (fileS == null) {
                    saveDia.setVisible(true);
                    String dirPath = saveDia.getDirectory();
                    String fileName = saveDia.getFile();

                    if (dirPath == null || fileName == null)
                        return;

                    fileS = new File(dirPath, fileName);
                }

                try {
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(fileS));
                    String text = workArea.getText();
                    bufw.write(text);
                    bufw.close();
                } catch (IOException er) {
                    throw new RuntimeException("fail to save");
                }
            }
        });
        JMenuItem item1_4 = new JMenuItem("Save");
        item1_4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                File fileS = null;
                if (fileS == null) {
                    saveDia.setVisible(true);
                    String dirPath = saveDia.getDirectory();
                    String fileName = saveDia.getFile();

                    if (dirPath == null || fileName == null)
                        return;

                    fileS = new File(dirPath, fileName);
                }

                try {
                    BufferedWriter bufw = new BufferedWriter(new FileWriter(fileS));
                    String text = workArea.getText();
                    bufw.write(text);
                    bufw.close();
                } catch (IOException er) {
                    throw new RuntimeException("fail to save！");
                }
            }
        });
        JMenuItem item1_5 = new JMenuItem("Quit");
        item1_5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });




        //在菜单中添加菜单项
        file.add(item1_1);
        file.add(item1_2);
        file.add(item1_3);
        file.add(item1_4);
        file.add(item1_5);

        JMenuItem item1_6 = new JMenuItem("Search");
        item1_6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SearchText(TextFrame.this,workArea);
            }
        });
        search.add(item1_6);


    }
    public class SearchText {
        public SearchText(JFrame f,JTextArea textArea) {
            JDialog jdlg = new JDialog(f, "Search", true);
            jdlg.setSize(700, 150);
            jdlg.setLocationRelativeTo(null);
            jdlg.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            JPanel panel=new JPanel();
            panel.setLayout(null);
            JLabel label=new JLabel("Text：");
            JLabel count=new JLabel("Find 0 target");
            JTextArea ftext=new JTextArea();
            JButton findnext=new JButton("Search(Next)");
            JButton no=new JButton("Cancel");
            JCheckBox matchcase=new JCheckBox("Differentiate capital and small");
            JLabel label1=new JLabel("How to find:");
            //创建两个单选按钮
            JRadioButton up = new JRadioButton("Up");
            JRadioButton down = new JRadioButton("Down");
            //创建按钮组，把两个单选按钮添加到该组
            ButtonGroup btnGroup = new ButtonGroup();
            btnGroup.add(up);
            btnGroup.add(down);
            // 设置第一个单选按钮选中
            up.setSelected(true);

            label.setBounds(20,20,93,22);
            count.setBounds(20, 55, 110, 35);
            ftext.setBounds(100,20,200,22);
            findnext.setBounds(320, 18, 200, 32);
            no.setBounds(320, 53, 200, 32);
            matchcase.setBounds(15, 90, 200, 25);
            label1.setBounds(250,90,100,25);
            up.setBounds(360, 90, 80, 25);
            down.setBounds(470, 90, 80, 25);

            //初始化查找按键
            findnext.setEnabled(false);
            //设置文本框和按钮的状态
            ftext.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(ftext.getText().equals(""))
                        findnext.setEnabled(false);
                }
                @Override
                public void insertUpdate(DocumentEvent e) {
                    findnext.setEnabled(true);
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    // TODO 自动生成的方法存根
                }
            });
            //查找下一个的监听器
            findnext.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int c=0;
                    int a = 0, b = 0;
                    int FindStartPos = textArea.getCaretPosition();
                    String strA,strB;
                    // 选中区分大小写,大小写转换
                    if (matchcase.isSelected()) {
                        strA = textArea.getText();
                        strB = ftext.getText();
                    } else {
                        strA = textArea.getText().toLowerCase();
                        strB = ftext.getText().toLowerCase();
                    }
                    //向上查找，否则向下查找
                    if (up.isSelected()) {
                        a = strA.lastIndexOf(strB, FindStartPos - ftext.getText().length() - 1);
                    } else if (down.isSelected()) {
                        a = strA.indexOf(strB, FindStartPos - ftext.getText().length() + 1);
                    }
                    //查找到边界
                    if (a > -1) {
                        if (up.isSelected()) {
                            textArea.setCaretPosition(a);
                            b = ftext.getText().length();
                            textArea.select(a, a + b);
                        } else if (down.isSelected()) {
                            textArea.setCaretPosition(a);
                            b = ftext.getText().length();
                            textArea.select(a, a + b);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Can't find(no more)",
                                "Search", JOptionPane.INFORMATION_MESSAGE);
                    }
                    //显示关键字的总数量
                    Pattern p=Pattern.compile(ftext.getText());
                    Matcher m=p.matcher(textArea.getText());
                    while(m.find()) {
                        c++;
                    }
                    count.setText("Find"+c+"targets");
                }
            });
            //取消的监听器
            no.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    jdlg.setVisible(false);
                }
            });

            label.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
            count.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
            ftext.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
            findnext.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
            no.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));

            panel.add(label);
            panel.add(count);
            panel.add(ftext);
            panel.add(findnext);
            panel.add(no);
            panel.add(matchcase);
            panel.add(label1);
            panel.add(up);
            panel.add(down);

            jdlg.setResizable(false);
            jdlg.setContentPane(panel);
            jdlg.setVisible(true);
        }
    }


    public static void main(String args[]) {
        TextFrame app = new TextFrame();

        app.setSize(600, 400);
        app.setLocation(200, 200);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setVisible(true);
    }
}
