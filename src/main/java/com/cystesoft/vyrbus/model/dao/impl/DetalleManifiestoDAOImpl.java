/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Sullo Avalos
 * Fecha		: 09/11/2013
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.zkoss.zk.ui.Executions;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.DetalleManifiesto;
import com.cystesoft.vyrbus.model.bean.Manifiesto;
import com.cystesoft.vyrbus.model.bean.Pasajero;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.TipoDocumento;
import com.cystesoft.vyrbus.model.bean.Usuario;
import com.cystesoft.vyrbus.model.bean.VentaPasaje;
import com.cystesoft.vyrbus.model.dao.DetalleManifiestoDAO;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.UtilData;

/**
 * @author JABANTO
 *
 */
public class DetalleManifiestoDAOImpl extends GenericDAOImpl implements DetalleManifiestoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleManifiestoDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleManifiesto> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (List<DetalleManifiesto>)super.findByX(DetalleManifiesto.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleManifiestoDAO#guradar(com.tepsa.sisvyr.model.bean.DetalleManifiesto)
	 */
	@Override
	public void guradar(DetalleManifiesto detalleManifiesto) throws Exception {
		// TODO Auto-generated method stub
		super.save(detalleManifiesto);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleManifiestoDAO#actualizar(com.tepsa.sisvyr.model.bean.DetalleManifiesto)
	 */
	@Override
	public void actualizar(DetalleManifiesto detalleManifiesto)throws Exception {
		// TODO Auto-generated method stub
		super.update(detalleManifiesto);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleManifiestoDAO#buscarPasajeros(java.lang.Long, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<DetalleManifiesto> buscarPasajeros(Long idManifiesto,Integer idLocalidadOrigen, Integer idAgenciaPuntoEmbarque)throws Exception {
		String sql="SELECT vp.venpas_id "+ // 0
					      ",vp.n_numasiento "+ // 1
					      ",vp.n_numpiso "+  // 2
					      ",vp.c_numboleto "+  // 3
					      ",vp.n_imppag "+  // 4
					      ",p.pasajero_id "+  // 5
					      ",p.c_apepat "+  // 6
					      ",p.c_apemat "+  // 7
					      ",p.c_nombre "+  // 8
					      ",p.c_fecnac "+  // 9
					      ",p.c_numdoc "+  // 10
					      ",td.c_denominacion "+  // 11
					      ",r.c_origen "+  // 12
					      ",r.c_destino "+  // 13
					      ",a.agencia_id AS agencia_idAEmbarcar " + //14 - Representa el idAgencia donde se debe embarcar el pasajero.
					      ",a.c_nomcor "+  // 15
					      ",dm.manifiesto_id "+ //16
					      ",ae.agencia_id AS agencia_idEmbarcado "+ //17 - Representa el idAgencia donde realizó el embarque
					      ",dm.audfecmod as FechaDesp "+ //18 - Representa la fecha y hora en que se realizó el despacho
					      ",dm.audusumod as usuarioDespacho "+ //19  - Representa el usuario que realizo el despacho.
					      ",ae.c_nomcor AS agencia_NombreEmbarcado "+ //20 - Representa el nombre corto de la agencia donde realizó el embarque
					"FROM VRTDETMAN dm "+
					   "INNER JOIN VRTVENPAS vp ON (vp.venpas_id=dm.venpas_id) "+
					   "INNER JOIN VRTMANIFIESTO m ON (m.manifiesto_id=dm.manifiesto_id) "+
					   "INNER JOIN VRMPASAJERO p ON (p.pasajero_id=vp.pasajero_id) "+
					   "INNER JOIN VRMTIPDOC td ON (td.tipdoc_id=p.tipdoc_id) "+
					   "INNER JOIN VRMRUTA r ON (r.ruta_id=vp.ruta_id) "+
					   "INNER JOIN VRMAGENCIA a ON (a.agencia_id=vp.agencia_idpartida) "+
					   "LEFT JOIN VRMAGENCIA ae ON (ae.agencia_id=dm.agencia_id) "+
					"WHERE dm.manifiesto_id="+idManifiesto+"  "+
					   "AND r.localidad_idorigen="+idLocalidadOrigen+" " +
					   "AND vp.agencia_idpartida=NVL("+idAgenciaPuntoEmbarque+",vp.agencia_idpartida) "+
					   "AND dm.c_estreg='"+Constantes.VALUE_ACTIVO+"' " +
//					   "AND dm.agencia_id IS NULL " +
					"ORDER BY vp.n_numasiento ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<DetalleManifiesto> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[]) result.get(i);
			VentaPasaje  ventaPasaje=new VentaPasaje();

			ventaPasaje.setId(((BigDecimal)obj[0]).longValue());
			ventaPasaje.setNumeroAsiento(((BigDecimal)obj[1]).intValue());
			ventaPasaje.setNumeroPiso(((BigDecimal)obj[2]).intValue());
			ventaPasaje.setNumeroBoleto(obj[3].toString());
			ventaPasaje.setImportePagado(((BigDecimal)obj[4]).doubleValue());

			Pasajero pasajero=new Pasajero();
			pasajero.setId(((BigDecimal)obj[5]).longValue());
			pasajero.setApellidoPaterno(obj[6].toString());
			pasajero.setApellidoMaterno(obj[7]!=null?obj[7].toString():"");
			pasajero.setNombre(obj[8].toString());
			pasajero.setFechaNacimiento(obj[9]!=null?obj[9].toString():null);
			pasajero.setNumeroDocumento(obj[10]!=null?obj[10].toString():null);

			TipoDocumento tipoDocumento=new TipoDocumento();
			tipoDocumento.setDenominacion(obj[11].toString());

			Ruta ruta=new Ruta();
			ruta.setOrigen(obj[12].toString());
			ruta.setDestino(obj[13].toString());

			Agencia agencia=new Agencia();
			agencia.setId(((BigDecimal)obj[14]).intValue());
			agencia.setNombreCorto(obj[15].toString());

			pasajero.setTipoDocumento(tipoDocumento);
			ventaPasaje.setPasajero(pasajero);
			ventaPasaje.setRuta(ruta);
			ventaPasaje.setAgenciaPartida(agencia);

			Manifiesto manifiesto=new Manifiesto();
			manifiesto.setId(((BigDecimal)obj[16]).longValue());

			Agencia agenciaEmbarcoPax= null;
			if(obj[17]!=null){
				agenciaEmbarcoPax=new Agencia();
				agenciaEmbarcoPax.setId(((BigDecimal)obj[17]).intValue());
				agenciaEmbarcoPax.setNombreCorto(obj[20].toString());
			}

			DetalleManifiesto detalleManifiesto=new DetalleManifiesto();
			detalleManifiesto.setFechaModificacion((Date)obj[18]);
			detalleManifiesto.setUsuarioModificacion(obj[19]!=null?obj[19].toString():"No identity");
			detalleManifiesto.setManifiesto(manifiesto);
			detalleManifiesto.setVentaPasaje(ventaPasaje);
			detalleManifiesto.setAgencia(agenciaEmbarcoPax);

			lstResult.add(detalleManifiesto);
		}
		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleManifiestoDAO#cerrarDespachoPasajeros(java.lang.Integer, java.lang.Long, java.lang.String)
	 */
	@Override
	public void cerrarDespachoPasajeros(Integer idAgencia, Long idManifiesto,String idsVentas) throws Exception {
		// TODO Auto-generated method stub
		DetalleManifiesto detalleManifiesto=new DetalleManifiesto();
		Usuario usuario=(Usuario) Executions.getCurrent().getDesktop().getSession().getAttribute(Constantes.ATRIBUTO_USUARIO);
		UtilData.auditarRegistro(detalleManifiesto, true, usuario, Executions.getCurrent());

		String sql="UPDATE VRTDETMAN SET agencia_id="+idAgencia+" "+
					                     ",Audusumod='"+detalleManifiesto.getUsuarioModificacion()+"' "+
					                     ",Audipmodi='"+detalleManifiesto.getIpModificacion()+"' "+
					"WHERE manifiesto_id="+idManifiesto+" "+
					      "AND venpas_id IN ("+idsVentas+") " +
					      "AND c_estreg='"+Constantes.VALUE_ACTIVO+"' ";

		log.info(sql);
		getSession().createSQLQuery(sql).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleManifiestoDAO#validarVentaManifiesto(java.lang.Long)
	 */
	@Override
	public Boolean validarVentaManifiesto(Long idVenta) throws Exception {
		// TODO Auto-generated method stub
		String sql="SELECT dm.venpas_id "+
					"FROM VRTDETMAN dm "+
					"INNER JOIN VRTMANIFIESTO m ON (m.manifiesto_id=dm.manifiesto_id) "+
					"WHERE dm.venpas_id="+idVenta+" "+
					      "AND m.c_estreg='A' "+
					      "AND dm.c_estreg='A' ";

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		if(result.size()==0)
			return false;
		else return true;
	}


}
