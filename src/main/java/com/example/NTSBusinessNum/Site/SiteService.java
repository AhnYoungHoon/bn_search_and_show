package com.example.NTSBusinessNum.Site;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class SiteService {

    public JSONObject searchSiteUrl(String siteUrl) throws IOException {
        List<Site> siteList = new ArrayList<>();
        Document document = Jsoup.connect(siteUrl).get();

        Elements contents = document.select("._2jA5rc-8oC"); // XXXXXXXXXX인 경우
        String str = "";
        String b_no = "";
        for (Element content : contents) {
            if(String.valueOf(content).length()==51){
                System.out.println(content.select("._2jA5rc-8oC"));
                System.out.println(String.valueOf(content.select("._2jA5rc-8oC")).length());
                str = String.valueOf(content.select("._2jA5rc-8oC"));
                b_no = str.substring(36,46);
            }
        }
        if(b_no.length()!=10){ // XXX-XX-XXXXX 형태인 경우
            System.out.println("a");
            contents = document.select("._3fpUfPAXM5");

            for(Element content : contents){
                System.out.println("b");
                if (String.valueOf(content).length()==53) {
                    System.out.println(content.select("._3fpUfPAXM5"));
                    System.out.println(String.valueOf(content.select("._3fpUfPAXM5")).length());
                    str = String.valueOf(content.select("._3fpUfPAXM5"));
                    b_no = str.substring(36,39) + str.substring(40,42) + str.substring(43,48);
                } else if (String.valueOf(content).length()==51) { // XXXXXXXXXX인데 css class가 _3fpUfPAXM5인 경우 (현재 안됨)
                    System.out.println(content.select("._3fpUfPAXM5"));
                    System.out.println(String.valueOf(content.select("._3fpUfPAXM5")).length());
                    str = String.valueOf(content.select("._3fpUfPAXM5"));
                    b_no = str.substring(36,46);
                }
            }
        }
        String state = searchBusinessNumber(b_no);
        System.out.println(b_no+" "+state);

        JSONObject jsonObject = new JSONObject();
        jsonObject.append("businessNumber", b_no);
        jsonObject.append("state", state);


        return jsonObject;
    }

    public String searchBusinessNumber(String b_no){
        URL url = null;
        HttpURLConnection urlConnection = null;

        String apiUrl = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=kMQVtVR8c3Ptw6aaSib1U4PkPeo6ADa4hhUDadtAiLbzfX%2FZsDuYYwGl5mbkvgzY8lGVGOlvuU6UBFibFmobOg%3D%3D";
        JSONObject params = new JSONObject();
        try{
            url = new URL(apiUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setConnectTimeout(30000);
            urlConnection.setReadTimeout(50000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type","application/json;utf-8");
            urlConnection.setRequestProperty("Accept","application/json");
            urlConnection.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            System.out.println(b_no);
            params.append("b_no", b_no);
            System.out.println(params);
            System.out.println(params.getClass());
            bw.write(params.toString());
            bw.flush();
            bw.close();

            int responseCode = urlConnection.getResponseCode();
            if(responseCode == 200){
                BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                JSONObject responseJson = new JSONObject(sb.toString());

                // 응답 데이터
                System.out.println("responseJson :: " + responseJson);
                JSONArray jsondata = (JSONArray) responseJson.get("data");
                JSONObject forBno = jsondata.getJSONObject(0);
                System.out.println("responseJson.data.b_no :: " + forBno.get("b_no"));
                System.out.println("tax_type :: " + forBno.get("tax_type"));
                if(forBno.get("tax_type").equals("국세청에 등록되지 않은 사업자등록번호입니다.")){
                    return "존재하지 않는 사업자";
                }else{
                    return "존재하는 사업자";
                }
            }else{
                return "에러";
            }


        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }



}
