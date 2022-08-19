package com.cystesoft.vyrbus.view.ctrl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.cystesoft.vyrbus.model.bean.OcupacionAsientosDesbloqueados;
import com.cystesoft.vyrbus.model.bean.OcupacionAsientosDesbloqueadosID;
import com.cystesoft.vyrbus.model.bean.Parametros;
import com.cystesoft.vyrbus.model.bean.TmpOcupacionAsientos;
import com.cystesoft.vyrbus.service.locator.ServiceLocator;
import com.cystesoft.vyrbus.service.util.Constantes;
import com.cystesoft.vyrbus.service.util.Messages;
import com.cystesoft.vyrbus.service.util.Util;
import com.cystesoft.vyrbus.service.util.UtilData;
import com.cystesoft.vyrbus.view.ui.DlgMessage;
import com.cystesoft.vyrbus.view.ui.WndBase;

/**
 *
 * @author Jos� Abanto
 *
 */
public class WndDesbloqueoAsientos extends WndBase implements Serializable{

	private static final long serialVersionUID = 1L;
	private Listbox lbxAsientosBloqueados;

	private Parametros parametros=null;

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#initComponents()
	 */
	@Override
	public void initComponents() {
		lbxAsientosBloqueados=(Listbox)getFellow("lbxAsientosBloqueados");
	}

	/*
	 * (non-Javadoc)
	 * @see com.tepsa.sisvyr.view.ui.WndBase#onCreate()
	 */
	@Override
	public void onCreate() throws Exception {
		try {
			//Carga parametros
			parametros=((Parametros)Executions.getCurrent().getSession().getAttribute(Constantes.ATRIBUTO_PARAMETROS));
			//Carga asientos bloqueados
			listarRegistros();


			if(parametros==null){//Si no encuentra parametros
				DlgMessage.information(Messages.getString("Generales.information.errorCargarParametros"));
				closeTabWindow();
			}
		} catch (Exception e) {
			e.printStackTrace();
			DlgMessage.error(e.getMessage());
		}

	}

	/**
	 * Lista informacion de los asientos bloqueados.
	 */
	public void listarRegistros()throws Exception {
		List<TmpOcupacionAsientos> lstRegistros = new ArrayList<>();
		lstRegistros=ServiceLocator.getTmpOcupacionAsientosManager().buscarPorEstadoRegistro(Constantes.VALUE_ACTIVO);
		Util.limpiarListbox(lbxAsientosBloqueados);

		if(lstRegistros.size()>0){
			Listitem item = null;
			Listcell cell = null;
			Integer x =-1;

			for(TmpOcupacionAsientos tmpOcupacionAsientos : lstRegistros){
				Date fechaHoraExpiBloq=tmpOcupacionAsientos.getFechaExpiraBloqueo();
				String sFechaHoraExpiBloq="Expira Bloqueo : "+Constantes.FORMAT_LONG.format(fechaHoraExpiBloq)+"";
				item = new Listitem();
				x++;

//				cell = new Listcell((tmpOcupacionAsientos.getItinerario().getBus()!=null? tmpOcupacionAsientos.getItinerario().getBus().getCodigo(): ""));
//				cell.setStyle("font-size: 11px !important");
//				item.appendChild(cell);
//				cell = new Listcell((tmpOcupacionAsientos.getItinerario().getId().toString()));
//				cell.setStyle("font-size: 11px !important");
//				item.appendChild(cell);
				cell = new Listcell((tmpOcupacionAsientos.getRuta().toString()));
				item.appendChild(cell);
				cell = new Listcell((Constantes.FORMAT_DATE.format(tmpOcupacionAsientos.getItinerario().getFechaPartida())));
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell);
				cell = new Listcell((tmpOcupacionAsientos.getItinerario().getHoraPartida()));
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell);
				cell = new Listcell((tmpOcupacionAsientos.getNumeroAsiento().toString()));
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell);
				cell = new Listcell((Constantes.FORMAT_LONG.format( tmpOcupacionAsientos.getFechaInsercion())) );
				cell.setStyle("font-size: 11px !important");
				item.appendChild(cell);
				cell = new Listcell((tmpOcupacionAsientos.getUsuarioHardware().getAgencia().getDenominacion()));
				item.appendChild(cell);
				cell = new Listcell((tmpOcupacionAsientos.getUsuario().getApellidoPaterno()+" "+tmpOcupacionAsientos.getUsuario().getApellidoMaterno()+", "+ tmpOcupacionAsientos.getUsuario().getNombre()));
				item.appendChild(cell);

				Toolbarbutton tooDesb=new Toolbarbutton();
				tooDesb.setId(String.valueOf(x));
				tooDesb.setDisabled(true);

				tooDesb.setDisabled(false);
				tooDesb.setImage("resources/mp_unlocked.png");
				tooDesb.setTooltiptext("Desbloquear Asiento.\n "+sFechaHoraExpiBloq+" ");

