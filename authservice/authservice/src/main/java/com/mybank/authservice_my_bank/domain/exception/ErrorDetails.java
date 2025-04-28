
package com.mybank.authservice_my_bank.domain.exception;


import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {

    private String message;

    private List<String> errors;

}
