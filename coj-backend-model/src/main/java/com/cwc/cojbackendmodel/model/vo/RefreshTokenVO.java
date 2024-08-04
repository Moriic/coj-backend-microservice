package com.cwc.cojbackendmodel.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenVO {
    private String token;
    private String refreshToken;
}
