package com.example.administrator.mytest2.HTTPRequest;

import android.util.Log;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/05/07.
 */

public class HttpRequest {
    static String responseData;         //用于传出从网页请求得到的数据
      public   static String content;   //用于传出解析后的数据

    //使用开源库OKHttp向服务器发出请求
    public static void sendRequestByOkHttp(final String url,okhttp3.Callback callback) {
        //使用回调接口便于对响应延时的数据作操作
            OkHttpClient client=new OkHttpClient();
           Request request=new Request.Builder()
                   .url(url)
                   .build();
          client.newCall(request).enqueue(callback);
    }

    //使用安卓原生的HttpURLConnection向服务器发出请求
    public static String  sendRequestByHttpURLConnection(final String url1,final HttpCallBackListener listener) {
        //使用回调接口便于对响应延时的数据作操作

        //开启线程来发送网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;

                try {
                    URL url=new URL(url1);
                    connection=(HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in=connection.getInputStream();
                    //接下来对获取的输入流进行获取
                    reader=new BufferedReader(new InputStreamReader(in));
                    StringBuilder response=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    if (listener!=null)
                        listener.onFinish(response.toString());
                    parseXMLWithPull();
                    parseXMLWithSAX();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if (reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection!=null){
                        connection.disconnect();
                    }
                }

            }
        }).start();
       return responseData;
    }



    //用Pull解析 解析从服务器请求来的XML格式数据
    private static void parseXMLWithPull() {
        try {
            Log.d("标记","进入函数");
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(responseData) );
            int eventType = xmlPullParser.getEventType();
            String name = "";
            String div = "";
            Log.d("标记","判断");
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = xmlPullParser.getName();
                switch (eventType) {
                    //开始解析每个节点
                    case XmlPullParser.START_TAG:
                        if ("title".equals(nodeName)) {
                            div = xmlPullParser.nextText();
                            Log.d("标记","div");
                        }else{
                            div = xmlPullParser.nextText();
                        Log.d("标记","div");}
                        break;

                    //完成解析每个节点
                    case XmlPullParser.END_TAG:
                        if ("head".equals(nodeName)) {
                            content = name + div;
                            Log.d("标记","head");
                        }
                        break;
                    default:
                        break;
                }
                Log.d("标记","head");
            eventType = xmlPullParser.next();
          }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //用SAX解析从服务器获取的数
     private static void parseXMLWithSAX (){
         Log.d("标记","进入函数");
         try {
             SAXParserFactory factory = SAXParserFactory.newInstance();
             XMLReader xmlReader = factory.newSAXParser().getXMLReader();
             HttpDefaultHandler handler=new HttpDefaultHandler();
             xmlReader.setContentHandler(handler);
             xmlReader.parse(new InputSource(new StringReader(responseData)));
         }
          catch (Exception e) {
             e.printStackTrace();
         }


     }
}
















