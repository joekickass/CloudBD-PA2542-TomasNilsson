package se.bth.serl.clony.chunks;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Test;

public class CloneTest {

	@Test
	public void testIsConsistent() {
		LinkedList<Chunk> chunksA = new LinkedList<>();
		chunksA.add(new Chunk("FileA.java", "aaa", 0, 1));
		chunksA.add(new Chunk("FileA.java", "bbb", 1, 2));
		chunksA.add(new Chunk("FileA.java", "ccc", 2, 3));

		LinkedList<Chunk> chunksB = new LinkedList<>();
		chunksB.add(new Chunk("FileB.java", "aaa", 8, 9));
		chunksB.add(new Chunk("FileB.java", "bbb", 9, 10));
		chunksB.add(new Chunk("FileB.java", "ccc", 10, 11));

		LinkedList<Chunk> chunksC = new LinkedList<>();
		chunksC.add(new Chunk("FileC.java", "aaa", 1221, 1222));
		chunksC.add(new Chunk("FileC.java", "bbb", 1222, 1223));
		chunksC.add(new Chunk("FileC.java", "ccc", 1223, 1224));

		List<LinkedList<Chunk>> instances = new ArrayList<>();
		instances.add(chunksA);
		instances.add(chunksB);
		instances.add(chunksC);

		Clone c = new Clone(instances);

		assertTrue(c.isConsistent());
	}
	
	@Test
	public void testIsConsistent_Wrong_length() {
		LinkedList<Chunk> chunksA = new LinkedList<>();
		chunksA.add(new Chunk("FileA.java", "aaa", 0, 1));
		chunksA.add(new Chunk("FileA.java", "bbb", 1, 2));
		chunksA.add(new Chunk("FileB.java", "ccc", 2, 3));
		
		LinkedList<Chunk> chunksB = new LinkedList<>();
		chunksB.add(new Chunk("FileB.java", "aaa", 8, 9));
		chunksB.add(new Chunk("FileB.java", "bbb", 9, 10));
		chunksB.add(new Chunk("FileB.java", "ccc", 10, 12));
		
		LinkedList<Chunk> chunksC = new LinkedList<>();
		chunksC.add(new Chunk("FileC.java", "aaa", 1221, 1222));
		chunksC.add(new Chunk("FileC.java", "bbb", 1222, 1223));
		chunksC.add(new Chunk("FileC.java", "ccc", 1223, 1224));
		
		List<LinkedList<Chunk>> instances = new ArrayList<>();
		instances.add(chunksA);
		instances.add(chunksB);
		instances.add(chunksC);
		
		Clone c = new Clone(instances);
		
		assertFalse(c.isConsistent());
	}
	
	@Test
	public void testCloneInSet() {
		LinkedList<Chunk> chunksA = new LinkedList<>();
		chunksA.add(new Chunk("FileA.java", "aaa", 0, 1));
		chunksA.add(new Chunk("FileA.java", "bbb", 1, 2));
		chunksA.add(new Chunk("FileB.java", "ccc", 2, 3));

		LinkedList<Chunk> chunksB = new LinkedList<>();
		chunksB.add(new Chunk("FileB.java", "aaa", 8, 9));
		chunksB.add(new Chunk("FileB.java", "bbb", 9, 10));
		chunksB.add(new Chunk("FileB.java", "ccc", 10, 11));
		
		LinkedList<Chunk> chunksC = new LinkedList<>();
		chunksC.add(new Chunk("FileC.java", "aaa", 1221, 1222));
		chunksC.add(new Chunk("FileC.java", "bbb", 1222, 1223));
		chunksC.add(new Chunk("FileC.java", "ccc", 1223, 1224));
		
		List<LinkedList<Chunk>> instances1 = new ArrayList<>();
		instances1.add(chunksA);
		instances1.add(chunksB);
		instances1.add(chunksC);
		
		Clone c1 = new Clone(instances1);
	
		
		LinkedList<Chunk> chunksD = new LinkedList<>();
		chunksD.add(new Chunk("FileD.java", "ddd", 0, 1));
		chunksD.add(new Chunk("FileD.java", "eee", 1, 2));
		chunksD.add(new Chunk("FileD.java", "fff", 2, 3));
		
		LinkedList<Chunk> chunksE = new LinkedList<>();
		chunksE.add(new Chunk("FileE.java", "eee", 8, 9));
		chunksE.add(new Chunk("FileE.java", "fff", 9, 10));
		chunksE.add(new Chunk("FileE.java", "ggg", 10, 11));
		
		List<LinkedList<Chunk>> instances2 = new ArrayList<>();
		instances2.add(chunksD);
		instances2.add(chunksE);
		
		Clone c2 = new Clone(instances2);
		Clone c3 = new Clone(instances2);
		
		LinkedList<Chunk> chunksG = new LinkedList<>();
		chunksG.add(new Chunk("FileD.java", "ddd", 0, 1));
		chunksG.add(new Chunk("FileD.java", "eee", 1, 2));
		chunksG.add(new Chunk("FileD.java", "fff", 2, 3));
		
		LinkedList<Chunk> chunksH = new LinkedList<>();
		chunksH.add(new Chunk("FileE.java", "eee", 8, 9));
		chunksH.add(new Chunk("FileE.java", "fff", 9, 10));
		chunksH.add(new Chunk("FileE.java", "ggg", 10, 11));
		
		List<LinkedList<Chunk>> instances3 = new ArrayList<>();
		instances3.add(chunksG);
		instances3.add(chunksH);
		
		Clone c4 = new Clone(instances3);
		
		SortedSet<Clone> clones = new TreeSet<>();
		clones.add(c1);
		assertEquals(1, clones.size());
		//Adding a different clone
		clones.add(c2);
		assertEquals(2, clones.size());
		//Adding again c1
		clones.add(c1);
		assertEquals(2, clones.size());
		//Adding again c2
		clones.add(c2);
		assertEquals(2, clones.size());
		//Adding a new clone with the same instances as c2
		clones.add(c3);
		assertEquals(2, clones.size());
		//Adding a new clone with different instances, that have however the same values
		clones.add(c4);
		assertEquals(2, clones.size());
	}

}
