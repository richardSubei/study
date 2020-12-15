package com.subei.net.nio.reactor;

import java.io.IOException;
import java.nio.channels.Selector;

public class NIOServerMainAndSub {

	
	private ReactorThread[] mainReactor = new ReactorThread[1];
	private ReactorThread[] subReactor = new ReactorThread[10];
	
	
	public void newGroup() {
		for (int i = 0; i < mainReactor.length; i++) {
			mainReactor[i] = new ReactorThread() {
				
				@Override
				public void Handler() {
					
				}
			};
		}
		
		for (int i = 0; i < subReactor.length; i++) {
			subReactor[i] = new ReactorThread() {
				
				@Override
				public void Handler() {
					
				}
			};
		}
		
	}
	
	public void register() {
		
		
	}
	
	
	
}

abstract class ReactorThread extends Thread {

	Selector selector;
	boolean running = false;
	
	public ReactorThread() {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract void Handler();
	
	public void register() {
		
	}
	
	public void run() {
		
	}

	public void doStart() {
		if (!running) {
			running = true;
			start();
		}
	}
	
}

