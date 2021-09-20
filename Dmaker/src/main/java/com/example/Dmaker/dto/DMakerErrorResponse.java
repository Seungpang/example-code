package com.example.Dmaker.dto;

import com.example.Dmaker.exception.DMakerErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorResponse {

    private DMakerErrorCode errorCode;
    private String errorMessage;

}
