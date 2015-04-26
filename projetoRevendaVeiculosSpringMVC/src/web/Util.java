package web;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import dominio.Foto;
import dominio.Veiculo;

/**
 * Classe utilit�ria para preenchimento dos campos de um ve�culo.
 * @author Alexandre
 */
public class Util {
	public static Foto multipartToFoto(MultipartFile arquivoFoto) 
			throws IOException{
		Foto foto = null;
		if(!arquivoFoto.isEmpty()){
			byte[] bytes = arquivoFoto.getBytes();
			String mimeType = arquivoFoto.getContentType();
			foto = new Foto(bytes, mimeType);
		}
		return foto;
	}
}
