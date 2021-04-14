package com.example.mypictureoftheday.model.entity.earth

import com.google.gson.annotations.Expose

data class CentroidCoordinates (
	@Expose val lat : Double,
	@Expose val lon : Double
)