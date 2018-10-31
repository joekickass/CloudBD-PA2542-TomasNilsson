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

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Michael Unterkalmsteiner
 *
 */
public abstract class BaseChunkCollection {
	protected SortedSet<Clone> clones;
	
	public BaseChunkCollection() {
		clones = new TreeSet<>();
	}
	
	public abstract void addChunk(Chunk c);
	public abstract List<Chunk> getChunks();
	public abstract SortedSet<Clone> getClones();
	
	public abstract boolean isEmpty();
	
	//TODO implement expansion. HINT: think recursively
	protected int expand(List<Chunk> a, List<Chunk> b) {
		return 0;
	}
}
