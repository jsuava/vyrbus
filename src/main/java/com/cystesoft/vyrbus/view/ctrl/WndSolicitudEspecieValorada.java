/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 24/04/2013
 */
package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;

import com.cystesoft.vyrbus.model.bean.Agencia;
import com.cystesoft.vyrbus.model.bean.TipoComprobante;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.MyTime;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.WndBase;
import com.cystesoft.vyrbus.view.ui.WndOpcionesMantenimiento;

/**
 * @author JABANTO
 *
 */
@SuppressWarnings("unused")
public class WndSolicitudEspecieValorada extends WndOpcionesMantenimiento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Datebox dbxFecha;
	private Combobox cmbAgencia;
	private Combobox cmbTipoComprobante;
	private Intbox ibxSerie;
	private Intbox ibxCantidad;
	
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		dbxFecha=(Datebox)this.getFellow("dbxFecha");
		cmbAgencia=(Combobox)this.getFellow("cmbAgencia");
		cmbTipoComprobante=(Combobox)this.getFellow("cmbTipoComprobante");
		ibxSerie=(Intbox)this.getFellow("ibxSerie");
		ibxCantidad=(Intbox)this.getFellow("ibxCantidad");
	}
	
	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		UtilData.cargarDataCombo(cmbAgencia, Agencia.class, false);
		UtilData.cargarDataCombo(cmbTipoComprobante, TipoComprobante.class, false);
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onNew()
	 */
	@Override
	public void onNew() throws Exception {
		dbxFecha.setValue(Constantes.FORMAT_DATE.parse(new MyTime().dateServer()));
		Util.seleccionarValorItemCombo(Agencia.class, cmbAgencia, getAgencia().getId());
		cmbTipoComprobante.setSelectedIndex(0);
		
		dbxFecha.setDisabled(true);
		cmbAgencia.setDisabled(true);
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSearch()
	 */
	@Override
	public void onSearch() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onRefresh(int)
	 */
	@Override
	public void onRefresh(int tab) throws Exception {
		// TODO Auto-generated method tub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onModify(int)
	 */
	@Override
	public void onModify(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onCancel(int)
	 */
	@Override
	public void onCancel(int action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onSave(int)
	 */
	@Override
	public void onSave(int action) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onDelete(int)
	 */
	@Override
	public void onDelete(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onPrint(int)
	 */
	@Override
	public void onPrint(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onExport(int)
	 */
	@Override
	public void onExport(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onHelp()
	 */
	@Override
	public void onHelp() throws Exception {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.IOpcionesMantenimiento#onChangeTab(int)
	 */
	@Override
	public void onChangeTab(int tab) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	/* (non-Javadoc)
	 * @see org.zkoss.zul.Window#onClose()
	 */
	@Override
	public void onClose() {
		closeTabWindow();
	}
	

}
