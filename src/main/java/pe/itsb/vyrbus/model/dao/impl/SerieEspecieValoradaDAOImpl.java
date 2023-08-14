/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 20/11/2013
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.SerieEspecieValorada;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.dao.SerieEspecieValoradaDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings("unchecked")
public class SerieEspecieValoradaDAOImpl extends GenericDAOImpl implements SerieEspecieValoradaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SerieEspecieValoradaDAO#buscarPorID(java.lang.String, java.lang.Integer)
	 */
	@Override
	public SerieEspecieValorada buscarPorID(String numeroSerie,Integer idTipoComprobante) throws Exception {
		// TODO Auto-generated method stub
		TipoComprobante tipoComprobante=new TipoComprobante(idTipoComprobante);
		TreeMap<String, Object>criteriosBusqueda=new TreeMap<>();
		criteriosBusqueda.put("numeroSerie", numeroSerie);
		criteriosBusqueda.put("tipoComprobante", tipoComprobante);
		criteriosBusqueda.put("estadoRegistro", Constantes.VALUE_ACTIVO);
		List<SerieEspecieValorada>lisSerie=(List<SerieEspecieValorada>) findByX(SerieEspecieValorada.class, criteriosBusqueda, null);

		SerieEspecieValorada serieEspecieValorada=null;
		if(lisSerie.size()>0){
			serieEspecieValorada=new SerieEspecieValorada();
			serieEspecieValorada=lisSerie.get(0);
		}

		return serieEspecieValorada;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SerieEspecieValoradaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<SerieEspecieValorada> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<SerieEspecieValorada>)super.findByX(SerieEspecieValorada.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SerieEspecieValoradaDAO#guardar(com.tepsa.sisvyr.model.bean.SerieEspecieValorada)
	 */
	@Override
	public void guardar(SerieEspecieValorada serieEspecieValorada)throws Exception {
		// TODO Auto-generated method stub
		super.save(serieEspecieValorada);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.SerieEspecieValoradaDAO#inactivar(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void inactivar(String numeroSerie, Integer idTipoComprobante)throws Exception {
		// TODO Auto-generated method stub

	}

}
