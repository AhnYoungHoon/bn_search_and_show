package com.example.NTSBusinessNum.Bno;

import com.example.NTSBusinessNum.Site.Site;
import com.example.NTSBusinessNum.Site.SiteRepository;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

@RequiredArgsConstructor //리포지터리 왜 초기화 안하냐는 오류 해결
@Service
public class BnoService {
    private final SiteRepository siteRepository;


    public JSONObject searchBno(String b_no) throws IOException {
        Site site = new Site();


        JSONObject jsonObject = new JSONObject();
        String state = searchBusinessNumber(b_no);

        site.setBusinessNum(b_no);
        site.setState(state);
        site.setSearchDate(LocalDateTime.now());
        site.setUrl("사업자번호로만 검색");
        siteRepository.save(site);

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
