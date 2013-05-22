/*
    Copyright 2012 Patrick von Reth <vonreth@kde.org>
    
    This file is part of SnarlNetworkBridge.

    SnarlNetworkBridge is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    SnarlNetworkBridge is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with SnarlNetworkBridge.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.snarl;

public class SNPProperty {
	private String name = null;
	private String value = null;

	/**
	 * Creates a new SNP Property containing name and value
	 * 
	 * @param name
	 *            the name of the Property
	 * @param value
	 *            the value of the Property
	 */
	public SNPProperty(String name, String value) {
		this.name = name;
		if (value!=null&&!value.equals(""))
			this.value = value;

	}

	/**
	 * Creates a new SNP Property containing name
	 * 
	 * @param name
	 *            the name of the Property
	 */

	public SNPProperty(String name) {
		this.name = name;
	}
	
	/**
	 * Sets the value of a property
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Returns the name of the property
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns the value of the Property
	 * 
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (value == null)
			return name;
		return "&" + name + "=" + value ;
	}
}
