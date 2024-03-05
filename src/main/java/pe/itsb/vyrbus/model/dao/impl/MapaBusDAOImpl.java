/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Avalos
 * Fecha		: 07/07/2012
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.MapaBus;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.dao.MapaBusDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 *
 * @author Jos� Avalos Sullo
 * @since JDK1.6
 */
public class MapaBusDAOImpl extends GenericDAOImpl implements MapaBusDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.MapaBusDAO#buscarMapaBus(java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<MapaBus> buscarMapaBus(Integer idServicio, String estado)throws Exception {
//		String sql = "SELECT mb.mapabus_id, s.servicio_id, s.c_denominacion, s.n_numpis pisoServicio, s.n_numasipis1, s.n_numfilpis1, s.n_numcolpis1, " +
//				"s.n_numasipis2, s.n_numfilpis2, s.n_numcolpis2, mb.c_pathimg, mb.n_tipobj, mb.n_numasi, mb.n_numfil, mb.n_numcol, mb.n_numpis, ta.tipasi_id, ta.c_denominacion tipoasiento " +
//				"FROM vrtmapabus mb " +
//				"LEFT JOIN VRMTIPASI ta ON (ta.tipasi_id=mb.tipasi_id) "+
//				"INNER JOIN vrmservicio s ON s.servicio_id=mb.servicio_id " +
//				"WHERE mb.servicio_id="+idServicio+" AND mb.c_estreg IN ('"+Constantes.VALUE_ACTIVO+"', '"+estado+"') " +
//				"ORDER BY mb.n_numfil, mb.n_numcol";

		String sql = "SELECT mb.mapabus_id, s.servicio_id, s.c_denominacion, s.n_numpis pisoServicio, s.n_numasipis1, s.n_numfilpis1, s.n_numcolpis1, " +
				"s.n_numasipis2, s.n_numfilpis2, s.n_numcolpis2, mb.c_pathimg, mb.n_tipobj, mb.n_numasi, mb.n_numfil, mb.n_numcol, mb.n_numpis, mb.n_zonbus " +
//				+ "ta.tipasi_id, ta.c_denominacion tipoasiento " +
				"FROM vrtmapabus mb " +
