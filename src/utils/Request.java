package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class Request<RequestType,ResponseType>{
    final String serverUri = "http://localhost:8080";
    public static String xAccessToken = "";
    HttpRequest.BodyPublisher requestBody;
    ObjectMapper objectMapper = new ObjectMapper();
    RequestType body;
    ResponseType result;
    HttpResponse<String> response;
    URI restUri;

    public Request(String restUri){
        this.body = null;
        try{
            this.requestBody = HttpRequest.BodyPublishers.ofString(
                    objectMapper.writeValueAsString(body));
        }catch (Exception e){
            System.out.println(body.getClass().getName() + "타입을 문자열로 변환할 수 없습니다.");
            System.out.println("에러 정보: "+e);
        }
        this.restUri = URI.create(serverUri+restUri);
    }

    public Request(String restUri, RequestType body){
        this.body = body;
        try{
            this.requestBody = HttpRequest.BodyPublishers.ofString(
                    objectMapper.writeValueAsString(body));
        }catch (Exception e){
            System.out.println(body.getClass().getName() + "타입을 문자열로 변환할 수 없습니다.");
            System.out.println("에러 정보: "+e);
        }
            this.restUri = URI.create(serverUri+restUri);
    }

    public ResponseType GET(Class<ResponseType> c){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();
        try{
            response = client.send(HttpRequest.newBuilder()
                            .uri(restUri)
                            .header("xAccessToken", xAccessToken)
                            .GET()
                            .build()
                    , HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()/100 != 2){
                CustomError error = (CustomError) objectMapper.readValue(response.body(), CustomError.class);
                JOptionPane.showMessageDialog(null, error.message , "Message", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            result = (ResponseType) objectMapper.readValue(response.body(), c);
        }catch (Exception e){
            System.out.println("GET" + restUri+" 요청에 실패했습니다.");
            System.out.println("에러 정보: "+e);
        }


        return result;
    }

    public ResponseType POST(Class<ResponseType> c){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();
        try{
            response = client.send(HttpRequest.newBuilder()
                            .uri(restUri)
                            .header("x-access-token", xAccessToken)
                            .header("Content-Type", "application/json; charset=UTF-8")
                            .POST(requestBody)
                            .build()
                    , HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            if(response.statusCode()/100 != 2){
                System.out.println(response.body());
                CustomError error = (CustomError) objectMapper.readValue(response.body(), CustomError.class);
                JOptionPane.showMessageDialog(null, error.message , "Message", JOptionPane.ERROR_MESSAGE);
            }
            result = (ResponseType) objectMapper.readValue(response.body(), c);
        }catch (Exception e){
            System.out.println("POST " + restUri+" 요청에 실패했습니다.");
            System.out.println("에러 정보: "+e);
        }

        return result;
    }

    public ResponseType PUT(Class<ResponseType> c){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();
        try{
            response = client.send(HttpRequest.newBuilder()
                            .uri(restUri)
                            .header("x-access-token", xAccessToken)
                            .header("Content-Type", "application/json; charset=UTF-8")
                            .DELETE()
                            .build()
                    , HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()/100 != 2){
                CustomError error = (CustomError) objectMapper.readValue(response.body(), CustomError.class);
                JOptionPane.showMessageDialog(null, error.message , "Message", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            result = (ResponseType) objectMapper.readValue(response.body(), c);
        }catch (Exception e){
            System.out.println("GET" + restUri+" 요청에 실패했습니다.");
            System.out.println("에러 정보: "+e);
        }
        return result;
    }


    public ResponseType DELETE(Class<ResponseType> c){
        ObjectMapper objectMapper = new ObjectMapper();
        HttpClient client = HttpClient.newHttpClient();
        try{
            response = client.send(HttpRequest.newBuilder()
                            .uri(restUri)
                            .header("x-access-token", xAccessToken)
                            .header("Content-Type", "application/json; charset=UTF-8")
                            .PUT(requestBody)
                            .build()
                    , HttpResponse.BodyHandlers.ofString());
            if(response.statusCode()/100 != 2){
                CustomError error = (CustomError) objectMapper.readValue(response.body(), CustomError.class);
                JOptionPane.showMessageDialog(null, error.message , "Message", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            result = (ResponseType) objectMapper.readValue(response.body(), c);
        }catch (Exception e){
            System.out.println("PUT " + restUri+" 요청에 실패했습니다.");
            System.out.println("에러 정보: "+e);
        }
        return result;
    }
}
