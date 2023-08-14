/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	:
 * Autor		: José Avalos
 * Fecha		: 08/04/2012
 */
package pe.itsb.vyrbus.service.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author José Avalos
 * @since JDK1.6
 */
public class Messages {
	private static final String BUNDLE_NAME = "pe.itsb.vyrbus.service.util.messages";
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private Messages() {
		new Locale("es", "PE");
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
