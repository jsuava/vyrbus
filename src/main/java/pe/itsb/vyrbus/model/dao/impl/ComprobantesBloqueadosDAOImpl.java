/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 21/02/2017
 * Hora			: 10:27:33
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.springframework.dao.DataIntegrityViolationException;

import pe.itsb.vyrbus.model.bean.ComprobantesBloqueados;
import pe.itsb.vyrbus.model.dao.ComprobantesBloqueadosDAO;
import pe.itsb.vyrbus.service.exceptions.DataIntegrityException;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose Abanto
 *
 */
public class ComprobantesBloqueadosDAOImpl extends GenericDAOImpl implements ComprobantesBloqueadosDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ComprobantesBloqueadosDAO#bloquearComprobante(com.tepsa.sisvyr.model.bean.ComprobantesBloqueados)
	 */
	@Override
	public int bloquearComprobante(ComprobantesBloqueados ComprobantesBloqueados)throws Exception {
		int result = Constantes.FAILURE;
		try{
			super.save(ComprobantesBloqueados);
			result = Constantes.CORRECT;

		}catch(DataIntegrityViolationException divex){
			throw new DataIntegrityException();
		}catch(Exception ex){
			throw new Exception(ex);
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ComprobantesBloqueadosDAO#desbloquearComprobante(com.tepsa.sisvyr.model.bean.ComprobantesBloqueados)
	 */
	@Override
	public int desbloquearComprobante(ComprobantesBloqueados comprobantesBloqueados) throws Exception {
		String hql = "DELETE FROM ComprobantesBloqueados tmp WHERE tmp.ventaPasaje.id = :venpasID "
				   + "AND usuario.id= :usuarioID AND usuarioHardware.id= :usuhardID ";

		log.info(hql);
		int result = getSession().createQuery(hql)
				.setLong("venpasID", comprobantesBloqueados.getVentaPasaje().getId())
				.setLong("usuarioID", comprobantesBloqueados.getUsuario().getId())
				.setLong("usuhardID", comprobantesBloqueados.getUsuarioHardware().getId())
				.executeUpdate();
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ComprobantesBloqueadosDAO#desbloquearComprobanteByUsuarioHardware(java.lang.Integer)
	 */
	@Override
	public int desbloquearComprobanteByUsuarioHardware(Integer idUsuarioHardware)throws Exception {
		String hql = "DELETE FROM ComprobantesBloqueados tmp WHERE tmp.usuarioHardware.id = :usuHardID ";

		log.info(hql);
		int result = getSession().createQuery(hql)
				.setInteger("usuHardID", idUsuarioHardware).executeUpdate();
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ComprobantesBloqueadosDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<ComprobantesBloqueados> buscarPorEstadoRegistro(String estado) {
		return null;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ComprobantesBloqueadosDAO#desbloquearComprobante(java.lang.Long)
	 */
	@Override
	public int desbloquearComprobante(Long idVentaPasaje) throws Exception {
		String hql = "DELETE FROM ComprobantesBloqueados tmp WHERE tmp.ventaPasaje.id = :venpasID";

		log.info(hql);
		int result = getSession().createQuery(hql)
				.setLong("venpasID", idVentaPasaje)
				.executeUpdate();
		return result;
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ComprobantesBloqueadosDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ComprobantesBloqueados> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) throws Exception {
		return (ArrayList<ComprobantesBloqueados>) super.findByX(ComprobantesBloqueados.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.ComprobantesBloqueadosDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public ComprobantesBloqueados buscarPorId(Long ventaPasajeId)throws Exception {
		// TODO Auto-generated method stub
		String hql="FROM ComprobantesBloqueados tmp WHERE tmp.ventaPasaje.id="+ventaPasajeId;
		log.info(hql);
		ComprobantesBloqueados comprobantesBloqueados=(ComprobantesBloqueados) getSession().createQuery(hql).uniqueResult();

		return comprobantesBloqueados;
	}

}
