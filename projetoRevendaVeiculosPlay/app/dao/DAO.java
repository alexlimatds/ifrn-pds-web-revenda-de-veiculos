package dao;

import java.util.List;

/**
 * Define os métodos comuns a todos os DAOs.
 */
public interface DAO<T> {
	
	/**
	 * Insere uma instância de T na base de dados.
	 * @param 	f instância a ser inserida.
	 * @return	o id atribuído ao objeto f.
	 */
	Integer inserir(T f);
	
	/**
	 * Atualiza uma instância de T na base dados.
	 * @param f	instância a ser atualizada.
	 */
	void atualizar(T f);
	
	/**
	 * Exclui uma instância de T da base de dados.
	 * @param id	id da instância a ser excluída.
	 */
	void excluir(Integer id);
	
	/**
	 * Retorna todas as instâncias de T registradas na base dados. 
	 * @return
	 */
	List<T> todos();
	
	/**
	 * Retorna uma instância específica de T.
	 * @param id	id da instância desejada.
	 * @return		retorna a instância desejada, ou null caso não exista instância com o id especificado. 
	 */
	T getPorId(Integer id);
}
