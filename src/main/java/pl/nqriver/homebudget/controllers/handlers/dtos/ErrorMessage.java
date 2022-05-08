package pl.nqriver.homebudget.controllers.handlers.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorMessage {
    String errorCode;
    String errorDescription;
}
