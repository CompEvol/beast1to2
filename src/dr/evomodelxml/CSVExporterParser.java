/*
 * CSVExporterParser.java
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

package dr.evomodelxml;

import dr.xml.*;

/**
 * @author Joseph Heled
 *         <p/>
 *         Very lame so far. need checks for possible qouting of elements.
 */
public class CSVExporterParser extends AbstractXMLObjectParser {

    final static public String CSV_EXPORT = "CSVexport";

    @Override
	public String getParserName() {
        return CSV_EXPORT;
    }

    @Override
	public String getParserDescription() {
        return "Write tabular data as an CSV file.";
    }

    @Override
	public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        final String fileName = xo.getStringAttribute(FILE_NAME);

        final PrintWriter writer = XMLParser.getFilePrintWriter(xo, fileName);

        final String sep = xo.getAttribute(SEPARATOR, "\t");

        for (int k = 0; k < xo.getChildCount(); ++k) {
            final XMLObject columns = (XMLObject) xo.getChild(k);
            if (columns.getName().equals(COLUMNS)) {
                TabularData source = (TabularData) columns.getChild(TabularData.class);
                // look for columns
                List<Integer> iColumns = new ArrayList<Integer>();
                List<RealParameter[]> adds = new ArrayList<RealParameter[]>();
                int maxAdds = 0;

                for (int nc = 0; nc < columns.getChildCount(); ++nc) {
                    final Object child = columns.getChild(nc);
                    if (child instanceof XMLObject) {
                        final XMLObject column = (XMLObject) child;
                        if (column.getName().equals(COLUMN)) {
                            final String name = column.getStringAttribute(COLUMN_NAME);
                            final int n = source.getColumn(name);
                            if (n < 0) {
                                throw new XMLParseException("column '" + name + "' not found in log.");
                            }
                            iColumns.add(n);

                            final int nAddtional = column.getChildCount();
                            RealParameter[] additionals = nAddtional > 0 ? new RealParameter[nAddtional] : null;
                            for (int nc1 = 0; nc1 < nAddtional; ++nc1) {
                                additionals[nc1] = (RealParameter) column.getChild(nc1);
                            }
                            maxAdds = Math.max(maxAdds, nAddtional);

                            adds.add(additionals);
                        }
                    }
                }

                final boolean hasAllAttribute = columns.hasAttribute(ALL_COLUMNS);


                if (iColumns.size() == 0) {
                    // when no columns specified - default is all
                    if (!hasAllAttribute || columns.getBooleanAttribute(ALL_COLUMNS)) {
                        for (int nc = 0; nc < source.nColumns(); ++nc) {
                            iColumns.add(nc);
                            adds.add(null);
                        }
                    }
                } else {
                    // some column specification
                    if (hasAllAttribute && columns.getBooleanAttribute(ALL_COLUMNS)) {
                        for (int nc = 0; nc < source.nColumns(); ++nc) {
                            if (!iColumns.contains(nc)) {
                                iColumns.add(nc);
                                adds.add(null);
                            }
                        }
                    }
                }

                if (columns.hasAttribute(AS_ROWS) && columns.getBooleanAttribute(AS_ROWS)) {
                    for (int nc = 0; nc < iColumns.size(); ++nc) {
                        writer.print(source.columnName(iColumns.get(nc)));

                        for (int nr = 0; nr < source.nRows(); ++nr) {
                            final Object value = source.data(nr, iColumns.get(nc));
                            writer.print(sep);
                            writer.print(value);

                        }
                        for (int nr = 0; nr < maxAdds; ++nr) {
                            final RealParameter[] addsnc = adds.get(nc);
                            if (addsnc != null && nr < addsnc.length) {
                                final Object value = addsnc[nr].getParameterValues()[0];
                                writer.print(sep);
                                writer.print(value);
                            }
                        }
                        writer.println();
                    }

                } else {
                    for (int nc = 0; nc < iColumns.size(); ++nc) {
                        if (nc > 0) {
                            writer.print(sep);
                        }
                        writer.print(source.columnName(iColumns.get(nc)));
                    }
                    writer.println();

                    for (int nr = 0; nr < source.nRows(); ++nr) {
                        for (int nc = 0; nc < iColumns.size(); ++nc) {
                            if (nc > 0) {
                                writer.print(sep);
                            }
                            final Object value = source.data(nr, iColumns.get(nc));
                            writer.print(value);
                        }
                        writer.println();
                    }

                    for (int nr = 0; nr < maxAdds; ++nr) {
                        for (int nc = 0; nc < iColumns.size(); ++nc) {
                            if (nc > 0) {
                                writer.print(sep);
                            }
                            final RealParameter[] addsnc = adds.get(nc);
                            if (addsnc != null && nr < addsnc.length) {
                                final Object value = addsnc[nr].getParameterValues()[0];
                                writer.print(value);
                            }
                        }
                        writer.println();
                    }
                }
            }
        }
        writer.close();
        return null;
    */
		}

    public static final String FILE_NAME = "fileName";
    public static final String SEPARATOR = "separator";
    public static final String COLUMNS = "columns";
    public static final String ALL_COLUMNS = "all";
    public static final String AS_ROWS = "rows";
    public static final String COLUMN = "CSVcolumn";
    public static final String COLUMN_NAME = "name";

    @Override
	public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[]{
//                new StringAttributeRule(FILE_NAME,
//                        "The name of a BEAST log file (can not include trees, which should be logged separately"),
//                new StringAttributeRule(SEPARATOR, "Values separator (default is tab)", true),
//                new ElementRule(COLUMNS, new XMLSyntaxRule[]{
//                        AttributeRule.newBooleanRule(ALL_COLUMNS, true,
//                                "Dump all columns. default is TRUE when no columns are specified, FALSE otherwise"),
//                        AttributeRule.newBooleanRule(AS_ROWS, true,
//                                "Write data in rows (default is columns)"),
//                        new ElementRule(TabularData.class),
//                        new ElementRule(COLUMN, new XMLSyntaxRule[]{
//                                AttributeRule.newStringArrayRule(COLUMN_NAME),
//                                new ElementRule(RealParameter.class)
//                        }, "column name", 0, Integer.MAX_VALUE)
//                }, "A subset of columns from one source", 1, Integer.MAX_VALUE)
        };
    }


    @Override
	public Class getReturnType() {
        return CSVExporterParser.class; //TODO write CSVExporter
    }
}
