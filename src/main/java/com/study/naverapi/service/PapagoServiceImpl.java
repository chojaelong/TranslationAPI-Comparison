package com.study.naverapi.service;

import com.study.naverapi.domain.Tran;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class PapagoServiceImpl implements PapagoService{

    @Value("${naver-clientid}")
    private String clientId;
    @Value("${naver-clientsecret}")
    private String clientSecret;

    @Override
    public List<Tran> tran(String word) throws ParseException {

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        try {
            text = URLEncoder.encode(word, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("인코딩 실패", e);
        }

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        List<String> languages = Arrays.asList("en", "ja", "zh-CN", "vi", "id", "de", "fr");
        List<Tran> trans = new ArrayList<>();
        for (String lang : languages) {
            String responseBody = post(apiURL, requestHeaders, text, lang);

            //JSON 파싱
            JSONParser parser = new JSONParser();
            JSONObject title = (JSONObject) parser.parse(responseBody);
            JSONObject message = (JSONObject) title.get("message");
            JSONObject value = (JSONObject) message.get("result");

            Tran tran = new Tran();
            tran.setWord(word);
            tran.setLang((String) value.get("tarLangType"));
            tran.setResult((String) value.get("translatedText"));
            trans.add(tran);
        }

        return trans;
    }

    private String post(String apiUrl, Map<String, String> requestHeaders, String text, String lang) {
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=ko&target=" + lang + "&text=" + text;
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}
