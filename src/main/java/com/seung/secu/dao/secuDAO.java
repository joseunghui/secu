package com.seung.secu.dao;

import com.seung.secu.dto.STORE;
import com.seung.secu.dto.secuDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface secuDAO {
    void secuJoin(secuDTO secu);

    secuDTO secuLogin(secuDTO secu);

    int dmap3(STORE store);
}
