package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaLlegada;
import com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaLlegadaDAO;

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
	public void delete(Long idItinerario) throws Exception {
		String sql="DELETE FROM VRTITIAGELLE WHERE VRTITIAGELLE.ITINERARIO_ID=" + idItinerario;
		
		 getSession().createSQLQuery(sql).executeUpdate();
		
	}

}
