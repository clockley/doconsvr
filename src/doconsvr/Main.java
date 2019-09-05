package doconsvr;
import static spark.Spark.post;

import javax.servlet.MultipartConfigElement;

import org.apache.pdfbox.io.RandomAccessBuffer;

import spark.servlet.SparkApplication;

public class Main implements SparkApplication {
	@Override
	public void init() {
		post("/uploadPDF", (request, response) -> {
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/tmp"));
            return PDFtoText.Parse(new RandomAccessBuffer(request.raw().getPart("uploaded_file").getInputStream()));
		});
	}
	
	public static void main(String[] args) {
		new Main().init();
	}
}