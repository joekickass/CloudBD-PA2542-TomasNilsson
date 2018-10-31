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

package se.bth.serl.clony.processors;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import se.bth.serl.clony.processors.SourceLine;
import se.bth.serl.clony.processors.SourceReader;

/**
 * 
 * @author Michael Unterkalmsteiner
 *
 */
public class SourceReaderTest {

	@Test
	public void testGetOnlySourceWithContent() {
		List<String> rawContent = new ArrayList<>();
		rawContent.add("This is no comment.");
		rawContent.add("/* first*/ Bla /* second */ Blub //Comment");
		rawContent.add("");
		rawContent.add("Ha /*Multi start");
		rawContent.add("Multi continues...");
		rawContent.add("Multi continues...");
		rawContent.add("Multi stops *//* zig */ Text");
		rawContent.add("//Comment");
		
		SourceReader sr = new SourceReader(rawContent);
		List<SourceLine> source = sr.getOnlySourceWithContent();
		assertEquals(4, source.size());
		assertEquals("This is no comment.", source.get(0).getContent());
		assertEquals(1, source.get(0).getLineNumber());
		assertEquals("Bla  Blub", source.get(1).getContent());
		assertEquals(2, source.get(1).getLineNumber());
		assertEquals("Ha", source.get(2).getContent());
		assertEquals(4, source.get(2).getLineNumber());
		assertEquals("Text", source.get(3).getContent());
		assertEquals(7, source.get(3).getLineNumber());
	}

}
