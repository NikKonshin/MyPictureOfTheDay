package com.example.mypictureoftheday.model.entity.earth

import com.google.gson.annotations.Expose

data class AttitudeQuaternions (
	@Expose val q0 : Double,
	@Expose val q1 : Double,
	@Expose val q2 : Double,
	@Expose val q3 : Double
)