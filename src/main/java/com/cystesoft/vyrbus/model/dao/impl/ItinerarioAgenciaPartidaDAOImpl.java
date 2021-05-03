package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaPartidaDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

@SuppressWarnings("unchecked")
public class ItinerarioAgenciaPartidaDAOImpl extends GenericDAOImpl implements ItinerarioAgenciaPartidaDAO {

	@Override
	public ArrayList<ItinerarioAgenciaPartida> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<ItinerarioAgenciaPartida>) super.findByX(ItinerarioAgenciaPartida.class, criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public ItinerarioAgenciaPartida buscarPorId(Long id) {
		return (ItinerarioAgenciaPartida) super.findById(ItinerarioAgenciaPartida.class, id);
	}


	@Override
	public void delete(Long idItinerario, Integer idLocalidad) throws Exception {
		String sql="DELETE FROM vrtitiagepar WHERE vrtitiagepar.itinerario_id=" + idItinerario + " AND vrtitiagepar.localidad_id=" + idLocalidad;
		getSession().createSQLQuery(sql).executeUpdate();		
	}
	/*
	 * (non-Javadoc)
	 * @see com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaPartidaDAO#buscarAgenciasPartida(java.lang.Long, java.lang.String, java.lang.String)
	 */
	public List<ItinerarioAgenciaPartida> buscarAgenciasPartida(Long idItinerario, String estado, String strLocalidad)throws Exception{
		String sql = "SELECT iap.itinerario_id, iap.agencia_id, a.c_denominacion, iap.c_horpar, a.c_nomcor, l.localidad_id, l.c_denominacion " +
				"FROM vrtitiagepar iap " +
				"INNER JOIN vrmagencia a ON a.agencia_id=iap.agencia_id " +
				"INNER JOIN vrmlocalidad l ON l.localidad_id=iap.localidad_id " +
				"WHERE iap.itinerario_id="+idItinerario+" AND iap.c_estreg IN ('"+Constantes.VALUE_ACTIVO+"','"+estado+"') AND iap.localidad_id IN("+strLocalidad+")";
		List<?> result = getSession().createSQLQuery(sql).list();
		List<ItinerarioAgenciaPartida> lstResult = new ArrayList<ItinerarioAgenciaPartida>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			ItinerarioAgenciaPartida itAgePartida = new ItinerarioAgenciaPartida();
			Itinerario itinerario =  new Itinerario(((BigDecimal)obj[0]).longValue());
			itAgePartida.setItinerario(itinerario);
			Agencia agencia = new Agencia(((BigDecimal)obj[1]).intValue());
			agencia.setDenominacion(obj[2].toString());
			agencia.setNombreCorto(obj[4]==null?null:obj[4].toString());
			itAgePartida.setAgencia(agencia);
			itAgePartida.setHoraPartida(obj[3].toString());
			Localidad localidad = new Localidad();
			localidad.setId(((BigDecimal)obj[5]).intValue());
			localidad.setDenominacion(obj[6].toString());
			itAgePartida.setLocalidad(localidad);
			lstResult.add(itAgePartida);
		}
		return lstResult;
	}

}
