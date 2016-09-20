package org.verapdf.model.impl.pb.operator.pathconstruction;

import java.util.List;

import org.apache.pdfbox.cos.COSBase;
import org.verapdf.model.baselayer.Object;
import org.verapdf.model.coslayer.CosNumber;
import org.verapdf.model.operator.Op_v;

/**
 * Operator which appends a cubic Bézier curve to the current path
 *
 * @author Timur Kamalov
 */
public class PBOp_v extends PBOpPathConstruction implements Op_v {

	/** Type name for {@code PBOp_v} */
    public static final String OP_V_TYPE = "Op_v";

    public PBOp_v(List<COSBase> arguments) {
        super(arguments, OP_V_TYPE);
    }

    @Override
    public List<? extends Object> getLinkedObjects(
            String link) {
        if (CONTROL_POINTS.equals(link)) {
            return this.getControlPoints();
        }
        return super.getLinkedObjects(link);
    }

    private List<CosNumber> getControlPoints() {
        return this.getListOfNumbers();
    }

}
