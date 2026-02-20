//package com.example.comicbe.config;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.http.MediaType;
//import org.springframework.http.client.ClientHttpRequestInterceptor;
//import org.springframework.http.converter.FormHttpMessageConverter;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.StringHttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.client.RestTemplate;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//import java.security.KeyManagementException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.X509Certificate;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class ClientRequestConfig {
//
//    @Value("${openai.api-key:}")
//    private String apiKey;
//
//    @Bean
//    @Primary
//    public ObjectMapper mapper() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper = mapper.findAndRegisterModules();
//        mapper = mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//        mapper = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//        return mapper;
//    }
//
//    @Bean
//    @Primary
//    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyManagementException {
//        TrustManager[] trustAllCerts = new TrustManager[]{
//                new X509TrustManager() {
//                    public X509Certificate[] getAcceptedIssuers() {
//                        return new X509Certificate[0];
//                    }
//
//                    public void checkClientTrusted(
//                            X509Certificate[] certs, String authType) {
//                    }
//
//                    public void checkServerTrusted(
//                            X509Certificate[] certs, String authType) {
//                    }
//                }
//        };
//        SSLContext sslContext = SSLContext.getInstance("SSL");
//        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
//
//
//        RestTemplate restTemplate = new RestTemplate();
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//
//        messageConverters.add(new FormHttpMessageConverter());
//        messageConverters.add(new StringHttpMessageConverter());
//
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        // Note: here we are making this converter to process any kind of response,
//        // not only application/*json, which is the default behaviour
//        MediaType[] mt = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_JSON_UTF8,
//                MediaType.APPLICATION_OCTET_STREAM};
//        converter.setSupportedMediaTypes(Arrays.asList(mt));
//        messageConverters.add(converter);
//
//        restTemplate.setMessageConverters(messageConverters);
//        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
//        interceptors.add(new RestTemplateInterceptor(apiKey));
//        restTemplate.setInterceptors(interceptors);
//
//        return restTemplate;
//    }
//}
