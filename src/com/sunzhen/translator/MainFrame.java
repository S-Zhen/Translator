package com.sunzhen.translator;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class MainFrame extends JFrame implements Runnable {
    private JTextField srcContentTextField; // ��¼���а������
    private JTextArea resContentTextField; // ��¼���������
    private JCheckBox translateFlag;       //��ǵ��ʵĻ�ȡ��Դ
                                           //ѡ�У��ֶ�����    δѡ�У����а��ȡ
    private Container topContainer;

    public MainFrame() {//��ʼ���ؼ�
        srcContentTextField = new JTextField(10);
        resContentTextField = new JTextArea();
        translateFlag = new JCheckBox();
        topContainer = new Container();
    }

    public void setMinWindowLayout() {
        // TODO Auto-generated method stub
        //��������
        resContentTextField.setBorder(new LineBorder(new java.awt.Color(127, 157, 185), 1, false));
        this.setLayout(new BorderLayout());
        this.add(this.resContentTextField);
        translateFlag.setToolTipText("�ֶ�����ȡ��");
        topContainer.setLayout(new BorderLayout());
        topContainer.add(srcContentTextField, BorderLayout.CENTER);
        topContainer.add(translateFlag, BorderLayout.EAST);
        this.add(this.topContainer, BorderLayout.NORTH);
        this.setResizable(false);

        translateFlag.addActionListener(new ActionListener() {//����JCheckBox�ļ���

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                if (translateFlag.isSelected()) {
                    translateFlag.setToolTipText("�Զ�����ȡ��");    //������ʾ
                } else {
                    translateFlag.setToolTipText("�ֶ�����ȡ��");
                }
            }
        });
        //����JTextField�������ݸı���¼�
        srcContentTextField.getDocument().addDocumentListener(new DocumentListener() {    

            @Override
            public void changedUpdate(DocumentEvent arg0) {

            }

            @Override
            public void insertUpdate(DocumentEvent arg0) {    //���ݸı�

                try {
                    //���ýӿڻ�ȡ������
                    String result = GetHtmlContentUtils.getTranslateResult(srcContentTextField.getText());
                    if (result == "")
                        result = "!Sorry,δ�ҵ��ô�!";
                    resContentTextField.setText(result);//��ʾ������
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    resContentTextField.setText("!Sorry,δ�ҵ��ô�!");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent arg0) {

            }

        });

        this.validate();
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            if (!translateFlag.isSelected()) {  //���JCheckBoxû�б�ѡ�У���Ӽ��а��ȡ����
                try {
                    String content = ClipboradUtils.getClipboardText();
                    srcContentTextField.setText(getSimpleWord(content));
                } catch (Exception e) { 
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static String getSimpleWord(String content) {//ȥ���а������һЩ�����ַ�
        return content.replace(".", "").replace(",", "")
                .replace("'", "").replace(":", "")
                .replace(";", "").trim();
    }
}
