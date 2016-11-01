/**
 * 
 */
package org.verapdf.pdfa;

import java.io.InputStream;
import java.net.URI;

import org.verapdf.ReleaseDetails;
import org.verapdf.component.ComponentDetails;
import org.verapdf.component.Components;
import org.verapdf.core.EncryptedPdfException;
import org.verapdf.core.ModelParsingException;
import org.verapdf.metadata.fixer.PBoxMetadataFixerImpl;
import org.verapdf.model.ModelParser;
import org.verapdf.pdfa.flavours.PDFAFlavour;

/**
 * @author <a href="mailto:carl@openpreservation.org">Carl Wilson</a>
 *         <a href="https://github.com/carlwilson">carlwilson AT github</a>
 * @version 0.1 Created 22 Sep 2016:09:20:18
 */

class PdfBoxFoundry extends AbstractFoundry {
	private static final URI id = URI.create("http://pdfa.verapdf.org/Foundry#pdfbox");
	private static final ReleaseDetails pdfBoxReleaseDetails = ReleaseDetails.addDetailsFromResource(
			ReleaseDetails.APPLICATION_PROPERTIES_ROOT + "pdfbox-validation." + ReleaseDetails.PROPERTIES_EXT);

	private static final ComponentDetails details = Components.veraDetails(id, "VeraPDF PDFBox Foundry",
			pdfBoxReleaseDetails.getVersion());
	private static final PdfBoxFoundry instance = new PdfBoxFoundry();

	private PdfBoxFoundry() {
		super();
	}

	@Override
	public ComponentDetails getDetails() {
		return details;
	}

	/**
	 * @see org.verapdf.pdfa.VeraPDFFoundry#newPdfParser(java.io.InputStream)
	 */
	@Override
	public PDFParser createParser(InputStream pdfStream) throws ModelParsingException, EncryptedPdfException {
		return createParser(pdfStream, PDFAFlavour.NO_FLAVOUR);
	}

	/**
	 * @see org.verapdf.pdfa.VeraPDFFoundry#newPdfParser(java.io.InputStream,
	 *      org.verapdf.pdfa.flavours.PDFAFlavour)
	 */
	@Override
	public PDFParser createParser(InputStream pdfStream, PDFAFlavour flavour)
			throws ModelParsingException, EncryptedPdfException {
		return ModelParser.createModelWithFlavour(pdfStream, flavour);
	}

	/**
	 * @see org.verapdf.pdfa.VeraPDFFoundry#newMetadataFixer(org.verapdf.metadata.fixer.utils.FixerConfig)
	 */
	@Override
	public MetadataFixer newMetadataFixer() {
		return new PBoxMetadataFixerImpl();
	}

	public static ReleaseDetails getReleaseDetails() {
		return pdfBoxReleaseDetails;
	}

	static VeraPDFFoundry getInstance() {
		return instance;
	}
}