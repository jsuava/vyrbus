/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Sullo Avalos
 * Fecha		: 01/04/2014
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.cystesoft.vyrbus.model.dao.GenericDAO;

/**
 * @author JABANTO
 *
 */
public abstract class GenericDAOImpl extends HibernateDaoSupport implements GenericDAO {
	
protected final Logger log;

	/**
	 * Contructor que de paso inicializa el logger.
	 */
	public GenericDAOImpl() {
		super();
		log = Logger.getLogger(getClass());
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GenericDAO#save(java.lang.Object)
	 */
	@Override
	public void save(Object object) {
		log.info("Guardando instancia "+object.getClass().getName());
		try {
			getHibernateTemplate().save(object);
			log.info("Se guardo exitosamente");
		} catch (RuntimeException rex) {
			log.info("Fallo el registro", rex);
			//System.out.println("aca ---->"+rex.getCause().getCause().getLocalizedMessage().substring(59, 60));
			throw rex;
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GenericDAO#update(java.lang.Object)
	 */
	@Override
	public void update(Object object) {
		log.debug("Actualizando instancia "+object.getClass().getName());
		try {
			getHibernateTemplate().merge(object);
			//getHibernateTemplate().update(object);
			log.debug("Actualizacion exitosa");
		} catch (RuntimeException rex) {
			log.error("Fallo en la actualizacion", rex);
			throw rex;
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.GenericDAO#delete(java.lang.Object)
	 */
	@Override
	public void delete(Object object) {
		log.debug("Borrando objeto" + object.getClass().getName());
		try {
			getHibernateTemplate().delete(object);
			log.debug("Borrado exitoso");
		} catch (RuntimeException rex) {
			log.error("Fallo el borrado", rex);
			throw rex;
		}
	}

	

	/**
	 * @author jM
	 */
	public Object findById(Class<?> oClass, Long id) {
		String hql = "FROM " + oClass.getSimpleName() + " WHERE id = ?";

		return getSession().createQuery(hql).setLong(0, id).uniqueResult();
	}
	/**
	 * @author jM
	 */
	public List<?> findByEstadoRegistro(Class<?> oClass, String estado, String orderCriterio) {
		String hql = "FROM " + oClass.getSimpleName() + " WHERE estadoRegistro = ? ORDER BY "+orderCriterio;

		return getSession().createQuery(hql).setString(0, estado).list();
	}
	/**
	 * Realiza la busqueda por los criterios pasados como parametros y los ordena.
	 * @param oClass				: Clase a la cualse le aplicara el criterio.
	 * @param criteriosBusqueda		: Criterios que se aplicaran para la busqueda.
	 * @param criteriosOrdenar		: Criterios que se aplicara para ordenar los datos.
	 * @return Lista.
	 */
	public List<?> findByX(Class<?> oClass, TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		Criteria oCriteria = getSession().createCriteria(oClass);
		for(Entry<?,?> e : criteriosBusqueda.entrySet()) {
			if(e.getValue() instanceof String)
//				if(oClass==Promocion.class && (e.getKey().equals("rutas") || e.getKey().equals("servicios") || e.getKey().equals("puntoVenta") || e.getKey().equals("canalVenta")
//						|| e.getKey().equals("tarjetaCredito"))){
//					oCriteria.add(Restrictions.in((String) e.getKey(), new String[]{e.getValue().toString()}));
//				}else{
					oCriteria.add(Restrictions.ilike((String) e.getKey(), e.getValue()));
//				}
			else
				oCriteria.add(Restrictions.like((String) e.getKey(), e.getValue()));
		}
		if(criteriosOrdenar!=null){
			for(String ordenar : criteriosOrdenar){
				oCriteria.addOrder(Order.asc(ordenar));
			}
		}
		oCriteria.setMaxResults(1000);
		return oCriteria.list();		
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.GenericDAO#findByX(java.lang.Class, java.lang.String, java.lang.String[], java.util.List)
	 */
	@Override
	public List<?> findByX(Class<?> oClass, String campo, Object[] criterios, List<String> criteriosOrdenar, String estadoRegistro) {
		Criteria oCriteria = getSession().createCriteria(oClass);
		oCriteria.add(Restrictions.in(campo, criterios));
		oCriteria.add(Restrictions.eq("estadoRegistro", estadoRegistro));
		if(criteriosOrdenar!=null){
			for(String ordenar : criteriosOrdenar){
				oCriteria.addOrder(Order.asc(ordenar));
			}
		}
		oCriteria.setMaxResults(1000);
		return oCriteria.list();
	}	
	
	/**
	 * Obtiene la fecha y hora del servidor.
	 * @return
	 */
	public String dateServer(){
		String sql="SELECT TO_CHAR(SYSDATE,'dd/mm/yyyy hh24:MI:ss') FROM DUAL";
		List<?> result = getSession().createSQLQuery(sql).list();
		String sdateServer=result.get(0).toString();
		
		return sdateServer;
	}
	

	
	
//	/**
//	 * Realiza la busqueda por los criterios pasados como parametros y los ordena.
//	 * @param oClass				: Clase a la cualse le aplicara el criterio.
//	 * @param criteriosBusqueda		: Criterios que se aplicaran para la busqueda.
//	 * @param criteriosOrdenar		: Criterios que se aplicara para ordenar los datos.
//	 * @return Lista.
//	 */
//	public List<?> findByX_Order(Class<?> oClass, TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
//		Criteria oCriteria = getSession().createCriteria(oClass);
//		for(Entry<?,?> e : criteriosBusqueda.entrySet()) {
//			if(e.getValue() instanceof String) 
//				oCriteria.add(Restrictions.ilike((String) e.getKey(), e.getValue()));
//			else
//				oCriteria.add(Restrictions.like((String) e.getKey(), e.getValue()));
//		}
//		for(String ordenar : criteriosOrdenar){
//			oCriteria.addOrder(Order.asc(ordenar));
//		}
//		return oCriteria.list();
//	}	
	
	public void deleted(Class<?> oClass, Long id){
		StringBuilder hql = new StringBuilder();
		
		hql.append("DELETE " + oClass.getSimpleName() + " ");
		hql.append("WHERE id = ?");
		
		getSession().createQuery(hql.toString()).setLong(0, id).executeUpdate();
	}
	
	public void inactivate(Class<?> oClass, Long id) {
		
		
		
		StringBuilder hql = new StringBuilder();
		
		hql.append("UPDATE " +  oClass.getSimpleName() + " ");
		hql.append("SET estadoRegistro = 'I' ");
		hql.append("WHERE id = ?");

		getSession().createQuery(hql.toString()).setLong(0, id).executeUpdate();
	}
	
	public void activate(Class<?> oClass, Long id) {
		StringBuilder hql = new StringBuilder();
		
		hql.append("UPDATE " +  oClass.getSimpleName() + " ");
		hql.append("SET estadoRegistro = 'A' ");
		hql.append("WHERE id = ?");

		getSession().createQuery(hql.toString()).setLong(0, id).executeUpdate();
	}
}
