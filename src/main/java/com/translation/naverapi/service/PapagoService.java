package com.translation.naverapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.translation.naverapi.domain.Translate;
import com.translation.naverapi.domain.TranslateInfo;
import com.translation.naverapi.repository.PapagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PapagoService {

    @Value("${naver-clientid}")
    private String clientId;
    @Value("${naver-clientsecret}")
    private String clientSecret;

    private final PapagoRepository papagoRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    public TranslateInfo translate(String word) throws JsonProcessingException {

        Optional<TranslateInfo> searchedTranslateInfo = papagoRepository.findByName(word);
        if (searchedTranslateInfo.isPresent()) {
            log.info("Searched word={}", word);
            return searchedTranslateInfo.get();
        }

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

        List<String> param = Arrays.asList("en", "ja", "zh-CN", "vi", "de", "fr");
        TranslateInfo translateInfo = new TranslateInfo();
        translateInfo.setName(word);

        for (String lang : param) {
            String responseBody = post(apiURL, requestHeaders, text, lang);

            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode result = jsonNode.findValue("result");

            mappingTranslateInfo(translateInfo, lang, result);
        }

        papagoRepository.save(translateInfo);
        return translateInfo;
    }

    private void mappingTranslateInfo(TranslateInfo translateInfo, String lang, JsonNode result) throws JsonProcessingException {
        Translate translate = objectMapper.treeToValue(result, Translate.class);
        String translatedText = translate.getTranslatedText();

        switch (lang) {
            case "en":
                translateInfo.setEnglish(translatedText);
                break;
            case "ja":
                translateInfo.setJapanese(translatedText);
                break;
            case "zh-CN":
                translateInfo.setChinese(translatedText);
                break;
            case "vi":
                translateInfo.setVietnamese(translatedText);
                break;
            case "de":
                translateInfo.setGerman(translatedText);
                break;
            case "fr":
                translateInfo.setFrench(translatedText);
                break;
        }
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
        InputStreamReader streamReader = new InputStreamReader(body, StandardCharsets.UTF_8);

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
