package com.example.mypictureoftheday.model.entity.earth

import com.google.gson.annotations.Expose

data class SunJ2000Position (
	@Expose val x : Double,
	@Expose val y : Double,
	@Expose val z : Double
)