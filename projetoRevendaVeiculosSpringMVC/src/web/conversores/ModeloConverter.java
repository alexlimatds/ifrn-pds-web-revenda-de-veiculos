package web.conversores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;

import dominio.Modelo;
import dominio.RepositorioModelo;

public class ModeloConverter implements Converter<String, Modelo>{
	
	@Autowired
	private RepositorioModelo repositorio;
	
	@Override
	public Modelo convert(String idStr) {
		try{
			Integer idInt = Integer.valueOf(idStr);
			return repositorio.getPorId(idInt);
		}
		catch(RuntimeException ex){
			throw new ConversionFailedException(
					TypeDescriptor.valueOf(String.class), 
					TypeDescriptor.valueOf(Integer.class), 
					idStr,
					ex);
		}
	}

}
