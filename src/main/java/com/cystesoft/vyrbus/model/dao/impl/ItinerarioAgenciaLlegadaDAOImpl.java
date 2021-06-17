package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaLlegadaDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

@SuppressWarnings("unchecked")
public class ItinerarioAgenciaLlegadaDAOImpl extends GenericDAOImpl implements ItinerarioAgenciaLlegadaDAO {
	@Override
	public ArrayList<ItinerarioAgenciaLlegada> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<ItinerarioAgenciaLlegada>) super.findByX(ItinerarioAgenciaLlegada.class,criteriosBusqueda, criteriosOrdenar);
	}

	@Override
	public ItinerarioAgenciaLlegada buscarPorId(Long id) {
		return (ItinerarioAgenciaLlegada) super.findById(ItinerarioAgenciaLlegada.class, id);
	}

	@Override
	public void delete(Long idItinerario, Integer idLocalidad) throws Exception {
		String sql="DELETE FROM vrtitiagelle WHERE vrtitiagelle.itinerario_id=" + idItinerario+ " AND vrtitiagelle.localidad_id=" + idLocalidad;
		
		 getSession().createSQLQuery(sql).executeUpdate();
		
	}
	@Override
	public List<ItinerarioAgenciaLlegada> buscarAgenciasLlegada(Long idItinerario, String estado, String strLocalidad)throws Exception{
		String sql = "SELECT ial.itinerario_id, ial.agencia_id, a.c_denominacion, ial.c_horlle, a.c_nomcor, l.localidad_id, l.c_denominacion " +
				"FROM vrtitiagelle ial " +
				"INNER JOIN vrmagencia a ON a.agencia_id=ial.agencia_id " +
				"INNER JOIN vrmlocalidad l ON l.localidad_id=ial.localidad_id " +
				"WHERE ial.itinerario_id="+idItinerario+" AND ial.c_estreg IN ('"+Constantes.VALUE_ACTIVO+"','"+estado+"') AND ial.localidad_id IN("+strLocalidad+")";
		List<?> result = getSession().createSQLQuery(sql).list();
		List<ItinerarioAgenciaLlegada> lstResult = new ArrayList<ItinerarioAgenciaLlegada>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			ItinerarioAgenciaLlegada itAgeLlegada = new ItinerarioAgenciaLlegada();
			Itinerario itinerario =  new Itinerario(((BigDecimal)obj[0]).longValue());
			itAgeLlegada.setItinerario(itinerario);
			Agencia agencia = new Agencia(((BigDecimal)obj[1]).intValue());
			agencia.setDenominacion(obj[2].toString());
			agencia.setNombreCorto(obj[4]==null?null:obj[4].toString());
			itAgeLlegada.setAgencia(agencia);
			itAgeLlegada.setHoraLlegada(obj[3].toString());
			Localidad localidad = new Localidad();
			localidad.setId(((BigDecimal)obj[5]).intValue());
			localidad.setDenominacion(obj[6].toString());
			itAgeLlegada.setLocalidad(localidad);
			lstResult.add(itAgeLlegada);
		}
		return lstResult;
	}
}
