/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 14/11/2012
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import org.springframework.dao.DataIntegrityViolationException;

import pe.itsb.vyrbus.model.bean.Agencia;
import pe.itsb.vyrbus.model.bean.Bus;
import pe.itsb.vyrbus.model.bean.Itinerario;
import pe.itsb.vyrbus.model.bean.Localidad;
import pe.itsb.vyrbus.model.bean.Ruta;
import pe.itsb.vyrbus.model.bean.TmpOcupacionAsientos;
import pe.itsb.vyrbus.model.bean.Usuario;
import pe.itsb.vyrbus.model.bean.UsuarioHardware;
import pe.itsb.vyrbus.model.dao.TmpOcupacionAsientosDAO;
import pe.itsb.vyrbus.service.exceptions.DataIntegrityException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose
 *
*/
@SuppressWarnings("unchecked")
public class TmpOcupacionAsientosDAOImpl extends GenericDAOImpl implements TmpOcupacionAsientosDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#bloquearAsiento(com.tepsa.sisvyr.model.bean.TmpOcupacionAsientos)
	 */
	@Override
	public int bloquearAsiento(TmpOcupacionAsientos tmpOcupacion) throws Exception {
		int result = Constantes.FAILURE;
		try{
			super.save(tmpOcupacion);
			result = Constantes.CORRECT;

		}catch(DataIntegrityViolationException divex){
			throw new DataIntegrityException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#desbloquearAsiento(com.tepsa.sisvyr.model.bean.TmpOcupacionAsientos)
	 */
	@Override
	public int desbloquearAsiento(TmpOcupacionAsientos tmpOcupacion) throws Exception {
		if(tmpOcupacion.getUsuarioHardware()!=null){
			String hql = "DELETE FROM TmpOcupacionAsientos tmp WHERE tmp.ruta.id = :rutaID AND tmp.itinerario.id =:itinerarioID AND " +
					"tmp.numeroAsiento =:asiento AND tmp.numeroPiso =:piso AND tmp.usuarioHardware.id=:usuHardID ";

			log.info(hql);
			int result = getSession().createQuery(hql)
					.setLong("itinerarioID", tmpOcupacion.getItinerario().getId())
					.setInteger("rutaID", tmpOcupacion.getRuta().getId())
					.setInteger("asiento", tmpOcupacion.getNumeroAsiento())
					.setInteger("piso", tmpOcupacion.getNumeroPiso())
					.setInteger("usuHardID", tmpOcupacion.getUsuarioHardware().getId())
					.executeUpdate();
			return result;
		}else{
			String hql = "DELETE FROM TmpOcupacionAsientos tmp WHERE tmp.ruta.id = :rutaID AND tmp.itinerario.id =:itinerarioID AND " +
					"tmp.numeroAsiento =:asiento AND tmp.numeroPiso =:piso ";

			log.info(hql);
			int result = getSession().createQuery(hql)
					.setLong("itinerarioID", tmpOcupacion.getItinerario().getId())
					.setInteger("rutaID", tmpOcupacion.getRuta().getId())
					.setInteger("asiento", tmpOcupacion.getNumeroAsiento())
					.setInteger("piso", tmpOcupacion.getNumeroPiso())
					.executeUpdate();
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#desbloquearAsientoByUsuarioHardware(java.lang.Integer)
	 */
	@Override
	public int desbloquearAsientoByUsuarioHardware(Integer idUsuarioHardware) throws Exception {
		String hql = "DELETE FROM TmpOcupacionAsientos tmp WHERE tmp.usuarioHardware.id = :usuHardID ";

		log.info(hql);
		int result = getSession().createQuery(hql)
				.setInteger("usuHardID", idUsuarioHardware).executeUpdate();
		return result;
	}

//	/* (non-Javadoc)
//	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#buscarAsientosBloqueados(java.lang.String, java.lang.String, java.lang.String)
//	 */
//	@Override
//	public List<TmpOcupacionAsientos> buscarAsientosBloqueados(String campo, String patron, String estado) throws Exception {
//		String sql = "SELECT n_asiento FROM vrttmpocuasi WHERE itinerario_id=4";
//
//		log.info(sql);
//		List<?> result = getSession().createSQLQuery(sql).list();
//		List<TmpOcupacionAsientos> lstResult = new ArrayList<TmpOcupacionAsientos>();
//		for(int i=0; i<result.size(); i++){
//			TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
//			tmpOcupacionAsientos.setNumeroAsiento(((BigDecimal)result.get(i)).intValue());
//			lstResult.add(tmpOcupacionAsientos);
//		}
//		return lstResult;
//	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#buscarAsientosBloqueados(java.lang.Long)
	 */
	@Override
	public List<TmpOcupacionAsientos> buscarAsientosBloqueados(Long idItinerario) throws Exception {
		String sql = "SELECT r.ruta_id, r.localidad_idOrigen, r.localidad_idDestino, tmp.itinerario_id, tmp.n_asiento, tmp.n_numpiso " +
				"FROM vrttmpocuasi tmp " +
				"INNER JOIN vrmruta r ON r.ruta_id=tmp.ruta_id " +
				"WHERE tmp.itinerario_id="+idItinerario;

		log.info(sql);
		List<?> result = getSession().createSQLQuery(sql).list();
		List<TmpOcupacionAsientos> lstResult = new ArrayList<>();
		for(int i=0; i<result.size(); i++){
			Object[] obj = (Object[])result.get(i);
			TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
			Ruta ruta = new Ruta();
			ruta.setId(((BigDecimal)obj[0]).intValue());
			Localidad origen = new Localidad();
			origen.setId(((BigDecimal)obj[1]).intValue());
			ruta.setLocalidadOrigen(origen);
			Localidad destino = new Localidad();
			destino.setId(((BigDecimal)obj[2]).intValue());
			ruta.setLocalidadDestino(destino);
			tmpOcupacionAsientos.setRuta(ruta);
			Itinerario itinerario = new Itinerario();
			itinerario.setId(((BigDecimal)obj[3]).longValue());
			tmpOcupacionAsientos.setItinerario(itinerario);
			tmpOcupacionAsientos.setNumeroAsiento(((BigDecimal)obj[4]).intValue());
			tmpOcupacionAsientos.setNumeroPiso(((BigDecimal)obj[5]).intValue());
			tmpOcupacionAsientos.setKey();	//Para obtener la clave asiento + piso
			lstResult.add(tmpOcupacionAsientos);
		}
		return lstResult;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<TmpOcupacionAsientos> buscarPorEstadoRegistro(String estado) {
		String sql="SELECT r.ruta_id, r.c_origen, r.c_destino, b.c_codigo, i.itinerario_id, oa.n_asiento, "+
							"i.d_fecpar, i.c_horpar, oa.audfecins, oa.n_numpiso, u.c_apepat, u.c_apemat, u.c_nombre, "+
							"a.c_denominacion as Agencia, uh.usuhard_id, u.usuario_id,a.agencia_id,oa.d_fecexpblo  "+
					"FROM vrttmpocuasi oa "+
						"INNER JOIN vrmruta r ON (r.ruta_id=oa.ruta_id) "+
						"INNER JOIN vrtitinerario i ON (i.itinerario_id=oa.itinerario_id) "+
						"LEFT OUTER JOIN vrmbus b ON (b.bus_id=i.bus_id) "+
						"INNER JOIN vrmusuario u ON (u.usuario_id=oa.usuario_id) "+
						"INNER JOIN vrtusuhard uh ON (uh.usuhard_id=oa.usuhard_id) "+
						"INNER JOIN vrmagencia a ON (a.agencia_id=uh.agencia_id) "+
					"WHERE oa.c_estreg='A' " +
					"ORDER BY i.d_fecpar, i.c_horpar, oa.n_asiento ";

		log.info(sql);

		List<?> result = getSession().createSQLQuery(sql).list();
		ArrayList<TmpOcupacionAsientos> lstResult = new ArrayList<>();
		for (Object element : result) {
			Object[] obj = (Object[])element;

			TmpOcupacionAsientos tmpOcupacionAsientos = new TmpOcupacionAsientos();
			Ruta ruta=new Ruta();
			Itinerario itinerario=new Itinerario();
			Bus bus= new Bus();
			Usuario usuario=new Usuario();
			UsuarioHardware usuarioHardware=new UsuarioHardware();

			ruta.setId(((BigDecimal)obj[0]).intValue());
			ruta.setOrigen(obj[1].toString());
			ruta.setDestino(obj[2].toString());

			bus.setCodigo(obj[3]!=null?obj[3].toString():"");

			itinerario.setId(((BigDecimal)obj[4]).longValue());
			itinerario.setFechaPartida((Date)obj[6]);
			itinerario.setHoraPartida(obj[7].toString());
			itinerario.setBus(bus);

			tmpOcupacionAsientos.setNumeroAsiento(((BigDecimal)obj[5]).intValue());
			tmpOcupacionAsientos.setFechaInsercion((Date)obj[8]);
			tmpOcupacionAsientos.setNumeroPiso(((BigDecimal)obj[9]).intValue());
			tmpOcupacionAsientos.setFechaExpiraBloqueo((Date)obj[17]);

			usuario.setApellidoPaterno(obj[10].toString());
			usuario.setApellidoMaterno(obj[11]!=null?obj[11].toString():"");
			usuario.setNombre(obj[12].toString());
			usuario.setId(((BigDecimal)obj[15]).intValue());

			Agencia agencia=new Agencia();
			agencia.setId(((BigDecimal)obj[16]).intValue());
			agencia.setDenominacion(obj[13].toString());
			usuarioHardware.setAgencia(agencia);
			usuarioHardware.setId(((BigDecimal)obj[14]).intValue());

			tmpOcupacionAsientos.setRuta(ruta);
			tmpOcupacionAsientos.setItinerario(itinerario);
			tmpOcupacionAsientos.setUsuario(usuario);
			tmpOcupacionAsientos.setUsuarioHardware(usuarioHardware);

			lstResult.add(tmpOcupacionAsientos);
		}


		return lstResult;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#desbloquearAsientoByUsuarioHardwareAndItinerario(java.lang.Integer, java.lang.Long)
	 */
	@Override
	public int desbloquearAsientoByUsuarioHardwareAndItinerario(Integer idUsuarioHardware, Long idItinerario) throws Exception {
		// TODO Auto-generated method stub
		String hql = "DELETE FROM TmpOcupacionAsientos tmp WHERE tmp.itinerario.id =:itinerarioID AND " +
				"tmp.usuarioHardware.id = :usuHardID ";

		log.info(hql);
		int result = getSession().createQuery(hql)
				.setLong("itinerarioID", idItinerario)
				.setInteger("usuHardID", idUsuarioHardware)
				.executeUpdate();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#desbloquearAsiento(java.lang.Integer, java.lang.Long, java.lang.Integer)
	 */
	@Override
	public int desbloquearAsiento(Integer idUsuarioHardware, Long idItinerario, Integer asiento) throws Exception {
		// TODO Auto-generated method stub
		String hql = "DELETE FROM TmpOcupacionAsientos tmp WHERE  tmp.itinerario.id =:itinerarioID AND " +
				"tmp.numeroAsiento =:asiento AND tmp.usuarioHardware.id = :usuHardID ";

		log.info(hql);
		int result = getSession().createQuery(hql)
				.setLong("itinerarioID", idItinerario)
				.setInteger("asiento", asiento)
				.setInteger("usuHardID", idUsuarioHardware)
				.executeUpdate();
		return result;
	}
	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.TmpOcupacionAsientosDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<TmpOcupacionAsientos> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar)throws Exception{
		return (ArrayList<TmpOcupacionAsientos>)super.findByX(TmpOcupacionAsientos.class, criteriosBusqueda, criteriosOrdenar);
	}

}
