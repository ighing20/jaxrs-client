package com.ighing.jaxrsclient;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ighing.jaxrsclient.model.ClienteModel;

public class ApiClient {

	private static final String URI = "http://localhost:9090/jaxrs/api";

	private final Client client;

	public ApiClient() {
		client = ClientBuilder.newClient();
	}

	public Response addCliente(ClienteModel cliente) {
		WebTarget webTarget = client.target(URI).path("clientes");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		return invocationBuilder.post(Entity.entity(cliente, MediaType.APPLICATION_JSON));
	}

	public ClienteModel getCliente(int ClienteId) {
		WebTarget webTarget = client.target(URI).path("clientes/" + String.valueOf(ClienteId));
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		return invocationBuilder.get(ClienteModel.class);
	}

	public List<ClienteModel> getAllClientes() {
		WebTarget webTarget = client.target(URI).path("clientes");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		return invocationBuilder.get(new GenericType<List<ClienteModel>>() {
		});
	}

	public Response updateCliente(ClienteModel cliente) {
		WebTarget webTarget = client.target(URI).path("clientes");
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		return invocationBuilder.put(Entity.entity(cliente, MediaType.APPLICATION_JSON));
	}
	
	public ClienteModel removeCliente(int ClienteId) {
		WebTarget webTarget = client.target(URI).path("clientes/" + String.valueOf(ClienteId));
		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		return invocationBuilder.delete(ClienteModel.class);
	}

	public static void main(String[] args) throws JsonProcessingException {

		ApiClient apiClient = new ApiClient();

		ClienteModel clienteModel = new ClienteModel("Juan Pedro", "Main street 1000", "555-555-5555");
		System.out.println("--- addCliente ---");
		System.out.println(apiClient.addCliente(clienteModel).getStatus());
;
		System.out.println("--- getCliente ---");
		System.out.println(apiClient.getCliente(1));

		System.out.println("--- updateCliente ---");
		clienteModel.setId(1);
		clienteModel.setNombre("me cambie el nombre.");
		System.out.println(apiClient.updateCliente(clienteModel).getStatus());

		System.out.println("--- getAllClientes ---");
		List<ClienteModel> clientes = apiClient.getAllClientes();
		clientes.forEach(System.out::println);
		
		System.out.println("--- removeCliente ---");
		System.out.println(apiClient.removeCliente(1));
		

	}

}
