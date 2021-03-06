/**
 * This file is part of veraPDF PDF Box PDF/A Validation Model Implementation, a module of the veraPDF project.
 * Copyright (c) 2015, veraPDF Consortium <info@verapdf.org>
 * All rights reserved.
 *
 * veraPDF PDF Box PDF/A Validation Model Implementation is free software: you can redistribute it and/or modify
 * it under the terms of either:
 *
 * The GNU General public license GPLv3+.
 * You should have received a copy of the GNU General Public License
 * along with veraPDF PDF Box PDF/A Validation Model Implementation as the LICENSE.GPL file in the root of the source
 * tree.  If not, see http://www.gnu.org/licenses/ or
 * https://www.gnu.org/licenses/gpl-3.0.en.html.
 *
 * The Mozilla Public License MPLv2+.
 * You should have received a copy of the Mozilla Public License along with
 * veraPDF PDF Box PDF/A Validation Model Implementation as the LICENSE.MPL file in the root of the source tree.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */
package org.verapdf.model.factory.colors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.graphics.color.*;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDAbstractPattern;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDShadingPattern;
import org.apache.pdfbox.pdmodel.graphics.pattern.PDTilingPattern;
import org.verapdf.model.impl.pb.containers.StaticContainers;
import org.verapdf.model.impl.pb.pd.colors.*;
import org.verapdf.model.impl.pb.pd.pattern.PBoxPDShadingPattern;
import org.verapdf.model.impl.pb.pd.pattern.PBoxPDTilingPattern;
import org.verapdf.model.pdlayer.PDColorSpace;
import org.verapdf.model.pdlayer.PDPattern;
import org.verapdf.model.tools.resources.PDInheritableResources;
import org.verapdf.pdfa.flavours.PDFAFlavour;

/**
 * Factory for transforming PDColorSpace objects of pdfbox to corresponding
 * PDColorSpace objects of veraPDF-library.
 *
 * @author Evgeniy Muravitskiy
 */
public class ColorSpaceFactory {

	public static final String CAL_GRAY = "CalGray";
	public static final String CAL_RGB = "CalRGB";
	public static final String DEVICE_CMYK = "DeviceCMYK";
	public static final String DEVICE_RGB = "DeviceRGB";
	public static final String DEVICE_GRAY = "DeviceGray";
	public static final String DEVICE_N = "DeviceN";
	public static final String ICC_BASED = "ICCBased";
	public static final String LAB = "Lab";
	public static final String SEPARATION = "Separation";
	public static final String INDEXED = "Indexed";
	public static final String PATTERN = "Pattern";

	private ColorSpaceFactory() {
		// disable default constructor
	}

	/**
	 * Transform object of pdfbox to corresponding object of veraPDF-library (
	 * {@link org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace} to
	 * {@link PBoxPDColorSpace}).
	 *
	 * @param colorSpace
	 *            pdfbox color space object
	 * @return {@code <? extends PBoxPDColorSpace>} object or {@code null} if
	 *         {@code colorSpace} argument {@code null} or unsupported type
	 */
	public static PDColorSpace getColorSpace(org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace colorSpace,
			PDDocument document, PDFAFlavour flavour) {
		return getColorSpace(colorSpace, null, PDInheritableResources.EMPTY_EXTENDED_RESOURCES, 0, false, document,
				flavour);
	}

