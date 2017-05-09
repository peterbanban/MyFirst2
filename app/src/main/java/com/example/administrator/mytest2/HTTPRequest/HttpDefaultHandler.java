package com.example.administrator.mytest2.HTTPRequest;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Created by Administrator on 2017/05/08.
 */

public class HttpDefaultHandler extends DefaultHandler {

     //定义需要解析的内容
    private String nodeName;
    private StringBuilder stringBuilder1;
    private StringBuilder stringBuilder2;
    //  可以继续添加需要解析的内容

    @Override
    public void startDocument() throws SAXException {
        //开始解析整个文档
        Log.d("标记","开始解析整个文档");
        stringBuilder1 =new StringBuilder();
        stringBuilder2 =new StringBuilder();
        super.startDocument();

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //开始解析某节点
    Log.d("标记","开始解析某节点");
        //记录当前节点名
        nodeName=localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        //正在解析节点中的内容
        Log.d("标记","正在解析节点中的内容");
        if("title".equals(nodeName))
            stringBuilder1.append(ch,start,length);
        if("id".equals(nodeName))
            stringBuilder2.append(ch,start,length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //结束解析某节点
        Log.d("标记","结束解析某节点");
        if("html".equals(nodeName)){
            //在此可以处添加自定义事件
            Log.d("SAX解析",stringBuilder1.toString()+stringBuilder2.toString());

            //将stringBuilder 清空
             stringBuilder1.setLength(0);
             stringBuilder2.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
