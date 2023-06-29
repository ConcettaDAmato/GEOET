/*
 * GNU GPL v3 License
 *
 * Copyright 2019 Niccolo` Tubini
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

package it.geoframe.blogspot.geoet.radiation.methods;
import org.joda.time.DateTime;
//import it.geoframe.blogspot.geoet.data.Parameters;
import it.geoframe.blogspot.geoet.data.ProblemQuantities;
//import it.geoframe.blogspot.geoet.data.WindProfile;
//import it.geoframe.blogspot.geoet.prospero.data.Leaf;
import it.geoframe.blogspot.geoet.prospero.methods.*;
import oms3.annotations.Author;
import oms3.annotations.Bibliography;
import oms3.annotations.Description;
import oms3.annotations.Documentation;
import oms3.annotations.Keywords;
import oms3.annotations.License;

@Description("This class compute some of the quantities of the radiation balance considering the LAI.")
@Documentation("")
@Author(name = "Concetta D'Amato, Michele Bottazzi and Riccardo Rigon", contact = "concetta.damato@unitn.it")
@Keywords("")
@Bibliography("")
@License("General Public License Version 3 (GPLv3)")


public class ComputeRadiationQuantities {

	private ProblemQuantities variables;
	RadiationMethod radiationMethods 	= new RadiationMethod();
	SolarGeometry solarGeometry 		= new SolarGeometry();
	
	
	public void computeRadiationQuantities(DateTime date, double latitude, double longitude,double time, double leafAreaIndex, String typeOfCanopy, 
			double shortWaveRadiationDirect, double shortWaveRadiationDiffuse, double netRadiation) {
		
		variables = ProblemQuantities.getInstance();
		
		variables.solarElevationAngle = solarGeometry.getSolarElevationAngle(date, latitude,longitude,time);

		if (variables.solarElevationAngle < 0) {
			variables.shortwaveCanopySun=0;
			variables.shortwaveCanopyShade=0;}
		
		else{
		// RADIATION SUN		
			variables.shortwaveCanopySun = radiationMethods.computeAbsorbedRadiationSunlit(leafAreaIndex, variables.solarElevationAngle, shortWaveRadiationDirect, shortWaveRadiationDiffuse);

		// RADIATION SHADOW
			variables.shortwaveCanopyShade = radiationMethods.computeAbsorbedRadiationShadow(leafAreaIndex, variables.solarElevationAngle, shortWaveRadiationDirect, shortWaveRadiationDiffuse);
			}
				
		// Compute the area in sunlight and shadow

		if (leafAreaIndex <=1){
			
			if (variables.solarElevationAngle <0){
				variables.areaCanopySun = leafAreaIndex/2;
				variables.areaCanopyShade = leafAreaIndex/2;
				}
		
			else {
				variables.areaCanopySun = radiationMethods.computeSunlitLeafAreaIndex(typeOfCanopy,leafAreaIndex, variables.solarElevationAngle);
				variables.areaCanopyShade = leafAreaIndex - variables.areaCanopySun;
				}
		}
				
	
		else {
			variables.areaCanopySun=1;
			variables.areaCanopyShade=1;
			}
				
					
		variables.netLong = shortWaveRadiationDirect-netRadiation;
		variables.netLong=(variables.netLong<0)?0:variables.netLong;
				
		variables.incidentSolarRadiationSoil = shortWaveRadiationDirect + shortWaveRadiationDiffuse - variables.shortwaveCanopySun - variables.shortwaveCanopyShade-variables.netLong;
		variables.incidentSolarRadiationSoil=(variables.incidentSolarRadiationSoil<0)?0:variables.incidentSolarRadiationSoil;
				
		if (variables.solarElevationAngle <0) {
			variables.incidentSolarRadiationSoil=0;}
		
		
						
	}
	

	
}