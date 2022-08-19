/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: jM
 * Fecha		: 20/04/2012
 */
package com.cystesoft.vyrbus.view.ui;

/**
 * Interface para los eventos de mantenimiento,
 * @author jM
 * @since JDK1.6
 * @see WndOpcionesMantenimiento;
 */
public interface IOpcionesMantenimiento {
	/**
	 * Constante para la Acción Nuevo
	 * @see WndOpcionesMantenimiento#onCancel(int)
	 * @see WndOpcionesMantenimiento#onSave(int)
	 */
	public final int ACTION_NEW = 0;
	/**
	 * Constante para la Acción Modificar
	 * @see WndOpcionesMantenimiento#onCancel(int)
	 * @see WndOpcionesMantenimiento#onSave(int)
	 */
	public final int ACTION_MODIFY = 1;
	/**
	 * Constante para el tab lista (listado de registros) cuando se encuentre seleccionado.
	 * @see WndOpcionesMantenimiento#onChangeTab(int)
	 */
	public final int TAB_LIST = 0;
	/**
	 * Constante para el tab mantenimiento (mantenimiento de registros) cuando se encuentre seleccionado.
	 * @see WndOpcionesMantenimiento#onChangeTab(int)
	 */
	public final int TAB_MAINTENANCE = 1;

	/**
	 * evento asignado al botón Nuevo
	 * @return void
	 */
	public void onNew() throws Exception;
	/**
	 * evento asignado al botón Buscar
	 * @return void
	 */
	public void onSearch() throws Exception;
	/**
	 * evento asignado al botón Refrescar
	 * @param tab parametro con el tab activado
	 * @return void
	 * @see IOpcionesMantenimiento#TAB_LIST
	 * @see IOpcionesMantenimiento#TAB_MAINTENANCE
	 */
	public void onRefresh(int tab) throws Exception;
	/**
	 * evento asignado al botón Modificar
	 * @param tab parametro con el tab activado
	 * @return void
	 * @see IOpcionesMantenimiento#TAB_LIST
	 * @see IOpcionesMantenimiento#TAB_MAINTENANCE
	 */
	public void onModify(int tab) throws Exception;
	/**
	 * evento asignado al botón Cancelar
	 * @param action
	 * @return void
	 * @see IOpcionesMantenimiento#ACTION_NEW
	 * @see IOpcionesMantenimiento#ACTION_MODIFY
	 */
	public void onCancel(int action) throws Exception;
	/**
	 * evento asignado al botón Guardar
	 * @param action
	 * @return void
	 * @throws Exception
	 * @see IOpcionesMantenimiento#ACTION_NEW
	 * @see IOpcionesMantenimiento#ACTION_MODIFY
	 */
	public void onSave(int action) throws Exception;
	/**
	 * evento asignado al botón Eliminar
	 * @param tab parametro con el tab activado
	 * @return void
	 * @see IOpcionesMantenimiento#TAB_LIST
	 * @see IOpcionesMantenimiento#TAB_MAINTENANCE
	 */
	public void onDelete(int tab) throws Exception;
	/**
	 * evento asignado al botón Imprimir
	 * @param tab parametro con el tab activado
	 * @return void
	 * @see IOpcionesMantenimiento#TAB_LIST
	 * @see IOpcionesMantenimiento#TAB_MAINTENANCE
	 */
	public void onPrint(int tab) throws Exception;
	/**
	 * evento asignado al botón Exportar
	 * @param tab parametro con el tab activado
	 * @return void
	 * @see IOpcionesMantenimiento#TAB_LIST
	 * @see IOpcionesMantenimiento#TAB_MAINTENANCE
	 */
	public void onExport(int tab) throws Exception;
	/**
	 * evento asignado al botón Ayuda
	 * @return void
	 */
	public void onHelp() throws Exception;
	/**
	 * evento asignado al botón Cerrar
	 * @return void
	 */
	public void onClose() throws Exception;
	/**
	 * evento asignado al cambiar de Tabs (Tab Lista ó Tab Mantenimiento)
	 * @param tab
	 * @return void
	 * @see IOpcionesMantenimiento#TAB_LIST
	 * @see IOpcionesMantenimiento#TAB_MAINTENANCE
	 */
	public void onChangeTab(int tab) throws Exception;
}