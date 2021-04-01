package com.example.mypictureoftheday.model.entity.earth

import com.google.gson.annotations.Expose

data class PicturesEarthData (
	@Expose val identifier : Long,
	@Expose val caption : String,
	@Expose val image : String,
	@Expose val version : Int,
	@Expose val centroid_coordinates : CentroidCoordinates,
	@Expose val dscovr_j2000_position : DscovrJ2000Position,
	@Expose val lunar_j2000_position : LunarJ2000Position,
	@Expose val sun_j2000_position : SunJ2000Position,
	@Expose val attitude_quaternions : AttitudeQuaternions,
	@Expose val date : String,
	@Expose val coords : Coords
)