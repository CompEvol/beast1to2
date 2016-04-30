/*
 * DataTypeUtils.java
 *
 * Copyright (c) 2002-2015 Alexei Drummond, Andrew Rambaut and Marc Suchard
 *
 * This file is part of BEAST.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership and licensing.
 *
 * BEAST is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 *  BEAST is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with BEAST; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor,
 * Boston, MA  02110-1301  USA
 */

package dr.evoxml.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import beast.evolution.datatype.DataType;
import beast.util.AddOnManager;
import beast1to2.Beast1to2Converter;
import dr.evolution.datatype.GeneticCode;
import dr.xml.XMLObject;
import dr.xml.XMLParseException;

/**
 * @author Alexei Drummond
 *
 * @version $Id$
 */
public class DataTypeUtils {

    static Map<String, DataType> types = new HashMap<>();

    static {
        findDataTypes();
    }

    static public void findDataTypes() {
        final String[] IMPLEMENTATION_DIR = {"beast.evolution.datatype"};
        // build up list of data types
        List<String> m_sDataTypes = AddOnManager.find(beast.evolution.datatype.DataType.class, IMPLEMENTATION_DIR);
        for (String dataTypeName : m_sDataTypes) {
            try {
                DataType dataType = (DataType) Class.forName(dataTypeName).newInstance();
                if (dataType.isStandard()) {
                    String description = dataType.getTypeDescription();
                    if (!types.containsKey(description)) {
                        types.put(description, dataType);
                    }
                }
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

    public static DataType getDataType(XMLObject xo) throws XMLParseException {

        DataType dataType = null;

        if (xo.hasAttribute(dr.evolution.datatype.DataType.DATA_TYPE)) {
            String dataTypeStr = xo.getStringAttribute(dr.evolution.datatype.DataType.DATA_TYPE);

            if (xo.hasAttribute(GeneticCode.GENETIC_CODE)) {
                dataTypeStr += "-" + xo.getStringAttribute(GeneticCode.GENETIC_CODE);
            }

 //           dataType = Alignment.findDataTypes(); 
//            		DataType.getRegisteredDataTypeByName(dataTypeStr);
              if (!types.containsKey(dataTypeStr)) {
            	  System.out.println("Data type " + types + " not supported yet" + Beast1to2Converter.NIY);
              }

              dataType = types.get(dataTypeStr);
        }

        for (int i = 0; i < xo.getChildCount(); i++) {

            Object child = xo.getChild(i);
            if (child instanceof DataType) {
                if (dataType != null) {
                    throw new XMLParseException("Multiple dataTypes defined for alignment element");
                }

                dataType = (DataType) child;
            }
        }

        return dataType;
    }

}
