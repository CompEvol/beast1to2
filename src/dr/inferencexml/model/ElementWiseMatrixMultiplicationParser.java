package dr.inferencexml.model;


import dr.inference.model.ElementWiseMatrixMultiplicationParameter;
import dr.inference.model.MatrixParameter;
import dr.xml.AbstractXMLObjectParser;
import dr.xml.XMLObject;
import dr.xml.XMLParseException;
import dr.xml.XMLSyntaxRule;

/**
 * Created by max on 11/30/15.
 */
public class ElementWiseMatrixMultiplicationParser extends AbstractXMLObjectParser {
    public final static String ELEMENT_WISE_MATRIX_MULTIPLICATION_PARAMETER="ElementWiseMatrixMultiplicationParameter";
    public final static String NAME="name";

    @Override
    public Object parseXMLObject(XMLObject xo) throws XMLParseException {
		System.out.println(getParserName() + " " + beast1to2.Beast1to2Converter.NIY);
		return null;
		/*
        final String name = xo.hasId() ? xo.getId() : null;
        MatrixParameter[] matList=new MatrixParameter[xo.getChildCount()];
        for (int i = 0; i <xo.getChildCount(); i++) {
            matList[i]=(MatrixParameter) xo.getChild(i);
        }

        return new ElementWiseMatrixMultiplicationParameter(name, matList);
    */
		}

    @Override
    public XMLSyntaxRule[] getSyntaxRules() {
        return new XMLSyntaxRule[0];
    }

    @Override
    public String getParserDescription() {
        return "Returns element wise matrix multiplication of a series of matrices";
    }

    @Override
    public Class getReturnType() {
        return ElementWiseMatrixMultiplicationParameter.class;
    }

    @Override
    public String getParserName() {
        return ELEMENT_WISE_MATRIX_MULTIPLICATION_PARAMETER;
    }
}
