/*
* GNU GPL v3 License
 *
 * Copyright 2019 Concetta D'Amato
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.geoframe.blogspot.geoet.rootdensity.methods;
import it.geoframe.blogspot.geoet.inout.InputTimeSeries;
import it.geoframe.blogspot.geoet.data.ProblemQuantities;

/**
 * 
 * @author Concetta D'Amato
 */


public class CostantMethod extends RootDensity{

	private ProblemQuantities variables;
	private InputTimeSeries input;
	
	
	public double [] computeRootDensity (double zRef) {
		
		variables = ProblemQuantities.getInstance();
		input = InputTimeSeries.getInstance();
		
		variables.rootDensity = input.rootDensityIC;
		
	return variables.rootDensity.clone();
	}
}
 