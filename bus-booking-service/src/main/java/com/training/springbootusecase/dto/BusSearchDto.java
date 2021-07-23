package com.training.springbootusecase.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BusSearchDto {

    private String source;
    private String destination;

}
