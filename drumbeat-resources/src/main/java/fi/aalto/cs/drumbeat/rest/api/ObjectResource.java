package fi.aalto.cs.drumbeat.rest.api;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;

import fi.aalto.cs.drumbeat.rest.accessory.PrettyPrinting;
import fi.aalto.cs.drumbeat.rest.application.DrumbeatApplication;
import fi.aalto.cs.drumbeat.rest.managers.ObjectManager;

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

@Path("/object")
public class ObjectResource  extends AbstractResource{
	private static ObjectManager manager;

	@Context
	private ServletContext servletContext;


	@Path("/{collectionname}/{datasourcename}/{guid}")
	@GET
	public String get(@Context HttpServletRequest httpRequest,@PathParam("collectionname") String collectionname, @PathParam("datasourcename") String datasourcename, @PathParam("guid") String guid) {
		DrumbeatApplication.getInstance().setBaseUrl(httpRequest);
		Model m = ModelFactory.createDefaultModel();
		try {
			if (!getManager().get2Model(m, collectionname, datasourcename, guid))
				return PrettyPrinting.formatError(httpRequest, "The ID does not exists");
		} catch (Exception e) {
			return PrettyPrinting.formatError(httpRequest, "Check that the RDF store is started: " + e.getMessage());
		}

		return model2AcceptedFormat(httpRequest, m);
	}
	
	@Path("/{collectionname}/{datasourcename}/{guid}")
	@PUT
	public String getPUT(@Context HttpServletRequest httpRequest,@PathParam("collectionname") String collectionname, @PathParam("datasourcename") String datasourcename, @PathParam("guid") String guid) {
		return PrettyPrinting.formatError(httpRequest, "Use import to add data.");
	}
	
	@Path("/{collectionname}/{datasourcename}/{guid}")
	@POST
	public String getPOST(@Context HttpServletRequest httpRequest,@PathParam("collectionname") String collectionname, @PathParam("datasourcename") String datasourcename, @PathParam("guid") String guid) {
		return PrettyPrinting.formatError(httpRequest, "Use import to add data.");
	}
	
	@Path("/{collectionname}/{datasourcename}/{guid}/type")
	@GET
	public String getType(@Context HttpServletRequest httpRequest,@PathParam("collectionname") String collectionname, @PathParam("datasourcename") String datasourcename, @PathParam("guid") String guid) {
		DrumbeatApplication.getInstance().setBaseUrl(httpRequest);
		Model m = ModelFactory.createDefaultModel();
		try {
			if (!getManager().getType2Model(m, collectionname, datasourcename, guid))
				return PrettyPrinting.formatError(httpRequest, "The ID does not exist");
		} catch (Exception e) {
			return PrettyPrinting.formatError(httpRequest, "Check that the RDF store is started: " + e.getMessage());
		}

		return model2AcceptedFormat(httpRequest, m);
	}

	@Path("/{collectionname}/{datasourcename}/{guid}/type")
	@PUT
	public String getTypePUT(@Context HttpServletRequest httpRequest,@PathParam("collectionname") String collectionname, @PathParam("datasourcename") String datasourcename, @PathParam("guid") String guid) {
		return PrettyPrinting.formatError(httpRequest, "Use import to add data.");
	}
	
	@Path("/{collectionname}/{datasourcename}/{guid}/type")
	@POST
	public String getTypePOST(@Context HttpServletRequest httpRequest,@PathParam("collectionname") String collectionname, @PathParam("datasourcename") String datasourcename, @PathParam("guid") String guid) {
		return PrettyPrinting.formatError(httpRequest, "Use import to add data.");
	}
	
	@Path("/{collectionname}/{datasourcename}/{guid}/{extras:.+}")
	@GET
	public String getExtrasGET(@Context HttpServletRequest httpRequest,@PathParam("collectionid") String collectionid,@PathParam("extras") String extras) {
		return PrettyPrinting.formatError(httpRequest, "Usage: /object/{collectionname}/{datasourcename}/{guid}  or /object/{collectionname}/{datasourcename}/{guid}/type  Extra characters were:/"+extras);
	}
	
	@Path("/{collectionname}/{datasourcename}/{guid}/{extras:.+}")
	@PUT
	public String getExtrasPUT(@Context HttpServletRequest httpRequest,@PathParam("collectionid") String collectionid,@PathParam("extras") String extras) {
		return PrettyPrinting.formatError(httpRequest, "Usage: /object/{collectionname}/{datasourcename}/{guid}  or /object/{collectionname}/{datasourcename}/{guid}/type  Extra characters were:/"+extras);
	}
	
	@Path("/{collectionname}/{datasourcename}/{guid}/{extras:.+}")
	@POST
	public String getExtrasPOST(@Context HttpServletRequest httpRequest,@PathParam("collectionid") String collectionid,@PathParam("extras") String extras) {
		return PrettyPrinting.formatError(httpRequest, "Usage: /object/{collectionname}/{datasourcename}/{guid}  or /object/{collectionname}/{datasourcename}/{guid}/type  Extra characters were:/"+extras);
	}
	@Override
	public ObjectManager getManager() {
		if (manager == null) {
			try {
				Model model = DrumbeatApplication.getInstance().getJenaProvider().openDefaultModel();
				manager = new ObjectManager(model);
			} catch (Exception e) {
				throw new RuntimeException("Could not get Jena model: " + e.getMessage(), e);
			}
		}
		return manager;
	}

}