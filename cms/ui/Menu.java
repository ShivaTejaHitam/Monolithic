package com.epam.cms.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Menu {
	
	private static final Logger LOGGER = LogManager.getLogger(Menu.class);

	public void showMenu(String[] options)
	{
		LOGGER.info("Choose one of the following options:");
		
		for(int i = 0 ; i < options.length ; i++)
		{
			LOGGER.info((i+1) + ")" + options[i]);
		}
	}
}
