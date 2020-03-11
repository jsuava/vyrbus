/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Sexo;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.dao.PasajeroDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class PasajeroDAOImpl extends GenericDAOImpl implements PasajeroDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PasajeroDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Pasajero> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Pasajero>) super.findByEstadoRegistro(Pasajero.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PasajeroDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Pasajero> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Pasajero>) super.findByX(Pasajero.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PasajeroDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Pasajero buscarPorId(Long id) {
//		Pasajero pasajero=(Pasajero) super.findById(Pasajero.class, id);
//		
//		try{
//			/**Valida datos del pax con los de la Reniec- 05/09/2013 - jabanto*/
//			pasajero=ServiceLocator.getReniecManager().validarPaxConReniec(pasajero);
//		}catch (Exception ex){}
//		
		return (Pasajero) super.findById(Pasajero.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PasajeroDAO#guardar(com.tepsa.sisvyr.domain.Pasajero)
	 */
	@Override
	public void guardar(Pasajero pasajero) {
//		if(pasajero.getValidadoReniec()==null)//Solamente para asegurarse
//			pasajero.setValidadoReniec(0);
		super.save(pasajero);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PasajeroDAO#actualizar(com.tepsa.sisvyr.domain.Pasajero)
	 */
	@Override
	public void actualizar(Pasajero pasajero) {
		super.update(pasajero);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.PasajeroDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Pasajero.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.PasajeroDAO#buscarPorFullTextIndex(java.lang.String[])
	 */
	@Override
	public ArrayList<Pasajero> buscarPorFullTextIndex(String[] nombres) throws Exception {
		String criterio = "";
		for(String valor : nombres){
			criterio = (criterio.equals("")?"":(criterio+" & ")) + valor+"%";
		}
		
		String sql = "SELECT p.pasajero_id, p.c_apepat, c_apemat, c_nombre, td.tipdoc_id, td.c_denominacion, p.c_numdoc, p.n_kilometros, " +
				"sx.sexo_id, sx.c_denominacion, p.c_nomape, p.c_fecnac " +
				"FROM vrmpasajero p " +
				"INNER JOIN vrmtipdoc td ON td.tipdoc_id=p.tipdoc_id " +
				"INNER JOIN vrmsexo sx ON sx.sexo_id=p.sexo_id " +
				"WHERE CATSEARCH(c_nomape, '"+criterio+"', null) > 0 AND p.c_estreg='"+Constantes.VALUE_ACTIVO+"' AND ROWNUM<=1000 ORDER BY p.c_nomape";
		log.info(sql);
		
		List<?> result = getSession().createSQLQuery(sql).list(); 
		ArrayList<Pasajero> lstResult = new ArrayList<Pasajero>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			Pasajero pasajero = new Pasajero();
			pasajero.setId(((BigDecimal)obj[0]).longValue());
			pasajero.setApellidoPaterno(obj[1].toString());
			pasajero.setApellidoMaterno(obj[2]==null?null:obj[2].toString());
			pasajero.setNombre(obj[3].toString());
			TipoDocumento tipoDocumento = new TipoDocumento();
			tipoDocumento.setId(((BigDecimal)obj[4]).intValue());
			tipoDocumento.setDenominacion(obj[5].toString());
			pasajero.setTipoDocumento(tipoDocumento);
			pasajero.setNumeroDocumento(obj[6]==null?"":obj[6].toString());
			pasajero.setKilometros(((BigDecimal)obj[7]).doubleValue());
			Sexo sexo= new Sexo();
			sexo.setId(((BigDecimal)obj[8]).intValue());
			sexo.setDenominacion(obj[9].toString());
			pasajero.setSexo(sexo);
			pasajero.setNombresApellidos(obj[10]!=null?obj[10].toString(): "");
			pasajero.setFechaNacimiento(obj[11]!=null?obj[11].toString():"");
			lstResult.add(pasajero);
		}
		return lstResult;
	}
	
}