package com.example.karsa.ServicesInt;

import com.example.karsa.model.SecuenciaModel;

public interface ISecuenciaServiceInt {
    public Integer getSecuencia();
    public void saveOrUpdateSecuencia(SecuenciaModel secuencia); 
}
