package dominio;

/**
 * Representa um arquivo de foto. 
 * @author Alexandre
 */
public class Foto {
	private byte[] bytes;
	private String mimeType;
	
	public Foto() {}
	
	public Foto(byte[] bytes, String mimeType) {
		this.bytes = bytes;
		this.mimeType = mimeType;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}
}
