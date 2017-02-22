package br.edu.opet.biblioteca.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("LocalDateConverter")
public class LocalDateConverter implements Converter
{
    @Override
    public Object getAsObject(FacesContext pContexto, UIComponent pComponente, String pValor)
    {
        if (pValor == null || pValor.isEmpty())
        {
            return null;
        }

        try
        {
            LocalDate tData = LocalDate.parse(pValor, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            return tData;
        }
        catch (DateTimeParseException tExcept)
        {
            throw new ConverterException("Formato de data inválido. Exemplo: 13/11/2015.");
        }
    }

    @Override
    public String getAsString(FacesContext pContexto, UIComponent pComponente, Object pObjeto)
    {
        if (pObjeto == null)
        {
            return "";
        }

        return ((LocalDate) pObjeto).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
