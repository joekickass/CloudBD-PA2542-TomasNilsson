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

/**
 * 
 * @author Michael Unterkalmsteiner
 *
 */
public class Chunk {
	/** The origin of the chunk (e.g. a filename). */
	private final String originId;

	/** Content of the chunk. */
	private final String chunkContent;

	/** First raw line of the chunk (0-based, inclusive). */
	private final int firstRawLineNumber;

	/** Last raw line of the chunk (0-based, exclusive). */
	private final int lastRawLineNumber;
	
	/** If stored in a list, this can be set to find neighboring chunks */
	private int index;

	/** Constructor. */
	public Chunk(String originId, String chunkContent, 
			int firstRawLineNumber, int lastRawLineNumber) {
		this.originId = originId;
		this.chunkContent = chunkContent;
		this.firstRawLineNumber = firstRawLineNumber;
		this.lastRawLineNumber = lastRawLineNumber;
	}

	/** Returns the origin of the chunk (for example the name of the file). */
	public String getOriginId() {
		return originId;
	}

	/** Returns the content of the chunk. */
	public String getChunkContent() {
		return chunkContent;
	}

	/** Returns the first raw line of the chunk (1-based, exclusive). */
	public int getFirstRawLineNumber() {
		return firstRawLineNumber;
	}

	/** Returns the last raw line of the chunk (1-based, exclusive). */
	public int getLastRawLineNumber() {
		return lastRawLineNumber;
	}
	
	public void setIndex(int i) {
		index = i;
	}
	
	public int getIndex() {
		return index;
	}
	
	public boolean overlapsWith(Chunk c) {
		if(!originId.equals(c.originId))
			return false;
		
		return this.interval().overlaps(c.interval());
	}

	/** {@inheritDoc} */
	@Override
	public boolean equals(Object obj) {
		if(obj == null)
			return false;
		
		if(this == obj)
			return true;
		
		if(!(obj instanceof Chunk)) {
			return false;
		}
		Chunk other = (Chunk) obj;
		return other.originId.equals(originId)
				&& other.chunkContent.equals(chunkContent)
				&& other.firstRawLineNumber == firstRawLineNumber
				&& other.lastRawLineNumber == lastRawLineNumber
				&& other.index == index;
	}

	/** {@inheritDoc} */
	@Override
	public int hashCode() {
		int result = 1;
		result = 37 * result + originId.hashCode();
		result = 37 * result + chunkContent.hashCode();
		result = 37 * result + firstRawLineNumber;
		result = 37 * result + lastRawLineNumber;
		result = 37 * result + index;
		return result;
	}
	
	private Interval interval() {
		return new Interval(firstRawLineNumber, lastRawLineNumber);
	}
	
	private class Interval {
		private int start;
		private int end;
		
		public Interval(int start, int end) {
			this.start = start;
			this.end = end;
		}
		
		public boolean overlaps(Interval i) {
			if((end < i.start) || (start > i.end))
				return false;
			return true;	
		}
	}
}