	/**
	 * Transform object of pdfbox to corresponding object of veraPDF-library (
	 * {@link org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace} to
	 * {@link PBoxPDColorSpace}). If color space is pattern color space, than
	 * transform to Pattern object.
	 *
	 * @param colorSpace
	 *            pdfbox color space object
	 * @param pattern
	 *            pattern of pattern color space
	 * @return {@code <? extends PBoxPDColorSpace>} object or {@code null} if
	 *         {@code colorSpace} argument is {@code null},{@code pattern}
	 *         argument is {@code null} or unsupported type of color space
	 */
	public static PDColorSpace getColorSpace(org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace colorSpace,
			PDAbstractPattern pattern, PDInheritableResources resources, int opm, boolean overprintingFlag,
			PDDocument document, PDFAFlavour flavour) {
		if (colorSpace == null) {
			return null;
		}
		if (StaticContainers.cachedColorSpaces.containsKey(colorSpace)) {
			return StaticContainers.cachedColorSpaces.get(colorSpace);
		}
		PDColorSpace result;
		switch (colorSpace.getName()) {
		case CAL_GRAY:
			result = new PBoxPDCalGray((PDCalGray) colorSpace);
			StaticContainers.cachedColorSpaces.put(colorSpace, result);
			return result;
		case CAL_RGB:
			result = new PBoxPDCalRGB((PDCalRGB) colorSpace);
			StaticContainers.cachedColorSpaces.put(colorSpace, result);
			return result;
		case DEVICE_N:
			result = new PBoxPDDeviceN((PDDeviceN) colorSpace, document, flavour);
			StaticContainers.cachedColorSpaces.put(colorSpace, result);
			return result;
		case DEVICE_CMYK:
			if (colorSpace.isInherited()) {
				return PBoxPDDeviceCMYK.getInheritedInstance();
			}
			return PBoxPDDeviceCMYK.getInstance();
		case DEVICE_RGB:
			if (colorSpace.isInherited()) {
				return PBoxPDDeviceRGB.getInheritedInstance();
			}
			return PBoxPDDeviceRGB.getInstance();
		case DEVICE_GRAY:
			if (colorSpace.isInherited()) {
				return PBoxPDDeviceGray.getInheritedInstance();
			}
			return PBoxPDDeviceGray.getInstance();
		case ICC_BASED:
			if (colorSpace.getNumberOfComponents() != 4) {
				result = new PBoxPDICCBased((PDICCBased) colorSpace);
				StaticContainers.cachedColorSpaces.put(colorSpace, result);
				return result;
			}
			// we don't want to cache ICCBasedCMYK color space because it can be
			// used with different extgstates
			result = new PBoxPDICCBasedCMYK((PDICCBased) colorSpace, opm, overprintingFlag);
			return result;
		case LAB:
			result = new PBoxPDLab((PDLab) colorSpace);
			StaticContainers.cachedColorSpaces.put(colorSpace, result);
			return result;
		case SEPARATION:
			result = new PBoxPDSeparation((PDSeparation) colorSpace, document, flavour);
			StaticContainers.cachedColorSpaces.put(colorSpace, result);
			return result;
		case INDEXED:
			result = new PBoxPDIndexed((PDIndexed) colorSpace, document, flavour);
			StaticContainers.cachedColorSpaces.put(colorSpace, result);
			return result;
		case PATTERN:
			return getPattern(pattern, resources, document, flavour);
		default:
			return null;
		}
	}

	/**
	 * Transform object of pdfbox to corresponding object of veraPDF-library (
	 * {@link org.apache.pdfbox.pdmodel.graphics.pattern.PDAbstractPattern} to
	 * {@link org.verapdf.model.impl.pb.pd.pattern.PBoxPDPattern})
	 *
	 * @param pattern
	 *            pdfbox pattern object
	 * @param resources
	 *            page resources for tiling pattern
	 * @return {@code <? extends PDPattern>} object or {@code null} if
	 *         {@code pattern} argument is {@code null}
	 */
	public static PDPattern getPattern(PDAbstractPattern pattern, PDInheritableResources resources, PDDocument document,
			PDFAFlavour flavour) {
		if (pattern != null) {
			if (pattern.getPatternType() == PDAbstractPattern.TYPE_SHADING_PATTERN) {
				return new PBoxPDShadingPattern((PDShadingPattern) pattern, document, flavour);
			} else if (pattern.getPatternType() == PDAbstractPattern.TYPE_TILING_PATTERN) {
				PDTilingPattern tiling = (PDTilingPattern) pattern;
				PDInheritableResources pdResources = resources.getExtendedResources(tiling.getResources());
				return new PBoxPDTilingPattern(tiling, pdResources, document, flavour);
			}
		}
		return null;
	}

}
