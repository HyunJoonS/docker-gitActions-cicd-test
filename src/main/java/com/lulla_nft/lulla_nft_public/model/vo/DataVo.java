package com.lulla_nft.lulla_nft_public.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataVo {

    private boolean success;
    private Object data;
    private String message;
    private String status;
    private String memo;

}
