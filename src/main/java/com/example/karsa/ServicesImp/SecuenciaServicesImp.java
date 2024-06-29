package com.example.karsa.ServicesImp;

import com.example.karsa.ServicesInt.ISecuenciaServiceInt;
import com.example.karsa.model.SecuenciaModel;
import com.example.karsa.repository.ISecuenciaRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecuenciaServicesImp implements ISecuenciaServiceInt{
    
    @Autowired
    private ISecuenciaRepository secuenciaRepository; 
    
    @Override
    public Integer getSecuencia() {
        List<SecuenciaModel> secuencias = secuenciaRepository.findAll();
        Integer secuencia = 0;
        if(!secuencias.isEmpty()){
            secuencia = secuencias.stream().max(Comparator.comparing(SecuenciaModel::getSecuencia)).get().getSecuencia() + 1;
        }
        SecuenciaModel secuenciaToSave = new SecuenciaModel(); 
        secuenciaToSave.setId(0);
        secuenciaToSave.setSecuencia(secuencia);
        saveOrUpdateSecuencia(secuenciaToSave);
        return secuencia; 
    }

    @Override
    public void saveOrUpdateSecuencia(SecuenciaModel secuencia) {
        secuenciaRepository.save(secuencia);
    }
    
}
