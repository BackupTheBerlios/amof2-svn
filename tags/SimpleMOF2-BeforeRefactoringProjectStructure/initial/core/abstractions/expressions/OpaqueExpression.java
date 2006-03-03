package core.abstractions.expressions;


/**
 * <b>OpaqueExpression</b>, superClass = {core.abstractions.expressions.ValueSpecification}
 */
public interface OpaqueExpression extends core.abstractions.expressions.ValueSpecification
{

    /**
     * <b>body</b>, multiplicity=(1,1)
     */
    public String getBody();

    public void setBody(String value);

    /**
     * <b>language</b>, multiplicity=(0,1)
     */
    public String getLanguage();

    public void setLanguage(String value);

}

