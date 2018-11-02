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

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.mozilla.universalchardet.UniversalDetector;

/**
 * 
 * @author Michael Unterkalmsteiner
 */
public class SourceReader {
	private List<SourceLine> fileContent;
	private static final Pattern emptyLine = Pattern.compile("^\\s*$");
	private static final Pattern oneLineComment = Pattern.compile("//.*");
	private static final Pattern oneLineMultiLineComment = Pattern.compile("/\\*.*?\\*/");
	private static final Pattern openMultiLineComment = Pattern.compile("/\\*+[^*/]*$");
	private static final Pattern closeMultiLineComment = Pattern.compile("^[^*/]*\\*+/");
	
	public SourceReader(Path path) {
		this(readFileContent(path.toFile()));
	}
	
	public SourceReader(File file) {
		this(readFileContent(file));
	}
		
	public SourceReader(List<String> lines) {
		fileContent = new ArrayList<>();
		
		if(lines != null) {
			boolean inMultiLineComment = false;
			for(int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);

				Matcher m;
				
				if(inMultiLineComment) {
					m = closeMultiLineComment.matcher(line);
					if(m.find()) {
						line = m.replaceAll("");
						inMultiLineComment = false;
					}
					else {
						fileContent.add(new SourceLine(i, ""));
						continue;
					}
				}
				
				m = oneLineComment.matcher(line);
				line = m.replaceAll("");
				
				m = oneLineMultiLineComment.matcher(line);
				line = m.replaceAll("");
				
				m = openMultiLineComment.matcher(line);
				if(m.find()) {
					line = m.replaceAll("");
					inMultiLineComment = true;
				}
			
				m = emptyLine.matcher(line);
				line = m.replaceAll("");
				
				fileContent.add(new SourceLine(i + 1, line.trim()));
			}
		}
	}
	
	public List<SourceLine> getOnlySourceWithContent() {
		return fileContent.stream().filter(p -> p.hasContent()).collect(Collectors.toList());
	}
	
	private static List<String> readFileContent(File file) {
		List<String> lines = null;

		try {
			// Try to detect charset
            String encoding = UniversalDetector.detectCharset(file);
            if (encoding != null) {
				System.out.println("Detected charset: " + encoding + " in " + file.getName());
				Charset charset = Charset.forName(encoding);
				lines = Files.readAllLines(file.toPath(), charset);
            } else {
				System.out.println("No charset detected in " + file.getName());
				lines = Files.readAllLines(file.toPath());
            }
		}
		catch(IOException e) {
			e.printStackTrace();
			System.err.println("Could not read file: " + file.getAbsoluteFile());
			/* TODO Files in the Qualitas corpus are encoded in different 
			 * formats (ASCII, UTF-8, ISO-8859). Either convert the input data beforehand or adapt this code here
			 * so that all files are correctly read. 
			*/
		}
		
		return lines;
	}
	

}
