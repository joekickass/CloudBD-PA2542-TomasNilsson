/*
 * Copyright <2017> <Blekinge Tekniska Högskola>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining 
 * a copy of this software and associated documentation files (the "Software"), 
 * to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 * and/or sell copies of the Software, and to permit persons to whom the 
 * Software is furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
 * DEALINGS IN THE SOFTWARE.
 */

package se.bth.serl.clony.chunks;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

import org.junit.Test;

import se.bth.serl.clony.processors.SourceProcessor;

/**
 * 
 * @author Michael Unterkalmsteiner
 *
 */
public class BaseChunkCollectionTest {

	@Test
	public void testAddChunks() throws Exception {
		File f = new File("data/A.java");
		
		for(int chunkSize = 0; chunkSize < 100; chunkSize++) {	
			SourceProcessor sp = new SourceProcessor(f.toPath(), chunkSize, new ListChunkCollection());
			BaseChunkCollection bcc = sp.populateChunkCollection();
			assertEquals(expectedNumberOfChunks(f.toPath(), chunkSize), bcc.getChunks().size());
		}		
	}

	@Test
	public void testComputeClonesList() {
		SourceProcessor sp = new SourceProcessor(new File("data/A.java").toPath(), 5, new ListChunkCollection());
		BaseChunkCollection bcc = sp.populateChunkCollection();
		assertFalse(bcc.isEmpty());
		SortedSet<Clone> clones = bcc.getClones();
		assertEquals(1, clones.size());
		clones.stream().forEach(e -> assertTrue(e.isConsistent()));
		Clone c = clones.first();
		assertEquals(7, c.size()); 
		assertEquals(2, c.instances());
		
		sp = new SourceProcessor(new File("data").toPath(), 5, new ListChunkCollection());
		bcc = sp.populateChunkCollection();
		assertFalse(bcc.isEmpty());
		clones = bcc.getClones();
		assertEquals(4, clones.size());
		clones.stream().forEach(e -> assertTrue(e.isConsistent()));
		
		int[] cloneSizes = {14, 12, 7, 7};
		int[] cloneInstances = {2, 2, 3, 2};
		int i = 0;
		for(Clone cl : clones) {
			assertEquals(cloneSizes[i], cl.size());
			if(cloneInstances[i] != cl.instances())
				fail("\nHere we see one \"bug\" in the clone detection using the linear list. The order \n" +
						"in which files are read, and consequentially, clones are detected, matters. \n" + 
						"In this test, file B is read before file A (this seems arbitrary). Then, the \n" +
						"first clone detected is B(1-12)A(24-35). Then, we expand to the chunk that contains \n" +
						"line B(13), i.e skip lines B(4-10), shadowing the clone instance B(4-10) in clone \n" + 
						"A(15-21)A(27-33). \nFeel free to fix ;)");
			i++;
		}
	}
	

	@Test
	public void testIsEmpty() {
		List<BaseChunkCollection> collections = new ArrayList<>();
		collections.add(new ListChunkCollection());
		
		for(BaseChunkCollection bcc : collections) 
			assertTrue(bcc.isEmpty());
	}
	
	private int expectedNumberOfChunks(Path path, int chunkSize) throws Exception {
		int exp = 0;
		List<Path> listOfFiles = Files.walk(path, Integer.MAX_VALUE).filter(Files::isRegularFile).filter(p -> p.toString().endsWith(".java")).collect(Collectors.toList());
		if(listOfFiles != null) {
			try {
				for(Path p: listOfFiles) {
					int numlines =  Files.readAllLines(p).size(); 
					int cs = chunkSize > 0 ? chunkSize : SourceProcessor.DEFAULT_CHUNKSIZE;
					if(numlines >= cs)  // If minimum clone length, i.e. the chunk size, is larger than the file, no chunks are expected
						exp += (numlines - cs) + 1;  
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return exp;
	}
}
