/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripci�n	:
 * Autor		: Jos� Abanto
 * Fecha		: 29 set. 2022
 * Hora			: 18:00:37
 */
package pe.itsb.vyrbus.service.business.impl;

import org.springframework.transaction.annotation.Transactional;

import pe.itsb.vyrbus.model.bean.HistoricoResumenVentas;
import pe.itsb.vyrbus.model.dao.HistoricoResumenVentasDAO;
import pe.itsb.vyrbus.service.business.HistoricoResumenVentasManager;

/**
 * @author Marco
 *
 */
public class HistoricoResumenVentasManagerImpl implements HistoricoResumenVentasManager{

	private HistoricoResumenVentasDAO historicoResumenVentasDAO;


	/* (non-Javadoc)
	 * @see pe.itsb.vyrbus.service.business.HistoricoResumenVentasManager#guardar(pe.itsb.vyrbus.model.bean.HistoricoResumenVentas)
	 */
	public HistoricoResumenVentasDAO getHistoricoResumenVentasDAO() {
		return historicoResumenVentasDAO;
	}


	public void setHistoricoResumenVentasDAO(HistoricoResumenVentasDAO historicoResumenVentasDAO) {
		this.historicoResumenVentasDAO = historicoResumenVentasDAO;
	}


	@Override
	@Transactional
	public void guardar(HistoricoResumenVentas historicoResumenVentas) {
		// TODO Auto-generated method stub
		getHistoricoResumenVentasDAO().guardar(historicoResumenVentas);
	}

}
