package org.verapdf.model.impl.pb.operator.textposition;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;
import org.verapdf.model.baselayer.Object;
import org.verapdf.model.coslayer.CosNumber;
import org.verapdf.model.operator.Op_Tm;

/**
 * @author Evgeniy Muravitskiy
 */
public class PBOp_Tm extends PBOpTextPosition implements Op_Tm {

	public static final String OP_TM_TYPE = "Op_Tm";

	public static final String CONTROL_POINTS = "controlPoints";

	public PBOp_Tm(List<COSBase> arguments) {
		super(arguments, OP_TM_TYPE);
	}

	@Override
	public List<? extends Object> getLinkedObjects(String link) {
		if (CONTROL_POINTS.equals(link)) {
			return this.getControlPoints();
		}
		return super.getLinkedObjects(link);
	}

	private List<CosNumber> getControlPoints() {
		return this.getListOfNumbers();
	}

}
