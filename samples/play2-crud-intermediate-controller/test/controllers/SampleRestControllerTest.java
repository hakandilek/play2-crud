package controllers;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.test.Helpers.contentAsString;
import static play.test.Helpers.fakeApplication;
import static play.test.Helpers.inMemoryDatabase;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import models.Sample;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.junit.Before;
import org.junit.Test;

import play.Logger;
import play.Logger.ALogger;
import play.api.mvc.RequestHeader;
import play.mvc.Http.Context;
import play.mvc.Http.Request;
import play.mvc.Http.RequestBody;
import play.mvc.Result;
import play.test.FakeApplication;
import play.test.Helpers;
import play.utils.crud.RestResult;
import play.utils.crud.RestResult.Status;
import play.utils.inject.InjectAdapter;

public class SampleRestControllerTest {
	
	private static ALogger log = Logger.of(SampleRestControllerTest.class);
	
	SampleRestController controller;

	@Before
	public void start() {
		FakeApplication app = fakeApplication(inMemoryDatabase());
		Helpers.start(app);
		controller = getInstance(SampleRestController.class);
	}

	@Test
	public void testGetDao() {
		assertThat(controller.getDao()).isNotNull();
	}

	@Test
	public void testGetKeyClass() {
		assertThat(controller.getKeyClass()).isEqualTo(Long.class);
	}

	@Test
	public void testGetModelClass() {
		assertThat(controller.getModelClass()).isEqualTo(Sample.class);
	}

	@Test
	public void testUpdate() throws JsonProcessingException, IOException {
		setHttpContext(parseJson("{ \"name\": \"name\", \"value\": \"test\" }"));

		Result result = controller.update(42L);
		
		assertThat(result).isNotNull();
		RestResult res = toEntity(result, RestResult.class);
		assertThat(res).isNotNull();
		assertThat(res.getStatus()).isEqualTo(Status.NOT_FOUND);
		assertThat(res.getKey()).isEqualTo(42L);
	}

	@Test
	public void testList() {
		Result result = controller.list();
		
		assertThat(result).isNotNull();
		RestResult res = toEntity(result, RestResult.class);
		assertThat(res).isNotNull();
		assertThat(res.getStatus()).isEqualTo(Status.OK);
		assertThat(res.getList()).isNotNull();
	}

	@Test
	public void testGet() {
		Result result = controller.read(42L);
		
		assertThat(result).isNotNull();
		RestResult res = toEntity(result, RestResult.class);
		assertThat(res).isNotNull();
		assertThat(res.getStatus()).isEqualTo(Status.NOT_FOUND);
		assertThat(res.getKey()).isEqualTo(42L);
	}

	@Test
	public void testDelete() {
		Result result = controller.delete(42L);
		
		assertThat(result).isNotNull();
		RestResult res = toEntity(result, RestResult.class);
		assertThat(res).isNotNull();
		assertThat(res.getStatus()).isEqualTo(Status.CANNOT_DELETE);
		assertThat(res.getKey()).isEqualTo(42L);
	}

	@Test
	public void testCreate() {
		Sample entity = new Sample();
		entity.setName("test");
		setHttpContext(toJson(entity));
		
		Result result = controller.create();
		
		assertThat(result).isNotNull();
		RestResult res = toEntity(result, RestResult.class);
		assertThat(res).isNotNull();
		assertThat(res.getStatus()).isEqualTo(Status.OK);
	}

	private <T> T toEntity(Result result, Class<T> valueType) {
		if (log.isDebugEnabled())
			log.debug("toEntity <-");
		if (log.isDebugEnabled())
			log.debug("valueType : " + valueType);
		if (log.isDebugEnabled())
			log.debug("result : " + result);

		T ent = null;
		try {
			String str = contentAsString(result);
			if (log.isDebugEnabled())
				log.debug("str : " + str);

			ObjectMapper om = new ObjectMapper();
			ent = om.readValue(str, valueType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ent;
	}

	protected <T> JsonNode toJson(T entity) {
		ObjectMapper om = new ObjectMapper();
		JsonNode req = om.valueToTree(entity);
		return req;
	}

	protected JsonNode parseJson(String json) throws JsonProcessingException, IOException {
		ObjectMapper om = new ObjectMapper();
		JsonNode req = om.readTree(json);
		return req;
	}

	protected <T> T getInstance(Class<T> type) {
		return InjectAdapter.getInstance().getInstance(type);
	}

	protected void setHttpContext(Map<String, String[]> requestBody) {
		RequestHeader reqHeader = mock(RequestHeader.class);
		Request req = mock(Request.class);
		if (requestBody != null) {
			RequestBody reqBody = mock(RequestBody.class);
			when(req.body()).thenReturn(reqBody);
			when(reqBody.asFormUrlEncoded()).thenReturn(requestBody);
		}
		Map<String, String> session = new HashMap<String, String>();
		Map<String, String> flash = new HashMap<String, String>();
		Map<String, Object> args = new HashMap<String, Object>();

		Context.current.set(new Context(1L, reqHeader, req, session, flash, args));
	}
	
	protected void setHttpContext(JsonNode requestBody) {
		RequestHeader reqHeader = mock(RequestHeader.class);
		Request req = mock(Request.class);
		if (requestBody != null) {
			RequestBody reqBody = mock(RequestBody.class);
			when(req.body()).thenReturn(reqBody);
			when(reqBody.asJson()).thenReturn(requestBody);
		}
		Map<String, String> session = new HashMap<String, String>();
		Map<String, String> flash = new HashMap<String, String>();
		Map<String, Object> args = new HashMap<String, Object>();

		Context.current.set(new Context(1L, reqHeader, req, session, flash, args));
	}

}
