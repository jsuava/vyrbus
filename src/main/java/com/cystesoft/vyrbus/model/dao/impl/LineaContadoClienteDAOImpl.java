package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.LineaContadoCliente;
import com.cystesoft.vyrbus.model.dao.LineaContadoClienteDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * 
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class LineaContadoClienteDAOImpl extends GenericDAOImpl implements LineaContadoClienteDAO {

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaContadoClienteDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<LineaContadoCliente> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<LineaContadoCliente>)super.findByEstadoRegistro(LineaContadoCliente.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaContadoClienteDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<LineaContadoCliente> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<LineaContadoCliente>)super.findByX(LineaContadoCliente.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaContadoClienteDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public LineaContadoCliente buscarPorId(Long id) {
		return (LineaContadoCliente)super.findById(LineaContadoCliente.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaContadoClienteDAO#guardar(com.tepsa.sisvyr.model.bean.LineaContadoCliente)
	 */
	@Override
	public void guardar(LineaContadoCliente lineaContadoCliente) {
		super.save(lineaContadoCliente);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaContadoClienteDAO#actualizar(com.tepsa.sisvyr.model.bean.LineaContadoCliente)
	 */
	@Override
	public void actualizar(LineaContadoCliente lineaContadoCliente) {
		super.update(lineaContadoCliente);		
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaContadoClienteDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(LineaContadoCliente.class, id);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.LineaContadoClienteDAO#validaDescuenteCliente(java.lang.Long)
	 */
	@Override
	public LineaContadoCliente validaDescuentoCliente(Long idCliente){
		String sql="SELECT lcd.linconcli_id "+
					"FROM Vrtlinconcli lcd " +
					"INNER JOIN vrtcarcli cc ON (cc.carcli_id=lcd.carcli_id) "+  
					"WHERE cc.cliente_id="+idCliente+" AND lcd.c_estlincon='"+Constantes.ESTADOSOL_ACTIVA+"' ";
		
		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		LineaContadoCliente lineaContadoCliente=null;
		if(result.size()>0){
			String id= result.get(0).toString();
			lineaContadoCliente=buscarPorId(Long.valueOf(id));
		}
		
		return lineaContadoCliente;
	}

}
