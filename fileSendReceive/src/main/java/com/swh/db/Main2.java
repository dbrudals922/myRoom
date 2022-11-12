package com.swh.db;

import org.apache.log4j.Logger;

public class Main2 {

	protected static final Logger logger = Logger.getLogger(Main.class.getName());

	public static void main(String[] args) {

		//TRACE < DEBUG < INFO < WARN < ERROR < FATAL

		logger.fatal("FATAL");
		logger.error("ERROR");
		logger.warn("WARN");
		logger.info("INFO");
		logger.debug("DEBUG");
		logger.trace("Exiting application.");
		
	}
}
