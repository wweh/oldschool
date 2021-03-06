package org.fnet.oldschool.map;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.fnet.oldschool.PublicVars;
import org.fnet.oldschool.entities.Block;

public class Generator {
	
	private static void addAll(ArrayList<String> segments, String... bundle) {
		segments.addAll(Arrays.asList(Arrays.copyOfRange(bundle, 1, bundle.length)));
	}
	
	public static ArrayList<String> generateMap() {
		ArrayList<String> segments = new ArrayList<>();
		Random random = new Random();
		
		addAll(segments, PublicVars.SEGMENTBUNDLE_START);
		int stairs = getBundlePriority(PublicVars.SEGMENTBUNDLE_UPDOWNSTAIRS);
		int normal = getBundlePriority(PublicVars.SEGMENTBUNDLE_NORMAL);
		
		for (int i = 0; i < PublicVars.MAP_LENGTH; i++) {
			
			if (randBoolFromPriority(random, stairs))
				addAll(segments, PublicVars.SEGMENTBUNDLE_UPDOWNSTAIRS);
			else if (randBoolFromPriority(random, normal))
				addAll(segments, PublicVars.SEGMENTBUNDLE_NORMAL);
			else
				segments.add("flat");
		}
		addAll(segments, PublicVars.SEGMENTBUNDLE_END);
		
		return segments;
	}
	
	public static boolean randBoolFromPriority(Random random, int priority) {
		return random.nextInt(priority + 1) > priority - 6;
	}

	private static final int HIGHEST_PRIORITY = 10;
	
	public static int getBundlePriority(String[] bundle) {
		int out = Integer.parseInt(bundle[0]);
		out = Math.max(0, out);
		out = Math.min(HIGHEST_PRIORITY, out);
		return HIGHEST_PRIORITY * 2 - out * 2;
	}
	
	public static void stringsToSegments(final ArrayList<String> segments, final ArrayList<Block> list) throws IOException {
		int x_pointer = 0;
		for (String s : segments) {
			Segment segment = new Segment(s);
			x_pointer += segment.addToList(list, x_pointer, PublicVars.DISPLAY_HEIGHT - 64) + 64;
		}
	}
	
}
