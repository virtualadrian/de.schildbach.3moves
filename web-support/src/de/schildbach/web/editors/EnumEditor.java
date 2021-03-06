/*
 * Copyright 2007 the original author or authors.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation, version 2.1.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package de.schildbach.web.editors;

import java.beans.PropertyEditorSupport;

/**
 * @author Andreas Schildbach
 */
@SuppressWarnings("unchecked")
public class EnumEditor extends PropertyEditorSupport
{
	private Class enumType;
	private boolean emptyAsNull;

	public EnumEditor(Class enumType, boolean emptyAsNull)
	{
		this.enumType = enumType;
		this.emptyAsNull = emptyAsNull;
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException
	{
		Enum<?> value = null;
		if (!emptyAsNull || text.length() > 0)
			value = Enum.valueOf(enumType, text);
		setValue(value);
	}

	@Override
	public String getAsText()
	{
		if (!emptyAsNull || getValue() != null)
			return ((Enum<?>) getValue()).name();
		else
			return "";
	}
}
