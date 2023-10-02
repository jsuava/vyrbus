package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.Cortesia;
import pe.itsb.vyrbus.model.bean.Empresa;
import pe.itsb.vyrbus.model.bean.MotivoCortesia;
import pe.itsb.vyrbus.model.bean.Pasajero;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.Servicio;
import pe.itsb.vyrbus.model.bean.TipoComprobante;
import pe.itsb.vyrbus.model.bean.TipoFormaPago;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.VentaPasaje;
import pe.itsb.vyrbus.model.dao.CortesiaDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 *
 * @author José Abanto.
 *
 */
@SuppressWarnings("unchecked")
public class CortesiaDAOImpl extends GenericDAOImpl implements CortesiaDAO{

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CorteciaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<Cortesia> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		return (ArrayList<Cortesia>) super.findByEstadoRegistro(Cortesia.class, estado, criterioOrden);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CorteciaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<Cortesia> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		return (ArrayList<Cortesia>) super.findByX(Cortesia.class, criteriosBusqueda, criteriosOrdenar);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CorteciaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public Cortesia buscarPorId(Long id) {
		return (Cortesia) super.findById(Cortesia.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CorteciaDAO#guardar(com.tepsa.sisvyr.model.bean.Cortesia)
	 */
	@Override
	public void guardar(Cortesia cortesia) {
		super.save(cortesia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CorteciaDAO#actualizar(com.tepsa.sisvyr.model.bean.Cortesia)
	 */
	@Override
	public void actualizar(Cortesia cortesia) {
		super.update(cortesia);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CorteciaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Cortesia.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CorteciaDAO#activar(java.lang.Long)
	 */
	@Override
	public void activar(Long id) {
		super.activate(Cortesia.class, id);
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CortesiaDAO#BuscarCortesia(com.tepsa.sisvyr.model.bean.Cortesia)
	 */
	@Override
	public List<Cortesia> BuscarCortesia(Cortesia cortesia) {
		String Where="";

		if (cortesia.getPasajero() !=null)
			Where = Where +" And c.pasajero_ID=" +cortesia.getPasajero().getId();
		if (!(cortesia.getTipoFormaPago() ==null))
			Where = Where + " And c.tipForPag_ID=" +cortesia.getTipoFormaPago().getId();
		if (!(cortesia.getMotivoCortesia()== null ))
			Where =  Where + "  And c.motCor_ID=" +cortesia.getMotivoCortesia().getId();
		if (!(cortesia.getId() ==null))
			Where =  Where + "  And c.Cortesia_ID=" +cortesia.getId();

		String FechaInico = Constantes.FORMAT_DATE.format(cortesia.getFechaInicio());

		String Sql="SELECT c.cortesia_Id, c.motcor_id, tfp.tipforpag_id, r.ruta_id, c.d_fecini, c.d_feccad, c.c_peraut, c.n_porcentaje, c.c_estreg, " +
					       "tfp.c_denominacion as tipoformaPafo, mc.c_denominacion as motivoCortecia, r.c_origen as Origen, r.c_destino as Destino, " +
					       "p.c_apepat, p.c_apemat,p.c_nombre, vp.c_numcontrol, vp.venpas_id, p.pasajero_id, c.audfecins, " +
					       "r.n_Puntaje, vpc.c_numboleto, e.empresa_id, e.c_nomcor " +
			       "FROM vrtcortesia c "+
				       "INNER JOIN vrtvenpas vp on vp.venpas_id=c.venpas_id " +
				       "INNER JOIN vrmtipforpag tfp on tfp.tipforpag_id=c.tipforpag_id " +
				       "INNER JOIN vrmruta r on r.ruta_id=c.ruta_id " +
				       "LEFT JOIN vrmmotcor mc on mc.motcor_id=c.motcor_id " +
				       "INNER JOIN vrmpasajero p on p.pasajero_id=c.pasajero_id " +
				       "LEFT JOIN vrtvenpas vpc ON vpc.venpas_idref=c.venpas_id " +
				       "INNER JOIN vrmempresa e ON e.empresa_id = vp.empresa_id " +
			       "WHERE c.d_fecini=to_date('"+FechaInico+"','dd/mm/yyyy') "+ Where + " AND c.c_estreg='"+Constantes.VALUE_ACTIVO+"' " +
			       		"AND vp.c_tiptra="+Constantes.TIPO_VENTA_FECHAABIERTA+ " "+
			       "ORDER BY p.c_apepat,tfp.c_denominacion ";
		log.info(Sql);

		List<?> result = getSession().createSQLQuery(Sql).list();
		List<Cortesia> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			Cortesia oCortesia = new Cortesia();

			TipoFormaPago tipoFormaPago = new TipoFormaPago();
			tipoFormaPago.setId(((BigDecimal)obj[2]).intValue());
			tipoFormaPago.setDenominacion(obj[9].toString());


			if(obj[1]!=null){
				MotivoCortesia motivoCortesia = new MotivoCortesia();
				motivoCortesia.setId(((BigDecimal)obj[1]).intValue());
				motivoCortesia.setDenominacion(obj[10].toString());
				oCortesia.setMotivoCortesia(motivoCortesia);
			}

			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[3]).intValue());
			ruta.setOrigen(obj[11].toString());
			ruta.setDestino(obj[12].toString());
			ruta.setPuntaje(obj[20]!=null? (((BigDecimal)obj[20]).intValue()): 0);

			Pasajero pasajero = new Pasajero();
			pasajero.setApellidoPaterno(obj[13].toString());
			pasajero.setApellidoMaterno(obj[14]==null?null:obj[14].toString());
			pasajero.setNombre(obj[15].toString());
			pasajero.setId(((BigDecimal)obj[18]).longValue());

			VentaPasaje ventaPasaje= new VentaPasaje();
			ventaPasaje.setNumeroControl(obj[16].toString());
			ventaPasaje.setId(((BigDecimal)obj[17]).longValue());
			ventaPasaje.setNumeroBoleto(obj[21]==null?"":obj[21].toString());
			Empresa empresa = new Empresa();
			empresa.setId(((BigDecimal)obj[22]).intValue());
			empresa.setNombreCorto(obj[23].toString());
			ventaPasaje.setEmpresa(empresa);

			oCortesia.setId(((BigDecimal)obj[0]).longValue());
			oCortesia.setTipoFormaPago(tipoFormaPago);

			oCortesia.setRuta(ruta);
			oCortesia.setPasajero(pasajero);
			oCortesia.setVentaPasaje(ventaPasaje);
			oCortesia.setFechaInicio(((Date)obj[4]));
			oCortesia.setFechaCaducidad(((Date) obj[5]));

			oCortesia.setPersonaAutoriza(obj[6]!=null?obj[6].toString():"");
			oCortesia.setPorcentaje(obj[7]!=null?((BigDecimal) obj[7]).intValue():Integer.valueOf(0));
			oCortesia.setEstadoRegistro(obj[8].toString());
			oCortesia.setNumeroControl(obj[16].toString());
			oCortesia.setFechaInsercion((Date)obj[19]);

			lstResult.add(oCortesia);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CortesiaDAO#BuscarBolXcumpleaniosPaxFree(java.lang.Long, java.lang.Integer, java.lang.String)
	 */
	@Override
	public ArrayList<Cortesia> BuscarBolXcumpleaniosPaxFree(Long idPasajero,Integer idTipoFormaPago) throws Exception {
		String sql ="SELECT vp.d_fecpar as FechaViaje, vp.c_numboleto as Boleto,r.c_origen as Origen, r.c_destino as Destino, "+ //0-3
					       "s.c_denominacion as Servicio, u.c_apepat as ApPaterno, u.c_apemat as ApMaterno, u.c_nombre as Nombres "+ //4-7
//					       ",to_char(vp.audfecins,'yyyy') as AnioCortesia" + //8
						   ",c.n_aniocum as AnioCortesia " + //8
					       ", vp.audfecins as FechaEmision "+ //9
					"FROM vrtvenpas vp "+
					"INNER JOIN (SELECT MAX(venpas_id)venpas_id, c_numcontrol " +
								 "FROM vrtvenpas WHERE pasajero_id ="+idPasajero+" AND tipforpag_id="+idTipoFormaPago+" GROUP BY c_numcontrol) max_venta "+
							"ON max_venta.venpas_id=vp.venpas_id " +
					"LEFT JOIN vrmservicio s ON (s.servicio_id=vp.servicio_id) "+
					"INNER JOIN vrmusuario u ON (u.usuario_id=vp.usuario_id) "+
					"LEFT JOIN vrtcortesia c ON (c.venpas_id=vp.venpas_id) "+
					"INNER JOIN vrmruta r ON (r.ruta_id=vp.ruta_id) "+
					"WHERE vp.pasajero_id ="+idPasajero+" AND vp.tipforpag_id="+idTipoFormaPago+" "+  // AND vp.d_fecpar=to_date('"+anio+"','yyyy') "+
					" AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					"AND vp.tipmov_id NOT IN ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+","+Constantes.ID_TIPMOV_ANULACION+") "+
					"ORDER BY vp.audfecins desc ";
		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<Cortesia> lstResult = new ArrayList<>();
		for (Object element : result) {
			Object[] obj = (Object[]) element;

			Cortesia cortesia=new Cortesia();
			VentaPasaje ventaPasaje=new VentaPasaje();
			Ruta ruta=new Ruta();
			Servicio servicio=new Servicio();
			Usuario usuario=new Usuario();

			ruta.setOrigen(obj[2].toString());
			ruta.setDestino(obj[3].toString());
			if(obj[4]!=null)
				servicio.setDenominacion(obj[4].toString());
			usuario.setApellidoPaterno(obj[5].toString());
			usuario.setApellidoMaterno(obj[6]!=null?obj[6].toString():"");
			usuario.setNombre(obj[7].toString());
			if(obj[0]!=null)
				ventaPasaje.setFechaPartida((Date)obj[0]);
			if(obj[1]!=null)
				ventaPasaje.setNumeroBoleto(obj[1].toString());
			if(servicio!=null)
				ventaPasaje.setServicio(servicio);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setUsuario(usuario);
			if(obj[8]!=null)
				 cortesia.setAnioCumpleanios(Integer.parseInt(obj[8].toString()));

			cortesia.setFechaInsercion((Date)obj[9]);
			cortesia.setRuta(ruta);
			cortesia.setVentaPasaje(ventaPasaje);

			lstResult.add(cortesia);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CortesiaDAO#cortesiaConfirmada(java.lang.String)
	 */
	@Override
	public boolean cortesiaConfirmada(String numeroControl) throws Exception {
		String sql="SELECT * FROM vrtvenpas vp "+
					"WHERE vp.venpas_id=(SELECT MAX(vps.venpas_id) FROM vrtvenpas vps "+
					                    "WHERE vps.c_numcontrol='"+numeroControl+"' AND vps.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					                    "AND vps.c_tiptra="+Constantes.TIPO_OPERACION_VENTA+" AND vps.tipmov_id not in ("+Constantes.ID_TIPMOV_ANULACION_SISTEMA+","+Constantes.ID_TIPMOV_DEVOLUCION+") "+
					                    "GROUP BY vps.c_numcontrol)";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		if (result.size()>0)return true;
		else
			return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.CortesiaDAO#buscarXIDventa(java.lang.Long)
	 */
	@Override
	public Cortesia buscarXIDventa(Long id) {
		Class<?> oClass=Cortesia.class;
		String hql = "FROM " + oClass.getSimpleName() + " WHERE ventaPasaje ="+id;

		return (Cortesia) getSession().createQuery(hql).uniqueResult();
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.VentaPasajesDAO#buscarVentasXCortesia(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public ArrayList<Cortesia>buscarCortesia(String fecha,String numDocPax, Integer idTipoFormaPago){

		String Sql="SELECT vp.venpas_id, vp.c_numboleto,p.c_nomape, r.c_origen, r.c_destino, sr.c_denominacion, vp.d_fecpar, vp.c_horpar, vp.n_numasiento " + //0-8
							", tfp.c_denominacion as servicio, c.cortesia_id, c.n_aniocum "+ //9-11
							", tc.c_denominacion tipoComprobante "+ //12
			       "FROM vrtcortesia c "+
				       "INNER JOIN  vrtvenpas vp on (vp.venpas_id=c.venpas_id) " +
				       "INNER JOIN vrmtipforpag tfp on (tfp.tipforpag_id=vp.tipforpag_id) " +
				       "INNER JOIN vrmruta r on (r.ruta_id=vp.ruta_id) " +
				       "INNER JOIN vrmservicio sr on (sr.servicio_id=vp.servicio_id)" +
				       "INNER JOIN vrmpasajero p on (p.pasajero_ID=vp.pasajero_ID) " +
				       "INNER JOIN vrmtipcom tc ON (tc.tipcom_id=vp.tipcom_id) "+
			       "WHERE vp.d_fecliq=to_date('"+fecha+"','dd/mm/yyyy')  AND c.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
			       	   "AND vp.c_estreg='"+Constantes.VALUE_ACTIVO+"'" +
			       	   "AND vp.c_tiptra="+Constantes.TIPO_VENTA_NORMAL;

			    if(!(numDocPax.trim().isEmpty()))
			    	Sql+=" AND p.c_numdoc="+numDocPax;
			    if(idTipoFormaPago!=null)
			    	Sql+=" AND  tfp.tipforpag_id="+idTipoFormaPago;

			    Sql+="ORDER BY vp.d_fecliq ";

		log.info(Sql);

		List<?> result = getSession().createSQLQuery(Sql).list();
		List<Cortesia> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);

			Cortesia oCortecia = new Cortesia();
			VentaPasaje ventaPasaje= new VentaPasaje();
			TipoFormaPago tipoFormaPago= new TipoFormaPago();
			Ruta ruta= new Ruta();
			Servicio servicio= new Servicio();
			Pasajero pasajero= new Pasajero();

			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setNumeroBoleto(obj[0]!=null?obj[1].toString():"");
			ventaPasaje.setFechaPartida(obj[6]!=null?(Date)obj[6]: null);
			ventaPasaje.setHoraPartida(obj[7]!=null?obj[7].toString(): "");
			ventaPasaje.setNumeroAsiento(obj[8]!=null?((BigDecimal)obj[8]).intValue(): null);

			pasajero.setNombresApellidos(obj[2].toString());
			ruta.setOrigen(obj[3].toString());
			ruta.setDestino(obj[4].toString());
			servicio.setDenominacion(obj[5].toString());
			tipoFormaPago.setDenominacion(obj[9]!=null?obj[9].toString():null);

			ventaPasaje.setTipoFormaPago(tipoFormaPago);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setServicio(servicio);

			TipoComprobante tipoComprobante= new TipoComprobante();
			tipoComprobante.setDenominacion(obj[12].toString());
			ventaPasaje.setTipoComprobante(tipoComprobante);

			oCortecia.setId(((BigDecimal)obj[10]).longValue());
			oCortecia.setAnioCumpleanios(obj[11]!=null?((BigDecimal)obj[11]).intValue():null);
			oCortecia.setTipoFormaPago(tipoFormaPago);
			oCortecia.setPasajero(pasajero);
			oCortecia.setRuta(ruta);
			oCortecia.setVentaPasaje(ventaPasaje);

			lstResult.add(oCortecia);
		}

		return (ArrayList<Cortesia>) lstResult;
	}



}
