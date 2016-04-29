package beast1to2;

import beast.core.BEASTInterface;
import beast.core.Description;
import beast.core.Input;
import beast.core.Input.Validate;
import beast.core.Runnable;
import beast.util.XMLProducer;
import dr.app.beast.BeastParser;
import dr.xml.XMLParseException;
import dr.xml.XMLParser;
import jam.util.IconUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import beast.app.beauti.BeautiConfig;
import beast.app.beauti.BeautiDoc;
import beast.app.draw.BEASTObjectDialog;
import beast.app.draw.BEASTObjectPanel;
import beast.app.util.*;

@Description("Convert BEAST 1 XML so it can be used by BEAST 2")
public class Beast1to2Converter extends Runnable { 
	public static final String NIY = " parser not implemented yet"; 
	public static final String PI = " parser partly implemented"; 
	public Input<XMLFile> xmlFileInput = new Input<>("xml", "BEASt 1 xml file", Validate.REQUIRED);
	public Input<OutFile> outFileInput = new Input<>("out", "BEASt 2 xml to output. If not specified, write to stdout");
	
	
	@Override
	public void initAndValidate() {
	}

	
	@Override
	public void run() throws Exception {
		File inputFile = xmlFileInput.get();
		BEASTInterface beast2Object = convert(inputFile);
		
		XMLProducer producer = new XMLProducer();
		String xml = producer.toXML(beast2Object);
		
		if (xml == null) {
			System.err.println("Conversion failed");
			return;
		}
		File outputFile = outFileInput.get();
		if (outputFile == null) {
			System.out.println(xml);
		} else {
	        FileWriter outfile = new FileWriter(outputFile);
	        outfile.write(xml);
	        outfile.close();
		}
	}


	private BEASTInterface convert(File inputFile) throws IOException, SAXException, XMLParseException, ParserConfigurationException {
        String fileName = inputFile.getName();
        FileReader fileReader = new FileReader(inputFile);
        List<String> additionalParsers = new ArrayList<String>();
        final boolean verbose = true;
        final boolean parserWarning = false; // if dev, then auto turn on, otherwise default to turn off
        final boolean strictXML = false;

        XMLParser parser = new BeastParser(new String[]{fileName}, additionalParsers, verbose, parserWarning, strictXML);

        parser.parse(fileReader, false);
        BEASTInterface beast2Object = (BEASTInterface) parser.getRoot().getNativeObject();
		return beast2Object;
	}
	
	
    static ConsoleApp app;
	public static void main(String[] args) throws Exception {
		Beast1to2Converter beast1to2Converter = new Beast1to2Converter();
		
		if (args.length == 0) {
			// create BeautiDoc and beauti configuration
			BeautiDoc doc = new BeautiDoc();
			doc.beautiConfig = new BeautiConfig();
			doc.beautiConfig.initAndValidate();
			
			// suppress a few inputs that we don't want to expose to the user
			doc.beautiConfig.suppressBEASTObjects.add(beast1to2Converter.getClass().getName() + ".mcmc");
			doc.beautiConfig.suppressBEASTObjects.add(beast1to2Converter.getClass().getName() + ".value");
			doc.beautiConfig.suppressBEASTObjects.add(beast1to2Converter.getClass().getName() + ".hosts");
		
			// create panel with entries for the application
			BEASTObjectPanel panel = new BEASTObjectPanel(beast1to2Converter, beast1to2Converter.getClass(), doc);
			
			// wrap panel in a dialog
			BEASTObjectDialog dialog = new BEASTObjectDialog(panel, null);
	
			// show the dialog
			if (dialog.showDialog()) {
				dialog.accept(beast1to2Converter, doc);
				// create a console to show standard error and standard output
				//ConsoleApp 
				app = new ConsoleApp("Beast1to2Converter", "Beast1to2Converter: " + beast1to2Converter.xmlFileInput.get().getPath(), 
						IconUtils.getIcon(Beast1to2Converter.class, "beast1to2.png"));
				beast1to2Converter.initAndValidate();
				beast1to2Converter.run();
			}
			System.out.println("All done");
			return;
		}

		Application main = new Application(beast1to2Converter);
		main.parseArgs(args, false);
		beast1to2Converter.initAndValidate();
		beast1to2Converter.run();
		System.out.println("All done");

	}
}
