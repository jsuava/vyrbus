/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 27/08/2014
 * Hora			: 12:04:10
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import com.cystesoft.vyrbus.model.bean.HRE;
import com.cystesoft.vyrbus.model.bean.Itinerario;
import com.cystesoft.vyrbus.model.bean.NumeroHojaRutaID;
import com.cystesoft.vyrbus.model.bean.Personal;
import com.cystesoft.vyrbus.model.bean.ProgramacionServicio;
import com.cystesoft.vyrbus.model.bean.Ruta;
import com.cystesoft.vyrbus.model.bean.Servicio;
import com.cystesoft.vyrbus.model.dao.HREDAO;
import com.cystesoft.vyrbus.service.util.Constantes;

/**
 * @author JABANTO
 *
 */
public class HREDAOImpl extends GenericDAOImpl implements HREDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.HREDAO#guardar(com.tepsa.sisvyr.model.bean.HRE)
	 */
	@Override
	public void guardar(HRE hre) throws Exception {
		// TODO Auto-generated method stub
		super.save(hre);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.HREDAO#actualizar(com.tepsa.sisvyr.model.bean.HRE)
	 */
	@Override
	public void actualizar(HRE hre) throws Exception {
		// TODO Auto-generated method stub
		super.update(hre);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.HREDAO#buscarPorId(java.lang.String)
	 */
	@Override
	public HRE buscarPorId(String nroHojaRuta) throws Exception {
		// TODO Auto-generated method stub
		String hql="FROM HRE WHERE numeroHojaRutaID.idNumeroHojaRuta='"+nroHojaRuta+"' ";
		
		HRE hre=(HRE) getSession().createQuery(hql).uniqueResult();
		return hre;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.HREDAO#buscarHREEmitida(java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<HRE> buscarHREEmitida(String fechaInicial, String fechaFinal,Integer idOrigen, Integer idDestino) throws Exception {
		String sql="SELECT hre.d_fecsal AS fSalida "+
					      ",hre.c_horsal AS hSalida "+
					      ",hre.c_nrobus AS bus "+
					      ",hre.d_fecestlle AS fELlegada "+
					      ",hre.c_horestlle AS hELlegada "+
					      ",hre.c_numhojrut AS nroHojaRuta "+
					      ",hre.d_fecllerea AS fRLlegada "+
					      ",hre.c_horllerea AS hRLlegada "+
					      ",r.c_origen AS origen "+
					      ",r.c_destino AS destino "+
					      ",s.c_Denominacion as Servicio "+
					      ",t.c_apepat AS t_ApePaterno "+
					      ",t.c_apemat AS t_ApeMaterno "+
					      ",t.c_nombre AS t_ApeNombre "+
					      ",p.c_apepat AS p_ApePaterno "+
					      ",p.c_apemat AS p_ApeMaterno "+
					      ",p.c_nombre AS p_ApeNombre "+
					      ",cp.c_apepat AS cp_ApePaterno "+
					      ",cp.c_apemat AS cp_ApeMaterno "+
					      ",cp.c_nombre AS cp_ApeNombre "+
					      ",cpx.c_apepat AS cpx_ApePaterno "+
					      ",cpx.c_apemat AS cpx_ApeMaterno "+
					      ",cpx.c_nombre AS cpx_ApeNombre "+
					"FROM VRTHOJRUTELE hre "+
					    "INNER JOIN VRMRUTA r ON (r.ruta_id=hre.ruta_id) "+ 
					    "INNER JOIN VRMBUS b ON (b.c_codigo=hre.c_nrobus) "+
//					    "INNER JOIN VRTITINERARIO i ON (i.d_fecpar=hre.d_fecsal AND i.c_horpar=hre.c_horsal AND i.bus_id=b.bus_id AND i.n_esanulado=0 AND i.c_estreg='A') "+          
						"INNER JOIN VRTITINERARIO i ON (i.itinerario_id=hre.itinerario_id) "+
					    "INNER JOIN VRMSERVICIO s ON (s.servicio_id=i.servicio_id) "+
					    "INNER JOIN VRTPROSER ps ON (ps.itinerario_id=i.itinerario_id) "+
					    "INNER JOIN(SELECT  dhre.c_numhojrut, dhre.personal_id, p.c_apepat, p.c_apemat, p.c_nombre "+
					           "FROM VRTDETFLOHRE dhre  "+
					                "INNER JOIN VRMPERSONAL p ON (p.personal_id=dhre.personal_id) "+
					           "WHERE dhre.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					           "GROUP BY dhre.c_numhojrut, dhre.personal_id, p.c_apepat, p.c_apemat, p.c_nombre "+
					           ") t ON (t.personal_id=ps.personal_idterramoza AND t.c_numhojrut=hre.c_numhojrut) "+
					    "INNER JOIN(SELECT  dhre.c_numhojrut, dhre.personal_id, p.c_apepat, p.c_apemat, p.c_nombre "+
					               "FROM VRTDETFLOHRE dhre  "+
					                    "INNER JOIN VRMPERSONAL p ON (p.personal_id=dhre.personal_id) "+
					               "WHERE dhre.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					               "GROUP BY dhre.c_numhojrut, dhre.personal_id, p.c_apepat, p.c_apemat, p.c_nombre "+
					               ") p ON (p.personal_id=ps.personal_idpiloto AND p.c_numhojrut=hre.c_numhojrut) "+
					    "INNER JOIN(SELECT  dhre.c_numhojrut, dhre.personal_id, p.c_apepat, p.c_apemat, p.c_nombre "+
					               "FROM VRTDETFLOHRE dhre  "+
					                    "INNER JOIN VRMPERSONAL p ON (p.personal_id=dhre.personal_id) "+
					               "WHERE dhre.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					               "GROUP BY dhre.c_numhojrut, dhre.personal_id, p.c_apepat, p.c_apemat, p.c_nombre "+
					               ") cp ON (cp.personal_id=ps.personal_idcopiloto AND cp.c_numhojrut=hre.c_numhojrut) "+
					    "LEFT JOIN(SELECT  dhre.c_numhojrut, dhre.personal_id, p.c_apepat, p.c_apemat, p.c_nombre "+
					               "FROM VRTDETFLOHRE dhre  "+
					                    "INNER JOIN VRMPERSONAL p ON (p.personal_id=dhre.personal_id) "+
					               "WHERE dhre.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					               "GROUP BY dhre.c_numhojrut, dhre.personal_id, p.c_apepat, p.c_apemat, p.c_nombre "+
					               ") cpx ON (cpx.personal_id=ps.personal_idcopilotoaux AND cpx.c_numhojrut=hre.c_numhojrut) "+
					"WHERE hre.d_Fecsal BETWEEN to_date('"+fechaInicial+"','dd/MM/yyyy') AND to_date('"+fechaFinal+"','dd,MM/yyyy') "+
						"AND r.localidad_idorigen=NVL("+idOrigen+",r.localidad_idorigen) "+
						"AND r.localidad_iddestino=NVL("+idDestino+",r.localidad_iddestino) "+
					    "AND hre.c_estreg='"+Constantes.VALUE_ACTIVO+"' "+
					    "AND ps.c_estreg='A' "+
					"ORDER BY concat(hre.d_Fecsal,hre.c_horsal) ";
		log.info(sql);
		List<?>result=getSession().createSQLQuery(sql).list();
		List<HRE>lstHRE=new ArrayList<HRE>();
		for(int x=0;x<result.size();x++){
			Object[] obj=(Object[]) result.get(x);
			
			HRE hre=new HRE();
			hre.setFechaSalida((Date)obj[0]);
			hre.setHoraSalida(obj[1].toString());
			hre.setNumeroBus(obj[2].toString());
			hre.setFechaEstimacionLlegada((Date)obj[3]);
			hre.setHoraEstimacionLlegada(obj[4].toString());
			hre.setNumeroHojaRutaID(new NumeroHojaRutaID(obj[5].toString()));
			hre.setFechaLlegadaReal(obj[6]!=null?(Date)obj[6]:null);
			hre.setHoraLlegadaReal(obj[7]!=null?obj[7].toString():null);
			
			Ruta ruta=new Ruta();
			ruta.setOrigen(obj[8].toString());
			ruta.setDestino(obj[9].toString());
			
			Servicio servicio=new Servicio();
			servicio.setDenominacion(obj[10].toString());
			
			Personal tripulante=new Personal();
			tripulante.setApellidoPaterno(obj[11]!=null?obj[11].toString():null);
			tripulante.setApellidoMaterno(obj[12]!=null?obj[12].toString():null);
			tripulante.setNombre(obj[13]!=null?obj[13].toString():null);
			
			Personal piloto=new Personal();
			piloto.setApellidoPaterno(obj[14]!=null?obj[14].toString():null);
			piloto.setApellidoMaterno(obj[15]!=null?obj[15].toString():null);
			piloto.setNombre(obj[16]!=null?obj[16].toString():null);
			
			Personal copiloto=new Personal();
			copiloto.setApellidoPaterno(obj[17]!=null?obj[17].toString():null);
			copiloto.setApellidoMaterno(obj[18]!=null?obj[18].toString():null);
			copiloto.setNombre(obj[19]!=null?obj[19].toString():null);
			
			Personal copilotoAuxiliar=null;
			if(obj[20]!=null){
				copilotoAuxiliar=new Personal();
				copilotoAuxiliar.setApellidoPaterno(obj[20]!=null?obj[20].toString():null);
				copilotoAuxiliar.setApellidoMaterno(obj[21]!=null?obj[21].toString():null);
				copilotoAuxiliar.setNombre(obj[22]!=null?obj[22].toString():null);
			}
			
			
			Itinerario itinerario=new Itinerario();
			itinerario.setServicio(servicio);
			
			ProgramacionServicio programacionServicio=new ProgramacionServicio();
			programacionServicio.setTripulante(tripulante);
			programacionServicio.setPiloto(piloto);
			programacionServicio.setCopiloto(copiloto);
			programacionServicio.setCopilotoAuxiliar(copilotoAuxiliar);
			programacionServicio.setItinerario(itinerario);
			
			hre.setRuta(ruta);
			hre.setProgramacionServicio(programacionServicio);
			
			
			lstHRE.add(hre);
		}
		
		return lstHRE;
	}

	/* 
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.HREDAO#buscarHREEmitida(java.lang.Long)
	 */
	@Override
	public HRE buscarHREEmitida(Long idItinerario)throws Exception {
//		String hql="FROM HRE WHERE fechaSalida='"+fechaPartida+"' AND numeroBus='"+codigoBus+"' AND estadoRegistro='"+Constantes.VALUE_ACTIVO+"'";
		String hql="FROM HRE WHERE itinerario_id="+idItinerario+" AND estadoRegistro='"+Constantes.VALUE_ACTIVO+"'";
		HRE hre=(HRE)getSession().createQuery(hql).uniqueResult();
		
		return hre;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.HREDAO#buscarByItinerario(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HRE> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrden) throws Exception {
		// TODO Auto-generated method stub
		return (List<HRE>) super.findByX(HRE.class, criteriosBusqueda, criteriosOrden);
	}
	
}
