package tn.com.dto;

import java.util.Date;

import enums.OperationType;
import lombok.Data;

@Data
public class AccountOperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;
    private String description;
}