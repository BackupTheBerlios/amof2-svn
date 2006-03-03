package core.abstractions.expressions;


public interface OpaqueExpression extends core.abstractions.expressions.ValueSpecification
{

    public String getBody();

    public void setBody(String value);

    public String getLanguage();

    public void setLanguage(String value);

}

