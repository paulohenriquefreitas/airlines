package com.br.airlines.model;

public class Fare
{
    private String Fees;

    private String BasePrice;

    private String clazz;

    private String Tax;

    public String getFees ()
    {
        return Fees;
    }

    public void setFees (String Fees)
    {
        this.Fees = Fees;
    }

    public String getBasePrice ()
    {
        return BasePrice;
    }

    public void setBasePrice (String BasePrice)
    {
        this.BasePrice = BasePrice;
    }

    public String getClazz ()
    {
        return clazz;
    }

    public void setClass (String clazz)
    {
        this.clazz = clazz;
    }

    public String getTax ()
    {
        return Tax;
    }

    public void setTax (String Tax)
    {
        this.Tax = Tax;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [Fees = "+Fees+", BasePrice = "+BasePrice+", class = "+clazz+", Tax = "+Tax+"]";
    }
}