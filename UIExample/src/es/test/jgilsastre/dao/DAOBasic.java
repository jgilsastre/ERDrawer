package es.test.jgilsastre.dao;

import java.util.List;

public interface DAOBasic<E> {

	public static final String _ID = "_id";
	public static final String IDENTIFIER = "ext_identifier";
	
	public void insert(E elem) throws DAOException;
	
	public void update(E elem) throws DAOException;
	
	public E find(String identifier) throws DAOException;
	
	public List<E> findAll() throws DAOException;
}
