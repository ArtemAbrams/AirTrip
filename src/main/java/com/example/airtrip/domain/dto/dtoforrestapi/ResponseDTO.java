package com.example.airtrip.domain.dto.dtoforrestapi;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDTO<T> {
    private T data;
    private HttpStatus httpStatus;
    private String error;
    public static <T> ResponseDTO<T> data(T data, HttpStatus httpStatus){
        return new ResponseDTO<>(data, httpStatus);
    }
    public ResponseDTO(T data, HttpStatus httpStatus){
        this.httpStatus = httpStatus;
        this.data = data;
    }
    public ResponseDTO(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }
    public ResponseDTO(HttpStatus httpStatus, String error){
        this.httpStatus = httpStatus;
        this.error=error;
    }
    public static ResponseDTO<Void> success(){
        return new ResponseDTO<>(HttpStatus.OK);
    }
    public static ResponseDTO<Void> error(String error, HttpStatus httpStatus){
        return new ResponseDTO<>(httpStatus, error);
    }
}
