package dominio;

import java.io.Serializable;

/**
 * Entidade genérica que define o atributo id e os métodos equals e hashCode. 
 * As entidades do sistema podem herdar desta classe para evitar a escrita destes 
 * membros.
 * 
 * @author Alexandre
 */
public abstract class Entidade implements Serializable{
	
	private Integer id;
	
	public Entidade() {}
	
	public Entidade(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entidade other = (Entidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
