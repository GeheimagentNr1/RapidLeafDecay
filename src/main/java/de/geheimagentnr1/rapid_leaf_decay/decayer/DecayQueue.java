package de.geheimagentnr1.rapid_leaf_decay.decayer;

import java.util.Comparator;
import java.util.TreeSet;


public class DecayQueue {
	
	
	private static TreeSet<DecayTask> decayTasks;
	
	public static void init() {
		
		decayTasks = new TreeSet<>( Comparator.comparing( ( DecayTask o ) -> o.getLevel().dimension() )
			.thenComparing( DecayTask::getPos ) );
	}
	
	public static void add( DecayTask decayTask ) {
		
		if(decayTasks != null) {
			decayTasks.add( decayTask );
		}
	}
	
	//package-private
	static TreeSet<DecayTask> getElementsAndReset() {
		
		TreeSet<DecayTask> resultDecayTasks = decayTasks;
		init();
		return resultDecayTasks;
	}
	
	//package-private
	static boolean isNotEmpty() {
		
		return decayTasks != null && !decayTasks.isEmpty();
	}
}
