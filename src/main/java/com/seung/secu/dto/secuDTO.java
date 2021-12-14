package com.seung.secu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("secu")
public class secuDTO {
    String secuMail;
    String secuPw;
}
