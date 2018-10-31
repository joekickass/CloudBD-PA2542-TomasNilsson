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

package se.bth.serl.clony;

import java.io.File;
import java.nio.file.Path;

import se.bth.serl.clony.chunks.ListChunkCollection;
import se.bth.serl.clony.processors.SourceProcessor;

public class NaiveDetector extends DetectorBase {
	
	public NaiveDetector(Path rootFolder, int chunkSize) {
		super(rootFolder, chunkSize);
		sp = new SourceProcessor(rootFolder, chunkSize, new ListChunkCollection());
	}
	
	public static void main(String[] args) {
		Path rootFolder = new File(args[0]).toPath();
		int chunksize = Integer.parseInt(args[1]);
		
		long start = System.currentTimeMillis();
		NaiveDetector d = new NaiveDetector(rootFolder, chunksize);
		d.run();
		d.saveResults();
		System.out.println("Runtime (s): " + (System.currentTimeMillis() - start) / 1000);
		
	}
}
