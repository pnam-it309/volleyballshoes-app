package com.DuAn1.volleyballshoes.app.entity;

import java.time.LocalDate;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    private int id;
    private int invoiceId;
    private String method;
    private String status;
    private LocalDate paidDate;
}
