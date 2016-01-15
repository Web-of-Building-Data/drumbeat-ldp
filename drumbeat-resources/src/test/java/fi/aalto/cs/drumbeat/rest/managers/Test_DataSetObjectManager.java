package fi.aalto.cs.drumbeat.rest.managers;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.shared.AlreadyExistsException;
import com.hp.hpl.jena.shared.NotFoundException;
import com.hp.hpl.jena.vocabulary.RDF;

import fi.aalto.cs.drumbeat.rest.DrumbeatTest;
import fi.aalto.cs.drumbeat.rest.application.TestApplication;
import fi.aalto.cs.drumbeat.rest.common.DrumbeatApplication;
import fi.aalto.cs.drumbeat.rest.common.LinkedBuildingDataOntology;
import fi.aalto.cs.drumbeat.common.DrumbeatException;
import fi.aalto.cs.drumbeat.rdf.RdfUtils;

public class Test_DataSetObjectManager extends DrumbeatTest {
	
	private static final boolean DO_TEST = true;
	
	private static DataSetObjectManager dataSetObjectManager;
	private static Model metaDataModel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		DrumbeatTest.setUpBeforeClass();
		metaDataModel = getApplication().getMetaDataModel();		
		String testDataFilePath = getApplication().getRealServerPath(TestApplication.TEST_RDF_META_DATA_FILE_PATH);
		RdfUtils.importRdfFileToJenaModel(metaDataModel, testDataFilePath);
		
		
		dataSetObjectManager = new DataSetObjectManager();
	}

	public Test_DataSetObjectManager() {
		super(DO_TEST);
	}
	
	
	/***************************************
	 * getAll()
	 * @throws DrumbeatException 
	 * @throws NotFoundException 
	 **************************************/
//	@Test
//	public void test_getAll() throws NotFoundException, DrumbeatException {
//		dataSetObjectManager.getAll("col-1", "dso-1-1", "dse-1-1-1");
//	}
}