				/*Valida acceso a desbloar*/
				if(accesoEliminar() ){
					//Valida si existen agencias configuradas con un tiempo de desbloqueo personalizado.
					if(parametros.getTiempoExpiraBloqueoPersonalizado()!=null){
						//Recupera el Identificador de la Agencia y su tiempo conmfigurado.
						String[] TimeExpiBloqPers=parametros.getTiempoExpiraBloqueoPersonalizado().split(";");
						for (String timeExpiBloqPer : TimeExpiBloqPers) {
							String[] agenciaTime=timeExpiBloqPer.split(",");// Identificador de la agencia
							if(agenciaTime.length==2){
								int idAgencia=Integer.parseInt(agenciaTime[0]);
								if(tmpOcupacionAsientos.getUsuarioHardware().getAgencia().getId().intValue()==idAgencia){//Valida Agencia.
									Date fechaHoraActual=new Date();
									if(fechaHoraExpiBloq.getTime()>fechaHoraActual.getTime()){
										tooDesb.setDisabled(true);
										tooDesb.setImage("resources/mp_locked.png");
										tooDesb.setTooltiptext("No se puede desbloquear el Asiento.\n "+sFechaHoraExpiBloq+" ");
									}
									break;
								}
							}
						}
					}
				}else{
					tooDesb.setDisabled(true);
					tooDesb.setImage("resources/mp_locked.png");
					tooDesb.setTooltiptext("No cuenta con los permisos necesarios para desbloquear el Asiento.");
				}

				tooDesb.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						desbloqueaAsiento(e.getTarget().getId());
					}
				});

				cell = new Listcell();
				cell.appendChild(tooDesb);
				item.appendChild(cell);


				item.setValue(tmpOcupacionAsientos);
				lbxAsientosBloqueados.appendChild(item);
			}
		}
	}

	/**
	 * desbloquea de asiento
	 * @throws Exception
	 */
	public void desbloqueaAsiento(String id) throws Exception{
		try{
			lbxAsientosBloqueados.setSelectedIndex(Integer.valueOf(id));
			if(lbxAsientosBloqueados.getSelectedIndex() >= 0 ){
				Messagebox.show(Messages.getString("wndTmpOcupabilidad.information.confirmaDesbloqueo"), DlgMessage.NOMBREAPLICACION, DlgMessage.BTN_YESNO, Messagebox.QUESTION, new EventListener<Event>() {
					@Override
					public void onEvent(Event e) throws Exception {
						if(e.getName().equals("onYes")){
							/*obtiene el objeto TmpOcupacionAsientos*/
							Listitem listitem=lbxAsientosBloqueados.getItemAtIndex(lbxAsientosBloqueados.getSelectedIndex());
							TmpOcupacionAsientos tmpOcupacionAsientos=listitem.getValue();

							/*Registra el asiento desdloqueado*/
							OcupacionAsientosDesbloqueadosID asientosDesbloqueadosID= new OcupacionAsientosDesbloqueadosID();
							asientosDesbloqueadosID.setIdItinerario(tmpOcupacionAsientos.getItinerario().getId());
							asientosDesbloqueadosID.setIdRuta(tmpOcupacionAsientos.getRuta().getId());
							asientosDesbloqueadosID.setIdUsuario(tmpOcupacionAsientos.getUsuario().getId());
							asientosDesbloqueadosID.setIdUsuarioHardware(tmpOcupacionAsientos.getUsuarioHardware().getId());

							OcupacionAsientosDesbloqueados asientosDesbloqueados= new OcupacionAsientosDesbloqueados();
							asientosDesbloqueados.setOcupacionAsientosDesbloqueadosID(asientosDesbloqueadosID);
							asientosDesbloqueados.setUsuarioHardware(tmpOcupacionAsientos.getUsuarioHardware());
							asientosDesbloqueados.setUsuario(tmpOcupacionAsientos.getUsuario());
							asientosDesbloqueados.setRuta(tmpOcupacionAsientos.getRuta());
							asientosDesbloqueados.setItinerario(tmpOcupacionAsientos.getItinerario());
							asientosDesbloqueados.setNumeroAsiento(tmpOcupacionAsientos.getNumeroAsiento());
							asientosDesbloqueados.setFechaPartida(Constantes.FORMAT_DATE.format(tmpOcupacionAsientos.getItinerario().getFechaPartida()));
							asientosDesbloqueados.setHoraPartida(tmpOcupacionAsientos.getItinerario().getHoraPartida());
							asientosDesbloqueados.setNumeroPiso(tmpOcupacionAsientos.getNumeroPiso());
							asientosDesbloqueados.setFechaBloqueo(tmpOcupacionAsientos.getFechaInsercion()!=null?tmpOcupacionAsientos.getFechaInsercion():null);
							asientosDesbloqueados.setFechaExpiracionBloqueo(tmpOcupacionAsientos.getFechaExpiraBloqueo());
							asientosDesbloqueados.setEstadoRegistro(Constantes.VALUE_ACTIVO);

							UtilData.auditarRegistro(asientosDesbloqueados, getUsuario(), Executions.getCurrent());
							ServiceLocator.getOcupacionAsientosDesbloqueadosManager().guardar(asientosDesbloqueados);

							/*realiza el desbloqueo del asiento*/
							ServiceLocator.getTmpOcupacionAsientosManager().desbloquearAsiento(tmpOcupacionAsientos);

							/*refresca la lista de asientos desbloqueados*/
							listarRegistros();
						}
					}
				});
			}
		}catch (Exception ex) {
			DlgMessage.error(this.getClass().getName()+" "+ex.getMessage());
			ex.printStackTrace();
		}
	}


}
