package org.verapdf.model.impl.pb.pd.font;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.font.PDTrueTypeFont;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.verapdf.model.baselayer.Object;
import org.verapdf.model.coslayer.CosName;
import org.verapdf.model.pdlayer.PDSimpleFont;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author Timur Kamalov
 */
public class PBoxPDTrueTypeFontTest extends PBoxPDSimpleFontTest {

	private static final String TRUETYPE_FONT_NAME = "TT0";
	private static final String TRUETYPE_SUBTYPE = "TrueType";

	private static final Long WIDTHS_SIZE = new Long(90l);
	private static final Long FIRST_CHAR =  new Long(32l);
	private static final Long LAST_CHAR =  new Long(121l);

	@BeforeClass
	public static void setUp() throws IOException, URISyntaxException {
		expectedType = TYPES.contains(PBoxPDTrueTypeFont.TRUETYPE_FONT_TYPE) ? PBoxPDTrueTypeFont.TRUETYPE_FONT_TYPE : null;

		setUp(FILE_RELATIVE_PATH);
		PDTrueTypeFont trueTypeFont = (PDTrueTypeFont) document.getPage(0).getResources().getFont(COSName.getPDFName(TRUETYPE_FONT_NAME));
		actual = new PBoxPDTrueTypeFont(trueTypeFont, defaultRenderingMode);

		expectedID = trueTypeFont.getCOSObject().hashCode() + " CUQUFZ+GillSansMT";
	}

	@Override
	public void testBaseFont() {
		List<? extends Object> baseFonts = actual.getLinkedObjects(PBoxPDFont.BASE_FONT);
		Object object = baseFonts.get(0);
		Assert.assertEquals("CosUnicodeName", object.getObjectType());
		Assert.assertEquals("CUQUFZ+GillSansMT", ((CosName) object).getinternalRepresentation());
	}

	@Override
	public void testSubtypeMethod() {
		Assert.assertEquals(TRUETYPE_SUBTYPE, ((PDSimpleFont) actual).getSubtype());
	}

	@Override
	public void testWidthsSize() {
		Assert.assertEquals(WIDTHS_SIZE, ((PDSimpleFont) actual).getWidths_size());
	}

	@Override
	public void testLastChar() {
		Assert.assertEquals(LAST_CHAR, ((PDSimpleFont) actual).getLastChar());
	}

	@Override
	public void testFirstChar() {
		Assert.assertEquals(FIRST_CHAR, ((PDSimpleFont) actual).getFirstChar());
	}

	@Override
	public void testIsStandard() {
		Assert.assertFalse(((PDSimpleFont) actual).getisStandard().booleanValue());
	}

	@Override
	public void testEncoding() {
		Assert.assertEquals(((org.verapdf.model.pdlayer.PDTrueTypeFont) actual).getEncoding(), "WinAnsiEncoding");
	}

}
