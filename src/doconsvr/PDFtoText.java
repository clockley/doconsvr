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
            string = pdfTextStripper.getText(pdDocument).replaceAll("\\p{C}", "");
            pdDocument.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        };
        return string;
	}
}
