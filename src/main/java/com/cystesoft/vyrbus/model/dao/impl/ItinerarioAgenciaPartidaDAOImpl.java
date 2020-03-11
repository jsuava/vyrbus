package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.ItinerarioAgenciaPartida;
import com.cystesoft.vyrbus.model.dao.ItinerarioAgenciaPartidaDAO;

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
	public void delete(Long idItinerario) throws Exception {
		String sql="DELETE FROM VRTITIAGEPAR WHERE VRTITIAGEPAR.ITINERARIO_ID=" + idItinerario;
		
		 getSession().createSQLQuery(sql).executeUpdate();
		
	}
	

}
