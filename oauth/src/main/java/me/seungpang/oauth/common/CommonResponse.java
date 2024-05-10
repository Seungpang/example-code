package me.seungpang.oauth.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CommonResponse<T> {

    private static final String SUCCESS_MESSAGE = "SUCCESS";
    private static final String ERROR_MESSAGE = "ERROR";

    private String result;
    private String message;
    private T data;

    public static <T> ResponseEntity<CommonResponse<T>> success(final T data) {
        return ResponseEntity.ok(new CommonResponse<>(SUCCESS_MESSAGE, null, data));
    }

    public static ResponseEntity<CommonResponse<Void>> success() {
        return ResponseEntity.ok(new CommonResponse<>(SUCCESS_MESSAGE, null, null));
    }

    public static CommonResponse<Void> error(final String message) {
        return new CommonResponse<>(ERROR_MESSAGE, message, null);
    }
}
