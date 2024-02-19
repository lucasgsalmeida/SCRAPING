package com.AWN.view;

import java.awt.AWTException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.AWN.controller.Processamento;

public class Main {

	public static void main(String[] args) throws IOException, AWTException {
		
		/*

		 ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

	        long currentTimeMillis = System.currentTimeMillis();

	        long initialDelay = calculateInitialDelay(currentTimeMillis);

	        scheduler.scheduleAtFixedRate(() -> {
	            try {
	                new Processamento().processarEmpresa();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }, initialDelay, 24 * 60 * 60 * 1000, TimeUnit.MILLISECONDS);
	    }

	    private static long calculateInitialDelay(long currentTimeMillis) {
	        int currentHour = (int) ((currentTimeMillis / (60 * 60 * 1000)) % 24);

	        int hoursUntilNextExecution = (currentHour < 5) ? (5 - currentHour) : (24 - currentHour + 5);

	        return hoursUntilNextExecution * 60 * 60 * 1000;
	   */
		
		Processamento proc = new Processamento();
		proc.processarEmpresa();
		
	}
}


