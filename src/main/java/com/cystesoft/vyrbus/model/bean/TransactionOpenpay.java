/**
 * Proyecto		: SISVYR
 * Sistema		: Sistema de Ventas y Reservas
 * Descripción	: 
 * Autor		: José Abanto
 * Fecha		: 9 ene. 2024
 * Hora			: 00:30:48
 */
package com.cystesoft.vyrbus.model.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Jose
 *
 */
public class TransactionOpenpay extends GenericBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
    private String codeTransaction;
    private String authorization;
    private String operation_type;
    private String transaction_type;
    private String status;
    private String description;
    private String error_message;
    private String error_code;
    private String order_id;
    private String card_type;
    private String card_brand;
    private String card_number;
    private String card_holder_name;
    private String card_expiration_year;
    private String card_expiration_month;
    private String card_bank_name;
    private String card_bank_code;
    private Double amount;
    private String currency;
    private String customer_name;
    private String customer_last_name;
    private String customer_email;
    private String customer_phone_number;
    private String payment_method_type;
    private String payment_method_reference;
    private String payment_method_barcode_url;
    private Date creation_date;
    private String method;
    private String contacto;
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the codeTransaction
	 */
	public String getCodeTransaction() {
		return codeTransaction;
	}
	/**
	 * @param codeTransaction the codeTransaction to set
	 */
	public void setCodeTransaction(String codeTransaction) {
		this.codeTransaction = codeTransaction;
	}
	/**
	 * @return the authorization
	 */
	public String getAuthorization() {
		return authorization;
	}
	/**
	 * @param authorization the authorization to set
	 */
	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}
	/**
	 * @return the operation_type
	 */
	public String getOperation_type() {
		return operation_type;
	}
	/**
	 * @param operation_type the operation_type to set
	 */
	public void setOperation_type(String operation_type) {
		this.operation_type = operation_type;
	}
	/**
	 * @return the transaction_type
	 */
	public String getTransaction_type() {
		return transaction_type;
	}
	/**
	 * @param transaction_type the transaction_type to set
	 */
	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the error_message
	 */
	public String getError_message() {
		return error_message;
	}
	/**
	 * @param error_message the error_message to set
	 */
	public void setError_message(String error_message) {
		this.error_message = error_message;
	}
	/**
	 * @return the error_code
	 */
	public String getError_code() {
		return error_code;
	}
	/**
	 * @param error_code the error_code to set
	 */
	public void setError_code(String error_code) {
		this.error_code = error_code;
	}
	/**
	 * @return the order_id
	 */
	public String getOrder_id() {
		return order_id;
	}
	/**
	 * @param order_id the order_id to set
	 */
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	/**
	 * @return the card_type
	 */
	public String getCard_type() {
		return card_type;
	}
	/**
	 * @param card_type the card_type to set
	 */
	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}
	/**
	 * @return the card_brand
	 */
	public String getCard_brand() {
		return card_brand;
	}
	/**
	 * @param card_brand the card_brand to set
	 */
	public void setCard_brand(String card_brand) {
		this.card_brand = card_brand;
	}
	/**
	 * @return the card_number
	 */
	public String getCard_number() {
		return card_number;
	}
	/**
	 * @param card_number the card_number to set
	 */
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}
	/**
	 * @return the card_holder_name
	 */
	public String getCard_holder_name() {
		return card_holder_name;
	}
	/**
	 * @param card_holder_name the card_holder_name to set
	 */
	public void setCard_holder_name(String card_holder_name) {
		this.card_holder_name = card_holder_name;
	}
	/**
	 * @return the card_expiration_year
	 */
	public String getCard_expiration_year() {
		return card_expiration_year;
	}
	/**
	 * @param card_expiration_year the card_expiration_year to set
	 */
	public void setCard_expiration_year(String card_expiration_year) {
		this.card_expiration_year = card_expiration_year;
	}
	/**
	 * @return the card_expiration_month
	 */
	public String getCard_expiration_month() {
		return card_expiration_month;
	}
	/**
	 * @param card_expiration_month the card_expiration_month to set
	 */
	public void setCard_expiration_month(String card_expiration_month) {
		this.card_expiration_month = card_expiration_month;
	}
	/**
	 * @return the card_bank_name
	 */
	public String getCard_bank_name() {
		return card_bank_name;
	}
	/**
	 * @param card_bank_name the card_bank_name to set
	 */
	public void setCard_bank_name(String card_bank_name) {
		this.card_bank_name = card_bank_name;
	}
	/**
	 * @return the card_bank_code
	 */
	public String getCard_bank_code() {
		return card_bank_code;
	}
	/**
	 * @param card_bank_code the card_bank_code to set
	 */
	public void setCard_bank_code(String card_bank_code) {
		this.card_bank_code = card_bank_code;
	}
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the customer_name
	 */
	public String getCustomer_name() {
		return customer_name;
	}
	/**
	 * @param customer_name the customer_name to set
	 */
	public void setCustomer_name(String customer_name) {
		this.customer_name = customer_name;
	}
	/**
	 * @return the customer_last_name
	 */
	public String getCustomer_last_name() {
		return customer_last_name;
	}
	/**
	 * @param customer_last_name the customer_last_name to set
	 */
	public void setCustomer_last_name(String customer_last_name) {
		this.customer_last_name = customer_last_name;
	}
	/**
	 * @return the customer_email
	 */
	public String getCustomer_email() {
		return customer_email;
	}
	/**
	 * @param customer_email the customer_email to set
	 */
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	/**
	 * @return the customer_phone_number
	 */
	public String getCustomer_phone_number() {
		return customer_phone_number;
	}
	/**
	 * @param customer_phone_number the customer_phone_number to set
	 */
	public void setCustomer_phone_number(String customer_phone_number) {
		this.customer_phone_number = customer_phone_number;
	}
	/**
	 * @return the payment_method_type
	 */
	public String getPayment_method_type() {
		return payment_method_type;
	}
	/**
	 * @param payment_method_type the payment_method_type to set
	 */
	public void setPayment_method_type(String payment_method_type) {
		this.payment_method_type = payment_method_type;
	}
	/**
	 * @return the payment_method_reference
	 */
	public String getPayment_method_reference() {
		return payment_method_reference;
	}
	/**
	 * @param payment_method_reference the payment_method_reference to set
	 */
	public void setPayment_method_reference(String payment_method_reference) {
		this.payment_method_reference = payment_method_reference;
	}
	/**
	 * @return the payment_method_barcode_url
	 */
	public String getPayment_method_barcode_url() {
		return payment_method_barcode_url;
	}
	/**
	 * @param payment_method_barcode_url the payment_method_barcode_url to set
	 */
	public void setPayment_method_barcode_url(String payment_method_barcode_url) {
		this.payment_method_barcode_url = payment_method_barcode_url;
	}
	/**
	 * @return the creation_date
	 */
	public Date getCreation_date() {
		return creation_date;
	}
	/**
	 * @param creation_date the creation_date to set
	 */
	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the contacto
	 */
	public String getContacto() {
		return contacto;
	}
	/**
	 * @param contacto the contacto to set
	 */
	public void setContacto(String contacto) {
		this.contacto = contacto;
	}
}
