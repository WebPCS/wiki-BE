package kr.pah.comwiki.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
    private Integer httpCode;
    private T message;

    public ResponseEntity<?> create(Integer httpCode, T message) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        return ResponseEntity.ok().headers(headers).body(new Result<T>(httpCode, message));
    }
}
