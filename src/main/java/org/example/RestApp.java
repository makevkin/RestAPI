package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.model.User;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class RestApp
{
    public static void main( String[] args ) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://94.198.50.185:7081/api/users";
        // делаем get запрос к удаленному API сервису
        String response = restTemplate.getForObject(url, String.class);

        System.out.println(response);
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        List<String> cookie = responseEntity.getHeaders().get("Set-Cookie");
        System.out.println(cookie);
//        String qqq = responseEntity.getBody();
//        System.out.println(qqq);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", cookie.stream().collect(Collectors.joining(";")));

        // делаем post запрос
        User user = new User(3L, "James", "Brown", (byte) 20);
        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);
        responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        System.out.println(responseEntity.getBody());

        // делаем put запрос
        user.setName("Thomas");
        user.setLastName("Shelby");
        HttpEntity<User> requestEntity1 = new HttpEntity<>(user, headers);
        responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity1, String.class);
        System.out.println(responseEntity.getBody());

        // делаем delete запрос

        HttpEntity<User> requestEntity2 = new HttpEntity<>(headers);
        responseEntity = restTemplate.exchange(url + "/3", HttpMethod.DELETE, requestEntity2, String.class);
        System.out.println(responseEntity.getBody());

    }
//
//        // Парсим полученный ответ от сервера с помощью Jackson
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode obj = mapper.readTree(response1);
//        System.out.println(obj);


//        // Парсим ответ с помощью класса, но пока не получилось
//        ResponceService responce2 = restTemplate.postForObject(url, request, ResponceService.class);
//        String responce2 = restTemplate.postForObject(url1, request, String.class);
//        System.out.println(responce2.getResponceList().get(0).getValue());

    }

