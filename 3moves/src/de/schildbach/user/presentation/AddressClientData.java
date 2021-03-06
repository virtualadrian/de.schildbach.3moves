/*
 * Copyright 2001-2011 the original author or authors.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.schildbach.user.presentation;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.NotSerializableException;
import java.util.Date;

import de.schildbach.web.crypto.ClientData;

/**
 * @author Andreas Schildbach
 */
public class AddressClientData extends ClientData
{
	private String username;
	private String transport;
	private String address;

	public AddressClientData()
	{
	}

	public AddressClientData(Date createdAt, String username, String transport, String address)
	{
		setCreatedAt(createdAt);
		setUsername(username);
		setTransport(transport);
		setAddress(address);
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getTransport()
	{
		return transport;
	}

	public void setTransport(String transport)
	{
		this.transport = transport;
	}

	@Override
	public void writeData(DataOutput out) throws IOException
	{
		out.writeByte(1); // version
		out.writeUTF(getUsername());
		out.writeLong(getCreatedAt().getTime());
		out.writeByte(transportToNumber(getTransport()));
		out.writeUTF(getAddress());
	}

	@Override
	public void readData(DataInput in) throws IOException
	{
		switch (in.readByte())
		{
			case 1:
				setUsername(in.readUTF());
				setCreatedAt(new Date(in.readLong()));
				setTransport(numberToTransport(in.readByte()));
				setAddress(in.readUTF());
				break;

			default:
				throw new NotSerializableException("unknown version");
		}
	}

	private static int transportToNumber(String transport)
	{
		if (transport.equals("email"))
			return 0;
		if (transport.equals("xmpp"))
			return 1;
		throw new IllegalArgumentException("unknown transport");
	}

	private static String numberToTransport(int number)
	{
		switch (number)
		{
			case 0:
				return "email";
			case 1:
				return "xmpp";
			default:
				throw new IllegalArgumentException("unknown transport number");
		}
	}
}
