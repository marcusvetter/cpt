package org.crossplatform.backend.service;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.crossplatform.backend.model.criterion.Criterion;
import org.crossplatform.backend.model.technology.Technology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileSystemConnector {

	// Hide public constructor (static utility class)
	private FileSystemConnector() {

	}

	private static final Logger LOG = LoggerFactory
			.getLogger(FileSystemConnector.class);

	public static final String TECHNOLOGIES_DEFAULT_LOCATION = "/technologies";
	public static final String CRITERIA_DEFAULT_LOCATION = "/criteria";

	/**
	 * If you call this method all available technology json files will be
	 * parsed and {@link Technology} java objects will be created. The method
	 * returns all those created {@link Technology} objects in a list.
	 * 
	 * @return List of created {@link Technology} objects
	 */
	public static List<Technology> loadTechnologies(
			String technologyResourcePath) {
		List<Technology> technologies = new ArrayList<>();

		loadAndMapFiles(technologyResourcePath, Technology.class).stream()
				.forEach(object -> technologies.add((Technology) object));

		return technologies;
	}

	/**
	 * If you call this method all available criterion json files will be parsed
	 * and {@link Criterion} java objects will be created. The method returns
	 * all those created {@link Criterion} objects in a list.
	 * 
	 * @return List of created {@link Criterion} objects
	 */
	public static List<Criterion> loadCriteria(String criteriaResourcePath) {
		List<Criterion> criteria = new ArrayList<>();

		loadAndMapFiles(criteriaResourcePath, Criterion.class).stream()
				.forEach(object -> criteria.add((Criterion) object));

		return criteria;
	}

	/**
	 * INTERNAL: Load all files from the given directory. It will parse all json
	 * files and map them to the given Class.
	 * 
	 * @param resourceDirectoryName
	 *            The directory to serach for the json files
	 * @param modelClass
	 *            The model class the files are going to be mapped
	 * @return list of created objects. You can simply cast the all objects in
	 *         this list to the given model class.
	 * @throws IOException
	 * @throws URISyntaxException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	private static List<Object> loadAndMapFiles(String resourceDirectoryName,
			Class<?> modelClass) {
		List<Object> objectList = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		getFiles(resourceDirectoryName).stream().forEach(file -> {
			try {
				objectList.add(mapper.readValue(file, modelClass));
			} catch (IOException e) {
				LOG.error(e.getMessage());
			}
		});
		return objectList;
	}

	/**
	 * INTERNAL: Here we load the files from the file system (given resource
	 * directory)
	 * 
	 * @param resourceDirectoryName
	 *            the resource directory
	 * @return list of files
	 * @throws URISyntaxException
	 */
	private static List<File> getFiles(String resourceDirectoryName) {
		List<File> fileList = new ArrayList<>();

		URI resourceDirURI;
		try {
			resourceDirURI = FileSystemConnector.class.getResource(
					resourceDirectoryName).toURI();
		} catch (URISyntaxException uriSyntaxException) {
			LOG.error(uriSyntaxException.getMessage());
			return fileList;
		}

		File resourceDirectory = new File(resourceDirURI);

		Arrays.asList(resourceDirectory.list())
				.stream()
				.forEach(
						fileName -> {
							try {
								File file = new File(FileSystemConnector.class
										.getResource(
												resourceDirectoryName + "/"
														+ fileName).toURI());
								if (file.isFile()) {
									fileList.add(file);
								}
							} catch (URISyntaxException e) {
								LOG.error(e.getMessage());
							}
						});

		return fileList;
	}
}
