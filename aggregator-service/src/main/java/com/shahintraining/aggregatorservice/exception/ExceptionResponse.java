package com.shahintraining.aggregatorservice.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * @author sh.khalajestanii
 * @project microservices
 * @since 11/1/2021
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExceptionResponse {
    private Date timeStamp;
    private String message;
    private String details;
}
