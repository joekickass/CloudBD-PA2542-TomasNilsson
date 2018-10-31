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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

/**
 * 
 * @author Michael Unterkalmsteiner
 *
 */
public class ListChunkCollection extends BaseChunkCollection {
	private List<Chunk> chunks;
	
	public ListChunkCollection() {
		chunks = new ArrayList<>();
	}
	
	@Override
	public void addChunk(Chunk c) {
		c.setIndex(chunks.size());
		chunks.add(c);
	}

	/**
	 * 
	 */
	@Override
	public SortedSet<Clone> getClones() {
		if(clones.isEmpty()) {
			int numChunks = chunks.size();
						
 			for(int i = 0; i < numChunks; i++) {
 				List<LinkedList<Chunk>> listOfInstances = new ArrayList<>();
				Chunk a = chunks.get(i);
				int maxExpansion = 0;
				for(int j = i + 1; j < numChunks; j++) {
					Chunk b = chunks.get(j);
					
					if(a.getChunkContent().equals(b.getChunkContent())) {
						int expansion = expand(chunks.subList(i + 1, j), chunks.subList(j + 1, chunks.size()));
						
						if(listOfInstances.size() == 0) { //Add the first instance only once
							LinkedList<Chunk> instance = new LinkedList<>();
							int start = i;
							int end = expansion > 0 ? i + expansion : start;
							for(int k = i; k <= end; k++)
								instance.add(chunks.get(k));
							listOfInstances.add(instance);
						}
						
						LinkedList<Chunk> instance = new LinkedList<>();
						int start = j;
						int end = expansion > 0 ? j + expansion : start;
						for(int k = j; k <= end; k++) 
							instance.add(chunks.get(k));
						listOfInstances.add(instance);
							
						if(expansion > maxExpansion)
							maxExpansion = expansion;
					}
				}
				
				if(listOfInstances.size() > 0)
					clones.add(new Clone(listOfInstances));
							
				i = i + maxExpansion;
				
				System.out.println("Chunks processed: " + (i + 1) + "/" + numChunks);
			}
		}
		
		return clones;
	}

	@Override
	public boolean isEmpty() {
		return chunks.isEmpty();
	}

	@Override
	public List<Chunk> getChunks() {
		return chunks;
	}

}
