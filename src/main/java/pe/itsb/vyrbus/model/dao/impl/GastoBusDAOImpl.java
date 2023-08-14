package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.GastoBus;
import pe.itsb.vyrbus.model.dao.GastoBusDAO;

public class GastoBusDAOImpl extends GenericDAOImpl implements GastoBusDAO {

	@Override
	public void guardar(GastoBus gastoBus) {
		super.save(gastoBus);

	}


	@Override
	public void delete(Long idLiquidacionBus) {
		String sql= "DELETE FROM vrtgasbus WHERE LiqBus_ID="+idLiquidacionBus;

		getSession().createSQLQuery(sql).executeUpdate();
	}


	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<GastoBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<GastoBus>) super.findByX(GastoBus.class, criteriosBusqueda, criteriosOrdenar);
	}



}
