package fi.aalto.cs.drumbeat.rest.api;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.ServletContext;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.github.jsonldjava.jena.JenaJSONLD;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import fi.aalto.cs.drumbeat.rest.accessory.HTMLPrettyPrinting;
import fi.aalto.cs.drumbeat.rest.managers.AppManager;
import fi.aalto.cs.drumbeat.rest.managers.DataSetManager;

/*
 The MIT License (MIT)

 Copyright (c) 2015 Jyrki Oraskari

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.
 */

@Path("/datasets")
public class DataSetResource {

	private static  DataSetManager datasetManager;

	@Context
	private ServletContext servletContext;

	@Path("/alive")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String isAlive() {
		return "{\"status\":\"LIVE\"}";
	}
	
	
	@Path("/{collectionname}/{datasourcename}")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String listHTML(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename) {
		Model m = ModelFactory.createDefaultModel();
		if(!getManager(servletContext).listAll(m,collectionname,datasourcename))
			   return "<HTML><BODY>Status:\"No datasources\"</BODY></HTML>";
			
		return HTMLPrettyPrinting.prettyPrinting(m);	
	}

	@Path("/{collectionname}/{datasourcename}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String listJSON_LD(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename) {
		Model m = ModelFactory.createDefaultModel();
		
		try {
			if(!getManager(servletContext).listAll(m,collectionname,datasourcename))
			   return "{\"Status\":\"No datasources\"}";
			
			JenaJSONLD.init();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			m.write(os, "JSON-LD");
			try {
				return new String(os.toByteArray(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
			}
		} catch (Exception e) {
			return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
		}
		
	}
	

	@Path("/{collectionname}/{datasourcename}")
	@GET
	@Produces("text/turtle")
	public String listTurtle(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename) {
		Model m = ModelFactory.createDefaultModel();
		
		try {
			if(!getManager(servletContext).listAll(m,collectionname,datasourcename))
			   return "{\"Status\":\"No datasources\"}";
			
			JenaJSONLD.init();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			m.write(os, "TURTLE");
			try {
				return new String(os.toByteArray(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
			}
		} catch (Exception e) {
			return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
		}
		
	}
	
	@Path("/{collectionname}/{datasourcename}")
	@GET
	@Produces("application/rdf+xml")
	public String listRDF(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename) {
		Model m = ModelFactory.createDefaultModel();
		
		try {
			if(!getManager(servletContext).listAll(m,collectionname,datasourcename))
			   return "{\"Status\":\"No datasources\"}";
			
			JenaJSONLD.init();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			m.write(os, "RDF/XML");
			try {
				return new String(os.toByteArray(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
			}
		} catch (Exception e) {
			return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
		}
		
	}
	
	
	@Path("/{collectionname}/{datasourcename}/{datasetname}")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String getHTML(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename,@PathParam("datasetname") String datasetname) {
		Model m = ModelFactory.createDefaultModel();
		if(!getManager(servletContext).get(m,collectionname, datasourcename, datasetname))
			   return "<HTML><BODY>Status:\"The ID does not exists\"</BODY></HTML>";
		return HTMLPrettyPrinting.prettyPrinting(m);	
	}

	@Path("/{collectionname}/{datasourcename}/{datasetname}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getJSON(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename,@PathParam("datasetname") String datasetname) {
		Model m = ModelFactory.createDefaultModel();
		if(!getManager(servletContext).get(m,collectionname, datasourcename, datasetname))
			   return "{\"Status\":\"The ID does not exists\"}";

		JenaJSONLD.init();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		m.write(os, "JSON-LD");
		try {
			return new String(os.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
		}
	}

	@Path("/{collectionname}/{datasourcename}/{datasetname}")
	@GET
	@Produces("text/turtle")
	public String getTURTLE(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename,@PathParam("datasetname") String datasetname) {
		Model m = ModelFactory.createDefaultModel();
		if(!getManager(servletContext).get(m,collectionname, datasourcename, datasetname))
			   return "{\"Status\":\"The ID does not exists\"}";

		JenaJSONLD.init();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		m.write(os, "TURTLE");
		try {
			return new String(os.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
		}
	}
	
	@Path("/{collectionname}/{datasourcename}/{datasetname}")
	@GET
	@Produces("application/rdf+xml")
	public String getRDF(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename,@PathParam("datasetname") String datasetname) {
		Model m = ModelFactory.createDefaultModel();
		if(!getManager(servletContext).get(m,collectionname, datasourcename, datasetname))
			   return "{\"Status\":\"The ID does not exists\"}";

		JenaJSONLD.init();
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		m.write(os, "RDF/XML");
		try {
			return new String(os.toByteArray(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "{\"Status\":\"ERROR:" + e.getMessage() + "\"}";
		}
	}
	
	
	@Path("/{collectionname}/{datasourcename}/{datasetname}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String createJSON(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename,@PathParam("datasetname") String datasetname) {
		try {
			getManager(servletContext).create(collectionname, datasourcename,datasetname);
		} catch (RuntimeException r) {
			r.printStackTrace();
			return "{\"Status\":\"ERROR:" + r.getMessage() + " collectionname:" +  collectionname+ " datasourcename:" + datasourcename+ " datasetname:" + datasetname + "\"}";
		}
		return "{\"Status\":\"Done\"}";
	}

	@Path("/{collectionname}/{datasourcename}/{datasetname}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteJSON(@PathParam("collectionname") String collectionname,@PathParam("datasourcename") String datasourcename,@PathParam("datasetname") String datasetname) {
		try {
			getManager(servletContext).delete(collectionname, datasourcename,datasetname);
		} catch (RuntimeException r) {
			r.printStackTrace();
			return "{\"Status\":\"ERROR:" + r.getMessage() + " collectionname:" +  collectionname+ " datasourcename:" + datasourcename+ " datasetname:" + datasetname + "\"}";
		}
		return "{\"Status\":\"Done\"}";
	}
	

	private static DataSetManager getManager(ServletContext servletContext) {
		if (datasetManager == null) {
			try {
				Model model = AppManager.getJenaProvider(servletContext).openDefaultModel();
				datasetManager = new DataSetManager(model);
			} catch (Exception e) {
				throw new RuntimeException("Could not get Jena model: " + e.getMessage(), e);
			}
		}
		return datasetManager;
	}

}