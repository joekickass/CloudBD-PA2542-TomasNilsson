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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.SortedSet;

import se.bth.serl.clony.chunks.BaseChunkCollection;
import se.bth.serl.clony.chunks.Clone;
import se.bth.serl.clony.processors.SourceProcessor;

/**
 * Base class for a clone detector.
 * @author Michael Unterkalmsteiner
 *
 */
public class DetectorBase {
	protected File rootFolder;
	protected int chunkSize;
	protected SourceProcessor sp;
	protected SortedSet<Clone> clones;
	
	public DetectorBase(Path rootFolder, int chunkSize) {
		this.rootFolder = rootFolder.toFile();
		this.chunkSize = chunkSize;
	}
	
	public void run() {
		BaseChunkCollection bcc = sp.populateChunkCollection();
		clones = bcc.getClones();
	}
	
	public void printResults() {
		clones.forEach(clone->System.out.println(clone));
			
		System.out.println("Total files/lines scanned: " + sp.totalFilesProcessed() + "/" + sp.totalLinesProcessed());
		System.out.println("Clones of size " + chunkSize + " or larger found: " + clones.size());
		System.out.println("Largest clone: " + (clones.size() > 0 ? clones.first().size() : 0));
	}
	
	public void saveResults() {
		String filename = this.getClass().getName() + "." + System.currentTimeMillis() + ".clones";
		
		try { 
			FileWriter fw = new FileWriter(filename);
			fw.write("Root folder: " + rootFolder.getAbsolutePath() + "\n");
			fw.write("Total files/lines scanned: " + sp.totalFilesProcessed() + "/" + sp.totalLinesProcessed() + "\n");
			fw.write("Clones of size " + chunkSize + " or larger found: " + clones.size() + "\n");
			fw.write("Largest clone: " + (clones.size() > 0 ? clones.first().size() : 0) + "\n");
			fw.write("\n");
			
			for(Clone c : clones)
				fw.write(c.getCloneInfo());
			
			fw.close();
			
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		
	}
}
