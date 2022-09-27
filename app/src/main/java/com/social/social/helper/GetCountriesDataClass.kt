package com.social.social.helper

import android.content.Context
import com.social.social.models.CountryModel
import org.json.JSONArray
import java.io.IOException
import java.util.*

class GetCountriesDataClass {

    private fun getJsonDataFromAsset(context: Context): String? {
        val jsonString: String
        try {
            jsonString =
                context.assets.open("countryCodes.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    fun getData(context: Context): ArrayList<CountryModel> {
        val list = ArrayList<CountryModel>()
        val jsonString = getJsonDataFromAsset(context)
        val jsonArray = JSONArray(jsonString)
        for (index in 0 until jsonArray.length()) {
            val countryName = jsonArray.getJSONObject(index).getString("name")
            val dialCode = jsonArray.getJSONObject(index).getString("dial_code")
            val code =
                jsonArray.getJSONObject(index).getString("code").toLowerCase(Locale.getDefault())
            val drawableId = context.resources.getIdentifier(code, "drawable", context.packageName)
            list.add(CountryModel(countryName, drawableId, dialCode))
        }
        return list
    }
}