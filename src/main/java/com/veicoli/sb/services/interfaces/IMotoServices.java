package com.veicoli.sb.services.interfaces;

import java.util.List;

import com.veicoli.sb.dto.input.MotoReq;
import com.veicoli.sb.dto.output.MotoDTO;

public interface IMotoServices {
	void create(MotoReq req) throws Exception;
    void update(MotoReq req) throws Exception;
    void delete(Integer id)  throws Exception;
    List<MotoDTO> list() throws Exception;
    MotoDTO getById(Integer id) throws Exception;
}
