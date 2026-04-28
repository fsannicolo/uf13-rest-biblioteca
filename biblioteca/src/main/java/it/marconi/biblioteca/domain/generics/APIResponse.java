package it.marconi.biblioteca.domain.generics;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // Esclude i campi null dalla serializzazione JSON
public class APIResponse<T> {
    private APIResponseStatus status;
    private String message;
    private T data;

    public static <T extends Collection<?>> APIResponse<T> success(T data) {
        return APIResponse.<T>builder()
                .status(APIResponseStatus.SUCCESS)
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> success(T data) {
        return APIResponse.<T>builder()
                .status(APIResponseStatus.SUCCESS)
                .data(data)
                .build();
    }

    public static <T> APIResponse<T> error(String message) {
        return APIResponse.<T>builder()
                .status(APIResponseStatus.ERROR)
                .message(message)
                .build();
    }
}
