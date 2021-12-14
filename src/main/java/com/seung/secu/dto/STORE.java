package com.seung.secu.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("store")
public class STORE {
    String storeName;
    double lat;
    double lng;
}
