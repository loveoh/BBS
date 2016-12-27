package com.kaishengit.web;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by loveoh on 2016/12/21.
 */
public class Test {
    public static void main(String[] args) throws IOException {

       /* CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "http://192.168.1.12/reg?username=111222&password=123&email=asdf@qq.com&phone=12332123121";
        for (int i = 0 ;i<10;i++) {
            HttpPost httpPost = new HttpPost(url);
            System.out.println(httpClient.execute(httpPost));*/

    /*    for (int j = 1;j<10;j++){
            Document document = Jsoup.connect("http://www.topit.me/tag/性感?p="+j)
                    .cookie("is_click","1")
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                    .get();

            //Elements elements = document.select("#msy .t >a");
            Elements elements = document.select("#content .catalog  .e >a");
           //Elements elements = document.select(".zm-item-answer .zm-editable-content img");
            System.out.println(elements);
            for (int i = 0;i<elements.size();i++){
                Element element = elements.get(i);
                String href = element.attr("href");
                System.out.println(href);

                Document bigDocument = Jsoup.connect(href)
                        .cookie("is_click","1")
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                        .get();
                Element bigElement = bigDocument.select("#content a img").first();
                //Element bigElement = bigDocument.select("#item-tip").first();
                String imgsrc = bigElement.attr("src");
                System.out.println(imgsrc);

                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpGet httpGet = new HttpGet(imgsrc);
                HttpResponse response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200){
                    InputStream inputStream = response.getEntity().getContent();
                    String imageName = imgsrc.substring(imgsrc.lastIndexOf("/"));
                    OutputStream outputStream = new FileOutputStream("H:/images/性感"+imageName);
                    IOUtils.copy(inputStream,outputStream);
                    outputStream.flush();
                    outputStream.close();
                    inputStream.close();
                }else{
                    System.out.println("error = "+response.getStatusLine().getStatusCode());
                }
            }

        }*/

      /*  //知乎爬虫
        Document document = Jsoup.connect("https://www.zhihu.com/question/22212644")
                .cookie("n_c", "1")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
                .get();

        //Elements elements = document.select("#msy .t >a");
        // Elements elements = document.select("#content .catalog  .e >a");
        Elements elements = document.select(".zm-item-answer .zm-editable-content img");
        // System.out.println(elements);
       // List<String> list = new ArrayList<>();
        for (int i = 0; i < elements.size(); i+=2) {
            Element element = elements.get(i);
            String href = element.attr("src");
            System.out.println(href);
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(href);
            HttpResponse response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                InputStream inputStream = response.getEntity().getContent();
                String imageName = href.substring(href.lastIndexOf("/"));
                OutputStream outputStream = new FileOutputStream("H:/images/知乎大胸"+imageName);
                IOUtils.copy(inputStream,outputStream);
                outputStream.flush();
                outputStream.close();
                inputStream.close();
            }else{
                System.out.println("error = "+response.getStatusLine().getStatusCode());
            }
           // list.add(href);
        }*/


//            Document bigDocument = Jsoup.connect(href)
//                    .cookie("is_click","1")
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36")
//                    .get();
//            //Element bigElement = bigDocument.select("#content a img").first();
//            Element bigElement = bigDocument.select("#item-tip").first();
//            String imgsrc = bigElement.attr("href");
//            System.out.println(imgsrc);

//            CloseableHttpClient httpClient = HttpClients.createDefault();
//            HttpGet httpGet = new HttpGet(href);
//            HttpResponse response = httpClient.execute(httpGet);
//            if (response.getStatusLine().getStatusCode() == 200){
//                InputStream inputStream = response.getEntity().getContent();
//                String imageName = href.substring(href.lastIndexOf("/"));
//                OutputStream outputStream = new FileOutputStream("H:/images/知乎"+imageName);
//                IOUtils.copy(inputStream,outputStream);
//                outputStream.flush();
//                outputStream.close();
//                inputStream.close();
//            }else{
//                System.out.println("error = "+response.getStatusLine().getStatusCode());
//            }
//        }



    }
}



