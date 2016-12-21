package com.kaishengit.web;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import sun.net.www.http.HttpClient;

import java.io.IOException;

/**
 * Created by loveoh on 2016/12/21.
 */
public class Test {
    public static void main(String[] args) throws IOException {

            CloseableHttpClient httpClient = HttpClients.createDefault();
            String url = "http://192.168.1.12/reg?username=111222&password=123&email=asdf@qq.com&phone=12332123121";
        for (int i = 0 ;i<10;i++) {
            HttpPost httpPost = new HttpPost(url);
            System.out.println(httpClient.execute(httpPost));
        }
    }


}
