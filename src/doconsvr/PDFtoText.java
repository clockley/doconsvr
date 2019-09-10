package doconsvr;

import java.io.*;

import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import io.github.jonathanlink.PDFLayoutTextStripper;

class PDFtoText {
	static public String Parse(RandomAccessBuffer file) {
		String string = null;
		StringBuilder cleanString = null;
        try {
            PDFParser pdfParser = new PDFParser(file, "r");
            pdfParser.parse();
            PDDocument pdDocument = new PDDocument(pdfParser.getDocument());
            PDFTextStripper pdfTextStripper = new PDFLayoutTextStripper();
            string = pdfTextStripper.getText(pdDocument);
            //https://stackoverflow.com/questions/6198986/how-can-i-replace-non-printable-unicode-characters-in-java
            cleanString = new StringBuilder(string.length());
            for (int offset = 0; offset < cleanString.length();) {
                int codePoint = string.codePointAt(offset);
                offset += Character.charCount(codePoint);

                // Replace invisible control characters and unused code points
                switch (Character.getType(codePoint))
                {
                    case Character.CONTROL:     // \p{Cc}
                    case Character.FORMAT:      // \p{Cf}
                    case Character.PRIVATE_USE: // \p{Co}
                    case Character.SURROGATE:   // \p{Cs}
                    case Character.UNASSIGNED:  // \p{Cn}
                    	cleanString.append('?');
                        break;
                    default:
                    	cleanString.append(Character.toChars(codePoint));
                        break;
                }  	
            }
            pdDocument.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        return cleanString.toString();
	}
}
