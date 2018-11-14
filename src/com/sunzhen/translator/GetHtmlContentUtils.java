package com.sunzhen.translator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetHtmlContentUtils {
    private final static String PreUrl="http://www.baidu.com/s?wd=";                        //�ٶ�����URL
    private final static String TransResultStartFlag="<span class=\"op_dict_text2\">";      //���뿪ʼ��ǩ
    private final static String TransResultEndFlag="</span>";                               //���������ǩ

    public static String getTranslateResult(String urlString) throws Exception {    //����Ҫ�����ĵ���
        URL url = new URL(PreUrl+urlString);            //����������URL
        // ��URL
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        // �õ������������������ҳ������
        BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
        String preLine="";
        String line;
        int flag=1;
        // ��ȡ�����������ݣ�����ʾ
        String content="";          //������
        while ((line = reader.readLine()) != null) {            //��ȡ���������㷨
            if(preLine.indexOf(TransResultStartFlag)!=-1&&line.indexOf(TransResultEndFlag)==-1){
                content+=line.replaceAll("��| ", "")+"\n";   //ȥ��Դ��������İ���Լ�ȫ���ַ�
                flag=0;
            }
            if(line.indexOf(TransResultEndFlag)!=-1){
                flag=1;
            }
            if(flag==1){
                preLine=line;
            }
        }
        return content;//���ط�����
    }
    
    public static void main(String[] args) throws Exception {
		System.out.println(getTranslateResult("blue"));
	}

}