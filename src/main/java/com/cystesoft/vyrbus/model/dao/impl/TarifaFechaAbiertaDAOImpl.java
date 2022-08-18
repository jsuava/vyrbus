/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 03/04/2014
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cystesoft.vyrbus.model.bean.Localidad;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.bean.TarifaFechaAbierta;
import com.cystesoft.vyrbus.model.dao.TarifaFechaAbiertaDAO;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.MyTime;

/**
 * @author JABANTO
 *
 */
public class TarifaFechaAbiertaDAOImpl extends GenericDAOImpl implements TarifaFechaAbiertaDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaFechaAbiertaDAO#buscarXRutaAndServicio(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public Double buscarTarifa(Integer idRuta,Integer idServicio) throws Exception {

		String sql="SELECT NVL(tf.n_monto,0)as monto,tf.d_Feccad FROM VRTTARIFAFA tf " +
				   "WHERE tf.ruta_id="+idRuta+" " +
				   		"AND tf.servicio_id="+idServicio+" " +
				   		"AND tf.d_fecsus IS NULL "+
				   		"AND tf.c_estreg='A' " +
				   	"ORDER BY tf.tarifafa_id ";
		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();

		Date fechaHoraActual=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
		Double tarifa=.00;

		for (Object element : result) {
			Object[] obj=(Object[])element;

			Date fechaCaducidad=(Date)obj[1];

			if(fechaCaducidad.getTime()>=fechaHoraActual.getTime())
				tarifa=((BigDecimal)obj[0]).doubleValue();
			else
				tarifa=.00;
		}

		return tarifa;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaFechaAbiertaDAO#buscarTarifas(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<TarifaFechaAbierta> buscarTarifas(Integer idOrigen, Integer idDestino, Integer idServicio) throws Exception {
		String sql="SELECT tfa.tarifafa_id "+
					      ",tfa.ruta_id "+
					      ",tfa.servicio_id "+
					      ",tfa.n_monto " +
					      ",tfa.d_Feccad " +
					      ",tfa.d_fecact " +
					      ",tfa.d_fecsus " +
					      ",tfa.c_estreg " +
					      ",tfa.audfecins " +
					      ",tfa.audusuins " +
					      ",tfa.audipinse " +
					      ",tfa.audfecmod " +
					      ",tfa.audusumod " +
					      ",tfa.audipmod "+
					"FROM VRTTARIFAFA tfa " +
						"INNER JOIN VRMRUTA r ON (r.ruta_id=tfa.ruta_id) "+
					"WHERE r.localidad_idorigen=NVL("+idOrigen+",r.localidad_idorigen) " +
						 "AND r.localidad_iddestino=NVL("+idDestino+",r.localidad_iddestino) "+
					     "AND tfa.servicio_id=NVL("+idServicio+",tfa.servicio_id) "+
					     "AND tfa.d_Fecsus IS NULL "+
					     "AND tfa.c_estreg='A'";
		log.info(sql);
		List<?>lstResult=getSession().createSQLQuery(sql).list();
		List<TarifaFechaAbierta>lstTarifasFA=new ArrayList<>();
		for(int i=0; i<lstResult.size();i++){
			Object[] obj=(Object[])lstResult.get(i);

			TarifaFechaAbierta tarifaFechaAbierta=new TarifaFechaAbierta();
			tarifaFechaAbierta.setId(((BigDecimal)obj[0]).longValue());

			Ruta ruta=new Ruta();
			ruta.setId(((BigDecimal)obj[1]).intValue());

			Servicio servicio=new Servicio();
			servicio.setId(((BigDecimal)obj[2]).intValue());

			tarifaFechaAbierta.setRuta(ruta);
			tarifaFechaAbierta.setServicio(servicio);
			tarifaFechaAbierta.setMonto(((BigDecimal)obj[3]).doubleValue());
			tarifaFechaAbierta.setFechaCaducidad((Date)obj[4]);
			tarifaFechaAbierta.setFechaActivacion((Date)obj[5]);
			tarifaFechaAbierta.setFechaSuspencion((Date)obj[6]);
			tarifaFechaAbierta.setEstadoRegistro(obj[7].toString());
			tarifaFechaAbierta.setFechaInsercion(obj[8]!=null?(Date)obj[8]:null);
			tarifaFechaAbierta.setUsuarioInsercion(obj[9]!=null?obj[9].toString():null);
			tarifaFechaAbierta.setIpInsercion(obj[10]!=null?obj[10].toString():null);
			tarifaFechaAbierta.setFechaModificacion(obj[11]!=null?(Date)obj[11]:null);
			tarifaFechaAbierta.setUsuarioModificacion(obj[12]!=null?obj[12].toString():null);
			tarifaFechaAbierta.setIpModificacion(obj[13]!=null?obj[13].toString():null);

			/* Valida que la fecha de caducidad de la tarifa a fecha abierta */
			Date fechaHoraActual=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
			if(tarifaFechaAbierta.getFechaCaducidad().getTime()>=fechaHoraActual.getTime())
				lstTarifasFA.add(tarifaFechaAbierta);
		}
		return lstTarifasFA;
	}

	@Override
	public List<TarifaFechaAbierta> listarTarifasFA(Integer idOrigen,Integer idDestino, Integer idServicio)throws Exception{
		String sql="SELECT   " +
				   "    fa.tarifafa_id IDT,  " +
				   "    s.servicio_id IDS, " +
				   "    s.c_denominacion SERVICIO,  " +
			       "	r.ruta_id, " +
			       "	r.c_origen, " +
			       "	r.c_destino, " +
				   "    fa.n_monto TARIFA, " +
				   "    fa.d_fecact ACTIVACION,  " +
				   "    fa.d_feccad CADUCIDAD, " +
			       "	r.localidad_idorigen, " +
			       "	r.localidad_iddestino " +
					"FROM vrttarifafa fa  " +
					"INNER JOIN vrmruta r ON (fa.ruta_id = r.ruta_id) " +
					"INNER JOIN vrmservicio s ON (fa.servicio_id = s.servicio_id) " +
					"WHERE  " +
					"	s.servicio_id = NVL(" + idServicio + ", s.servicio_id) " +
					"	AND r.localidad_idorigen = NVL(" + idOrigen + ", r.localidad_idorigen) " +
					"	AND r.localidad_iddestino = NVL(" + idDestino + ", r.localidad_iddestino) " +
					"	AND fa.c_estreg='A' " +
					"ORDER BY s.c_denominacion, r.c_origen||'-'||r.c_destino";

		log.info(sql);
		List<?>lstResult=getSession().createSQLQuery(sql).list();
		List<TarifaFechaAbierta>lstTarifasFA=new ArrayList<>();
		for(int i=0; i<lstResult.size();i++){
			Object[] obj=(Object[])lstResult.get(i);

			TarifaFechaAbierta tarifaFechaAbierta=new TarifaFechaAbierta();
			tarifaFechaAbierta.setId(((BigDecimal)obj[0]).longValue());

			Servicio servicio=new Servicio();
			servicio.setId(((BigDecimal)obj[1]).intValue());
			servicio.setDenominacion(obj[2]!=null?obj[2].toString():null);

			Ruta ruta=new Ruta();
			ruta.setId(((BigDecimal)obj[3]).intValue());
			ruta.setOrigen(obj[4]!=null?obj[4].toString():null);
			ruta.setDestino(obj[5]!=null?obj[5].toString():null);

			Localidad origen = new Localidad();
			origen.setId(((BigDecimal)obj[9]).intValue());
			Localidad destino = new Localidad();
			destino.setId(((BigDecimal)obj[10]).intValue());
			ruta.setLocalidadOrigen(origen);
			ruta.setLocalidadDestino(destino);

			tarifaFechaAbierta.setRuta(ruta);
			tarifaFechaAbierta.setServicio(servicio);
			tarifaFechaAbierta.setMonto(((BigDecimal)obj[6]).doubleValue());
			tarifaFechaAbierta.setFechaActivacion((Date)obj[7]);
			tarifaFechaAbierta.setFechaCaducidad((Date)obj[8]);

			/* Valida que la fecha de caducidad de la tarifa a fecha abierta */
			Date fechaHoraActual=Constantes.FORMAT_DATE_TIME_24H.parse(new MyTime().dateServer());
			if(tarifaFechaAbierta.getFechaCaducidad().getTime()>=fechaHoraActual.getTime())
				lstTarifasFA.add(tarifaFechaAbierta);
		}
		return lstTarifasFA;
	}



	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaFechaAbiertaDAO#guardar(com.tepsa.sisvyr.model.bean.TarifaFechaAbierta)
	 */
	@Override
	public void guardar(TarifaFechaAbierta tarifaFechaAbierta) throws Exception {
		// TODO Auto-generated method stub
		super.save(tarifaFechaAbierta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaFechaAbiertaDAO#actualizar(com.tepsa.sisvyr.model.bean.TarifaFechaAbierta)
	 */
	@Override
	public void actualizar(TarifaFechaAbierta tarifaFechaAbierta)throws Exception {
		// TODO Auto-generated method stub
		super.update(tarifaFechaAbierta);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TarifaFechaAbiertaDAO#inactivate(java.lang.Long)
	 */
	@Override
	public void inactivate(Long id) throws Exception {
		// TODO Auto-generated method stub
		super.inactivate(TarifaFechaAbierta.class, id);
	}

}
