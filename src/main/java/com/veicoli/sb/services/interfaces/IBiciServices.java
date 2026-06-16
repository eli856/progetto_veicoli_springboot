package com.veicoli.sb.services.interfaces;

import java.util.List;

import com.veicoli.sb.dto.input.BiciReq;
import com.veicoli.sb.dto.output.BiciDTO;

public interface IBiciServices {
	void create(BiciReq req) throws Exception;
    void update(BiciReq req) throws Exception;
    void delete(Integer id)  throws Exception;
    List<BiciDTO> list() throws Exception;
    BiciDTO getById(Integer id) throws Exception;
}
