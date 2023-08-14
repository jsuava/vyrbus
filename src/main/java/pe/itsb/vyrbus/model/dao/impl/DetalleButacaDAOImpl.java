/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Abanto
 * Fecha		: 05/01/2017
 * Hora			: 15:50:53
 */
package pe.itsb.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pe.itsb.vyrbus.model.bean.DetalleButaca;
import pe.itsb.vyrbus.model.dao.DetalleButacaDAO;
import pe.itsb.vyrbus.service.util.Constantes;

/**
 * @author Jose Abanto
 *
 */
@SuppressWarnings("unchecked")
public class DetalleButacaDAOImpl extends GenericDAOImpl implements DetalleButacaDAO{

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleButacaDAO#buscarPorEstadoRegistro(java.lang.String, java.lang.String)
	 */
	@Override
	public ArrayList<DetalleButaca> buscarPorEstadoRegistro(String estado,String criterioOrden) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleButaca>) super.findByEstadoRegistro(DetalleButaca.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleButacaDAO#buscarPorX(java.util.TreeMap, java.util.List)
	 */
	@Override
	public ArrayList<DetalleButaca> buscarPorX(TreeMap<String, Object> criteriosBusqueda,List<String> criteriosOrdenar) {
		// TODO Auto-generated method stub
		return (ArrayList<DetalleButaca>) super.findByX(DetalleButaca.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleButacaDAO#buscarPorId(java.lang.Long)
	 */
	@Override
	public DetalleButaca buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return (DetalleButaca) super.findById(DetalleButaca.class, id);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleButacaDAO#guardar(com.tepsa.sisvyr.model.bean.DetalleButaca)
	 */
	@Override
	public void guardar(DetalleButaca detalleButaca) {
		// TODO Auto-generated method stub
		super.save(detalleButaca);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleButacaDAO#actualizar(com.tepsa.sisvyr.model.bean.DetalleButaca)
	 */
	@Override
	public void actualizar(DetalleButaca detalleButaca) {
		// TODO Auto-generated method stub
		super.update(detalleButaca);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleButacaDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.model.dao.DetalleButacaDAO#validarExisteTarifa(com.tepsa.sisvyr.model.bean.DetalleButaca)
	 */
	@Override
	public List<DetalleButaca> validarExisteTarifa(DetalleButaca detalleButaca)throws Exception {
		String hql="From DetalleButaca db Where db.fecha='"+Constantes.FORMAT_DATE.format(detalleButaca.getFecha())+"' "
				 + "And db.ruta.id="+detalleButaca.getRuta().getId()+" "
				 + "And db.mapaBus.id="+detalleButaca.getMapaBus().getId()+" "
		 		 + "And "+(detalleButaca.getCanalVenta()==null?"db.canalVenta.id is null":"db.canalVenta.id="+detalleButaca.getCanalVenta().getId())+" "
		 		 + "And "+(detalleButaca.getItinerario()==null?"db.itinerario.id is null":"db.itinerario.id="+detalleButaca.getItinerario().getId())+" "
		 		 + "And "+(detalleButaca.getAgencia()==null?"db.agencia.id is null":"db.agencia.id="+detalleButaca.getAgencia().getId())+" "
		 		 + "And "+(detalleButaca.getUsuario()==null?"db.usuario.id is null":"db.usuario.id="+detalleButaca.getUsuario().getId())+" "
				 + "And db.estadoRegistro='A'";
		List<DetalleButaca> result=getSession().createQuery(hql).list();

		return result;
	}

}
