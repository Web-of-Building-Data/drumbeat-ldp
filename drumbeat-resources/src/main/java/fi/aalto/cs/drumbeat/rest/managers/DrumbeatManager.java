package fi.aalto.cs.drumbeat.rest.managers;

import java.util.List;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;

import fi.aalto.cs.drumbeat.rest.common.DrumbeatApplication;
import fi.aalto.cs.drumbeat.rest.common.LinkedBuildingDataOntology;
import fi.aalto.cs.drumbeat.common.DrumbeatException;
import fi.aalto.cs.drumbeat.rdf.jena.provider.JenaProvider;

public abstract class DrumbeatManager {
	
	private final Model metaDataModel;
	private final JenaProvider jenaProvider;
	
	public DrumbeatManager() throws DrumbeatException {
		this(DrumbeatApplication.getInstance().getMetaDataModel(), DrumbeatApplication.getInstance().getJenaProvider());		
	}
	
	public DrumbeatManager(Model metaDataModel, JenaProvider jenaProvider) {
		this.metaDataModel = metaDataModel;
		this.jenaProvider = jenaProvider;
	}
	
	public Model getMetaDataModel() {
		return metaDataModel;
	}
	
	protected JenaProvider getJenaProvider() {
		return jenaProvider;
	}
	
	protected QueryExecution createQueryExecution(Query query, Model model) {
		return getJenaProvider().createQueryExecution(query, model);
	}
	
	public Resource getCollectionResource(String collectionId) {
		return metaDataModel.createResource(
				LinkedBuildingDataOntology.formatCollectionResourceUri(
						collectionId));
	}
	
	public Resource getDataSourceResource(String collectionId, String dataSourceId) {
		return metaDataModel.createResource(
				LinkedBuildingDataOntology.formatDataSourceResourceUri(
						collectionId,
						dataSourceId));
	}

	public Resource getDataSetResource(String collectionId, String dataSourceId, String dataSetId) {
		return metaDataModel.createResource(
				LinkedBuildingDataOntology.formatDataSetResourceUri(
						collectionId,
						dataSourceId,
						dataSetId));
	}
	
	/**
	 * @deprecated Instead of SELECT-queries use CONSTRUCT-queries to get direct Model 
	 * 
	 * Converts a {@link ResultSet} with 3 columns to a {@link Model}
	 * @param resultSet
	 * @return
	 * 
	 */
	@Deprecated
	public static Model convertResultSetToModel(ResultSet resultSet) {
		List<String> varNames = resultSet.getResultVars();
		if (varNames.size() != 3) {
			throw new IllegalArgumentException(
					String.format("Numer of columns: expected <3>, actual <%d>", varNames.size()));
		}
		
		Model resultModel = ModelFactory.createDefaultModel();
		
		while (resultSet.hasNext()) {
			QuerySolution row = resultSet.next();
			
			Resource subject = row.getResource(varNames.get(0));
			if (subject == null) {
				throw new NullPointerException("Expected non-null resource for being statement subject");
			}
			
			Property predicate = row.getResource(varNames.get(1)).as(Property.class);
			if (predicate == null) {
				throw new NullPointerException("Expected non-null resource for being statement predicate");
			}

			RDFNode object = row.get(varNames.get(2));
			if (object == null) {
				throw new NullPointerException("Expected non-null rdfNode for being statement object");
			}

			resultModel.add(subject, predicate, object);
		}
		
		return resultModel;
	}
	
}
