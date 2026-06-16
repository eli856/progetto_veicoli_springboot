package com.veicoli.sb.services.interfaces;

import java.util.List;

import com.veicoli.sb.dto.input.MacchinaReq;
import com.veicoli.sb.dto.output.MacchinaDTO;


public interface IMacchinaServices {
    void create(MacchinaReq req) throws Exception;
    void update(MacchinaReq req) throws Exception;
    void delete(Integer id)  throws Exception;
    List<MacchinaDTO> list() throws Exception;
    MacchinaDTO getById(Integer id) throws Exception;
}