//				"LEFT JOIN VRMTIPASI ta ON (ta.tipasi_id=mb.tipasi_id) "+
				"INNER JOIN vrmservicio s ON s.servicio_id=mb.servicio_id " +
				"WHERE mb.servicio_id="+idServicio+" AND mb.c_estreg IN ('"+Constantes.VALUE_ACTIVO+"', '"+estado+"') " +
				"ORDER BY mb.n_numfil, mb.n_numcol";


		log.info(sql);

		try{
			List<?> result = getSession().createSQLQuery(sql).list();
			List<MapaBus> lstResult = new ArrayList<>();

			for(int i=0; i<result.size(); i++){
				Object[] obj = (Object[])result.get(i);
				MapaBus mapaBus = new MapaBus();
				mapaBus.setId(((BigDecimal)obj[0]).intValue());
				Servicio servicio = new Servicio();
				servicio.setId(((BigDecimal)obj[1]).intValue());
				servicio.setDenominacion(obj[2].toString());
				servicio.setNumeroPisos(((BigDecimal)obj[3]).intValue());
				servicio.setNumeroAsientosPiso1(((BigDecimal)obj[4]).intValue());
				servicio.setNumeroFilasPiso1(((BigDecimal)obj[5]).intValue());
				servicio.setNumeroColumnasPiso1(((BigDecimal)obj[6]).intValue());
				servicio.setNumeroAsientosPiso2(obj[7]==null?null:((BigDecimal)obj[7]).intValue());
				servicio.setNumeroFilasPiso2(obj[8]==null?null:((BigDecimal)obj[8]).intValue());
				servicio.setNumeroColumnasPiso2(obj[9]==null?null:((BigDecimal)obj[9]).intValue());
				mapaBus.setServicio(servicio);
//				ObjetoBus objetoBus = new ObjetoBus();
//				objetoBus.setId(((BigDecimal)obj[3]).intValue());
//				objetoBus.setDenominacion(obj[4].toString());
//				objetoBus.setPath(obj[5].toString());
//				objetoBus.setTipoObjeto(((BigDecimal)obj[6]).intValue());
//				mapaBus.setObjetoBus(objetoBus);
				mapaBus.setPathImagen(obj[10].toString());
				mapaBus.setTipoObjeto(((BigDecimal)obj[11]).intValue());
				mapaBus.setNumeroAsiento(obj[12]==null?null:((BigDecimal)obj[12]).intValue());

				mapaBus.setNumeroFila(((BigDecimal)obj[13]).intValue());
				mapaBus.setNumeroColumna(((BigDecimal)obj[14]).intValue());
				mapaBus.setNumeroPiso(((BigDecimal)obj[15]).intValue());
				mapaBus.setNumeroZona(obj[16]==null?null:((BigDecimal)obj[16]).intValue());
//				if(obj[16]!=null){
//					TipoAsiento tipoAsiento=new TipoAsiento();
//					tipoAsiento.setId(((BigDecimal)obj[16]).intValue());
//					tipoAsiento.setDenominacion(obj[17].toString());
//					mapaBus.setTipoAsiento(tipoAsiento);
//				}

				lstResult.add(mapaBus);
			}
			return lstResult;
		}catch(Exception ex){
			log.error(ex);
			throw new Exception(ex);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MapaBusDAO#buscarServiciosWithMapa()
	 */
	@Override
	public List<MapaBus> buscarServiciosWithMapa()throws Exception{
		String hql = "SELECT DISTINCT(servicio.id) FROM MapaBus WHERE estadoRegistro='"+Constantes.VALUE_ACTIVO+"'";

		log.info(hql);
		List<?> result = getSession().createQuery(hql).list();
		List<MapaBus> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Integer idServicio = (Integer)result.get(i);
			MapaBus mapaBus = new MapaBus();
			Servicio servicio = new Servicio();
			servicio.setId(idServicio);
			mapaBus.setServicio(servicio);
			lstResult.add(mapaBus);
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.MapaBusDAO#deleteMapaBus(java.lang.Integer)
	 */
	@Override
	public int deleteMapaBus(Integer idServicio) throws Exception {
		int result = Constantes.FAILURE;
		String hql = "DELETE FROM MapaBus WHERE servicio.id="+idServicio;
		getSession().createQuery(hql).executeUpdate();
		result = Constantes.CORRECT;
		return result;
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.MapaBusDAO#buscarMapaBusHorizontal(java.lang.Integer, java.lang.String)
	 */
	@Override
	public List<MapaBus> buscarMapaBusHorizontal(Integer idServicio, String estado) throws Exception {
		String sql = "SELECT mb.mapabus_id, s.servicio_id, s.c_denominacion, s.n_numpis pisoServicio, s.n_numasipis1, s.n_numfilpis1, s.n_numcolpis1, " +
				"s.n_numasipis2, s.n_numfilpis2, s.n_numcolpis2, mb.c_pathimg, mb.n_tipobj, mb.n_numasi, mb.n_numfil, mb.n_numcol, mb.n_numpis, mb.n_zonbus " +
				"FROM vrtmapabus mb " +
				"INNER JOIN vrmservicio s ON s.servicio_id=mb.servicio_id " +
				"WHERE mb.servicio_id="+idServicio+" AND mb.c_estreg IN ('"+Constantes.VALUE_ACTIVO+"', '"+estado+"') " +
				"ORDER BY mb.n_numfil, mb.n_numcol";


		log.info(sql);

		try{
			List<?> result = getSession().createSQLQuery(sql).list();
			List<MapaBus> lstResult = new ArrayList<>();

			for(int i=0; i<result.size(); i++){
				Object[] obj = (Object[])result.get(i);
				MapaBus mapaBus = new MapaBus();
				mapaBus.setId(((BigDecimal)obj[0]).intValue());
				Servicio servicio = new Servicio();
				servicio.setId(((BigDecimal)obj[1]).intValue());
				servicio.setDenominacion(obj[2].toString());
				servicio.setNumeroPisos(((BigDecimal)obj[3]).intValue());
				servicio.setNumeroAsientosPiso1(((BigDecimal)obj[4]).intValue());
				servicio.setNumeroFilasPiso1(((BigDecimal)obj[6]).intValue());
				servicio.setNumeroColumnasPiso1(((BigDecimal)obj[5]).intValue());
				servicio.setNumeroAsientosPiso2(obj[7]==null?null:((BigDecimal)obj[7]).intValue());
				servicio.setNumeroFilasPiso2(obj[9]==null?null:((BigDecimal)obj[9]).intValue());
				servicio.setNumeroColumnasPiso2(obj[8]==null?null:((BigDecimal)obj[8]).intValue());
				mapaBus.setServicio(servicio);
				mapaBus.setPathImagen(obj[10].toString());
				mapaBus.setTipoObjeto(((BigDecimal)obj[11]).intValue());
				mapaBus.setNumeroAsiento(obj[12]==null?null:((BigDecimal)obj[12]).intValue());
				if (((BigDecimal)obj[15]).intValue()== 0 ){
                    //servicio.setNumeroColumnasPiso1(((BigDecimal)obj[6]).intValue());
                    mapaBus.setNumeroFila(((BigDecimal)obj[6]).intValue()-(1+ ((BigDecimal)obj[14]).intValue()));
                    mapaBus.setNumeroColumna(((BigDecimal)obj[13]).intValue());
//					mapaBus.setNumeroFila(((BigDecimal)obj[5]).intValue()-(1+ ((BigDecimal)obj[14]).intValue()));
//                    mapaBus.setNumeroColumna(((BigDecimal)obj[6]).intValue());
                }
                if ( ((BigDecimal)obj[15]).intValue() == 1 ){
                    mapaBus.setNumeroFila(((BigDecimal)obj[9]).intValue()-(1+((BigDecimal)obj[14]).intValue()));
                    mapaBus.setNumeroColumna(((BigDecimal)obj[13]).intValue());
                }
				mapaBus.setNumeroPiso(((BigDecimal)obj[15]).intValue());
				mapaBus.setNumeroZona(obj[16]==null?null:((BigDecimal)obj[16]).intValue());

				lstResult.add(mapaBus);
			}
			return lstResult;
		}catch(Exception ex){
			log.error(ex);
			throw new Exception(ex);
		}
	}

	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.model.dao.MapaBusDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<MapaBus> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)
			throws Exception {
		// TODO Auto-generated method stub
		return (ArrayList<MapaBus>) super.findByX(MapaBus.class, criteriosBusqueda, criteriosOrdenar);
	}
}
