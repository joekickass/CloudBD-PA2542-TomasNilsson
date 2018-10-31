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

import org.junit.Test;

/**
 * 
 * @author Michael Unterkalmsteiner
 *
 */
public class ChunkTest {

	@Test
	public void testChunkOverlapsWith() {
		Chunk a = new Chunk("File A", "", 7, 22);
		Chunk b = new Chunk("File A", "", 7, 22);
		assertTrue(a.overlapsWith(b));
		assertTrue(b.overlapsWith(a));

		b = new Chunk("File A", "", 1, 6);
		assertFalse(a.overlapsWith(b));
		assertFalse(b.overlapsWith(a));
		
		b = new Chunk("File A", "", 1, 7);
		assertTrue(a.overlapsWith(b));
		assertTrue(b.overlapsWith(a));
		
		b = new Chunk("File A", "", 1, 8);
		assertTrue(a.overlapsWith(b));
		assertTrue(b.overlapsWith(a));
		
		b = new Chunk("File A", "", 21, 30);
		assertTrue(a.overlapsWith(b));
		assertTrue(b.overlapsWith(a));
		
		b = new Chunk("File A", "", 22, 30);
		assertTrue(a.overlapsWith(b));
		assertTrue(b.overlapsWith(a));
		
		b = new Chunk("File A", "", 23, 30);
		assertFalse(a.overlapsWith(b));
		assertFalse(b.overlapsWith(a));
		
		b = new Chunk("File A", "", 1, 30);
		assertTrue(a.overlapsWith(b));
		assertTrue(b.overlapsWith(a));
		
		b = new Chunk("File A", "", 10, 20);
		assertTrue(a.overlapsWith(b));
		assertTrue(b.overlapsWith(a));
		
		b = new Chunk("File B", "", 10, 20);
		assertFalse(a.overlapsWith(b));
		assertFalse(b.overlapsWith(a));
	}

}
