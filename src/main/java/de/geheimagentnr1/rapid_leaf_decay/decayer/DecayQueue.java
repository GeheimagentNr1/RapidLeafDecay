package de.geheimagentnr1.rapid_leaf_decay.decayer;

import java.util.Comparator;
import java.util.TreeSet;


public class DecayQueue {
	
	
	private static TreeSet<DecayTask> decayTasks;
	
	public static void init() {
		
		decayTasks = new TreeSet<>( Comparator.comparingInt( ( DecayTask o ) ->
			o.getWorld().getDimension().getType().getId() ).thenComparing( DecayTask::getPos ) );
	}
	
	public static void add( DecayTask decayTask ) {
		
		decayTasks.add( decayTask );
	}
	
	public static TreeSet<DecayTask> getElementsAndReset() {
		
		TreeSet<DecayTask> resultDecayTasks = decayTasks;
		init();
		return resultDecayTasks;
	}
	
	public static boolean notEmpty() {
		
		return !decayTasks.isEmpty();
	}
}
