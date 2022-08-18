/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 04/05/2012
 */
package com.cystesoft.vyrbus.model.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.cystesoft.vyrbus.model.bean.Ubigeo;
import com.cystesoft.vyrbus.model.dao.UbigeoDAO;

/**
 *
 * @author jM
 * @since JDK1.6
 */
@SuppressWarnings("unchecked")
public class UbigeoDAOImpl extends GenericDAOImpl implements UbigeoDAO {

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UbigeoDAO#buscarPorEstadoRegistro(java.lang.String)
	 */
	@Override
	public ArrayList<Ubigeo> buscarPorEstadoRegistro(String estado, String criterioOrden) {
		return (ArrayList<Ubigeo>) super.findByEstadoRegistro(Ubigeo.class, estado, criterioOrden);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UbigeoDAO#buscarPorX(java.util.TreeMap)
	 */
	@Override
	public ArrayList<Ubigeo> buscarPorX(TreeMap<String, Object> criteriosBusqueda, List<String> criteriosOrdenar) {
		return (ArrayList<Ubigeo>) super.findByX(Ubigeo.class, criteriosBusqueda, criteriosOrdenar);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UbigeoDAO#guardar(com.tepsa.sisvyr.domain.Ubigeo)
	 */
	@Override
	public void guardar(Ubigeo ubigeo) {
		super.save(ubigeo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UbigeoDAO#actualizar(com.tepsa.sisvyr.domain.Ubigeo)
	 */
	@Override
	public void actualizar(Ubigeo ubigeo) {
		super.update(ubigeo);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UbigeoDAO#ubicacionGeografica(com.tepsa.sisvyr.domain.Ubigeo)
	 */
	@Override
	public String ubicacionGeografica(Ubigeo ubigeo) {
		String idUbigeo = ubigeo.getId();
		String codigoDepartamento = ubigeo.getCodigoDepartamento() + "0000";
		String codigoProvincia = ubigeo.getCodigoDepartamento() + ubigeo.getCodigoProvincia() + "00";
		String codigoDistrito = ubigeo.getCodigoDepartamento()  + ubigeo.getCodigoProvincia() + ubigeo.getCodigoDistrito();
		StringBuilder ubicacion = new StringBuilder();


		if (codigoDepartamento.equals(idUbigeo)) {
			ubicacion.append(ubigeo.getNombreUbigeo());
		}
			else {
				Ubigeo oUbigeoDepartamento =  buscarPorId(codigoDepartamento);
				ubicacion.append(oUbigeoDepartamento.getNombreUbigeo());
		}

		ubicacion.append(" / ");

		if (!(ubigeo.getCodigoProvincia().equals("00"))) {
			if (codigoProvincia.equals(idUbigeo)) {
				ubicacion.append(ubigeo.getNombreUbigeo());
			}
				else {
					Ubigeo oUbigeoProvincia =  buscarPorId(codigoProvincia);
					ubicacion.append(oUbigeoProvincia.getNombreUbigeo());
			}
		}

		ubicacion.append(" / ");

		if (!(ubigeo.getCodigoDistrito().equals("00"))) {
			if (codigoDistrito.equals(idUbigeo)) {
				ubicacion.append(ubigeo.getNombreUbigeo());
			}
				else {
					Ubigeo oUbigeoDistrito =  buscarPorId(codigoDistrito);
					ubicacion.append(oUbigeoDistrito.getNombreUbigeo());
			}
		}

		return ubicacion.toString();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UbigeoDAO#buscarPorId(java.lang.String)
	 */
	@Override
	public Ubigeo buscarPorId(String id) {
		Criteria oCriteria = getSession().createCriteria(Ubigeo.class);

		oCriteria.add(Restrictions.eq("id", id));

		return (Ubigeo) oCriteria.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UbigeoDAO#ubicacionGeografica(java.lang.String)
	 */
	@Override
	public String ubicacionGeografica(String id) {
		return ubicacionGeografica(buscarPorId(id));
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.dao.UbigeoDAO#inactivar(java.lang.Long)
	 */
	@Override
	public void inactivar(Long id) {
		super.inactivate(Ubigeo.class, id);
	}
}