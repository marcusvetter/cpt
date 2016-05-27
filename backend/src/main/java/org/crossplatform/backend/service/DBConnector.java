package org.crossplatform.backend.service;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.crossplatform.backend.model.criterion.Criterion;
import org.crossplatform.backend.model.datamodel.DefaultDataModel;
import org.crossplatform.backend.model.datamodel.ProfiledDataModelFactory;
import org.crossplatform.backend.model.technology.Technology;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class DBConnector {
	
	private static final String TECHNOLOGY_COLLECTION_PREFIX = "technologies-";
	private static final String CRITERION_COLLECTION_PREFIX = "criteria-";
	public static final String DEFAULT_PROFILE_NAME = "default";
	private static final String MONGO_DB_HOST = "localhost";
	private static final String MONGO_DB_NAME = "cpt";
	private static final int MONGO_DB_PORT = 27017;
	
	public static List<Technology> getTechnologies(String profileName) {
		return DBConnector.getData(TECHNOLOGY_COLLECTION_PREFIX, profileName,
				Technology.class);
	}

	public static boolean updateTechnologies(String profileName,
			List<Technology> technologies) {
		return DBConnector.updateData(TECHNOLOGY_COLLECTION_PREFIX, profileName,
				technologies, Technology.class);
	}

	public static List<Criterion> getCriteria(String profileName) {
		return DBConnector.getData(CRITERION_COLLECTION_PREFIX, profileName, Criterion.class);
	}

	public static boolean updateCriteria(String profileName,
			List<Criterion> criteria) {
		return DBConnector.updateData(CRITERION_COLLECTION_PREFIX, profileName, criteria,
				Criterion.class);
	}

	public static boolean createNewProfile(String profileName) {

		boolean success = false;

		try {
			MongoClient mongo = new MongoClient(MONGO_DB_HOST, MONGO_DB_PORT);
			DB db = mongo.getDB(MONGO_DB_NAME);

			checkDefaultProfile(db);

			DBCollection technologies = db.getCollection(TECHNOLOGY_COLLECTION_PREFIX
					+ profileName);
			DBCollection criteria = db.getCollection(CRITERION_COLLECTION_PREFIX + profileName);

			if (technologies.count() > 0) {
				return false;
			} else {
				JacksonDBCollection<Technology, String> technologyCollection = JacksonDBCollection
						.wrap(technologies, Technology.class, String.class);

				new DefaultDataModel().getTechnologies().forEach(t -> {
					technologyCollection.insert(t);
				});
			}
			
			if (criteria.count() > 0) {
				return false;
			} else {
				JacksonDBCollection<Criterion, String> criteriaCollection = JacksonDBCollection
						.wrap(criteria, Criterion.class, String.class);

				new DefaultDataModel().getCriteria().forEach(c -> {
					criteriaCollection.insert(c);
				});
			}

			mongo.close();
			success = true;

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return success;
	}

	// INTERNAL STUFF
	private static void checkDefaultProfile(DB db) {
		// TECHNOLOGIES
		DBCollection defaultTechnologies = db
				.getCollection(TECHNOLOGY_COLLECTION_PREFIX + DEFAULT_PROFILE_NAME);
		if (defaultTechnologies.count() == 0) {

			JacksonDBCollection<Technology, String> technologyCollection = JacksonDBCollection
					.wrap(defaultTechnologies, Technology.class, String.class);

			// Load the technologies from the file system
			FileSystemConnector.loadTechnologies(
					FileSystemConnector.TECHNOLOGIES_DEFAULT_LOCATION).forEach(
					t -> {
						technologyCollection.insert(t);
					});
		}

		// CRITERIA
		DBCollection defaultCriteria = db.getCollection(CRITERION_COLLECTION_PREFIX + DEFAULT_PROFILE_NAME);
		if (defaultCriteria.count() == 0) {

			JacksonDBCollection<Criterion, String> criteriaCollection = JacksonDBCollection
					.wrap(defaultCriteria, Criterion.class, String.class);

			// Load the criteria from the file system
			FileSystemConnector.loadCriteria(
					FileSystemConnector.CRITERIA_DEFAULT_LOCATION).forEach(
					c -> {
						criteriaCollection.insert(c);
					});
		}
	}

	private static <T> List<T> getData(String collectionPrefix,
			String profileName, Class<T> V) {

		List<T> results = new ArrayList<T>();

		try {
			MongoClient mongo = new MongoClient(MONGO_DB_HOST, MONGO_DB_PORT);

			DB db = mongo.getDB(MONGO_DB_NAME);
			DBCollection dbCollection = db.getCollection(collectionPrefix
					+ profileName);

			JacksonDBCollection<T, String> collection = JacksonDBCollection
					.wrap(dbCollection, V, String.class);

			DBCursor<T> cursor = collection.find();
			try {
				while (cursor.hasNext()) {
					results.add(cursor.next());
				}
			} finally {
				cursor.close();
			}

			mongo.close();

		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		return results;

	}

	private static <T> boolean updateData(String collectionPrefix,
			String profileName, List<T> data, Class<T> V) {
		MongoClient mongo;
		try {
			mongo = new MongoClient(MONGO_DB_HOST, MONGO_DB_PORT);
			DB db = mongo.getDB(MONGO_DB_NAME);
			DBCollection dbCollection = db.getCollection(collectionPrefix
					+ profileName);

			// Remove all entries
			while (dbCollection.count() > 0) {
				dbCollection.remove(dbCollection.findOne());
			}

			JacksonDBCollection<T, String> collection = JacksonDBCollection
					.wrap(dbCollection, V, String.class);

			data.forEach(d -> {
				collection.insert(d);
			});

			mongo.close();
			
			return ProfiledDataModelFactory.updateProfiledDataModel(profileName);

		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		}
	}

}
