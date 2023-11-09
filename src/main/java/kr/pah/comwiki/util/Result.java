package kr.pah.comwiki.util;

import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;

@Data
public class Result<T> {
    private Integer status;
    private T payload;

    private static final HttpHeaders JSON_HEADERS = createJsonHeaders();

    public Result(Integer status, T payload) {
        this.status = status;
        this.payload = payload;
    }

    public static ResponseEntity<?> login() {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/login"));
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }

    public static <T> ResponseEntity<Result<T>> create(HttpStatus status, T payload) {
        return ResponseEntity.status(status).headers(JSON_HEADERS).body(new Result<>(status.value(), payload));
    }

    private static HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return headers;
    }
}
