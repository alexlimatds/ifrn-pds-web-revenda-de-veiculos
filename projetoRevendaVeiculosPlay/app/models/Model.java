package models;

/**
 * Superclasse que define atributos e métodos comuns a todas as classes de domínio que 
 * devem ser persistidas na base de dados.
 */
public abstract class Model {
	
	/**
	 * Identificador da instância.
	 */
	public Integer id;
	
	protected Model() {
		id = null;
	}
	
	protected Model(Integer id) {
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
		Model other = (Model) obj;
		if (this.id == null || other.id != null){
				return false;
		}
		
		return id.equals(other.id);
	}

}
