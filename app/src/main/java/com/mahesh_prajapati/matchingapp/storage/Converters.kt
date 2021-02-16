package com.mahesh_prajapati.matchingapp.storage

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mahesh_prajapati.matchingapp.storage.model.*

class Converters {
    @TypeConverter
    public fun fromValuesToCoordinates(value: Coordinates):String{
         return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesCoordinates(value: String): Coordinates{
        val typeToken = object : TypeToken< Coordinates>() {}.type
       return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToDob(value: Dob):String{
         return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesDob(value: String): Dob{
        val typeToken = object : TypeToken< Dob>() {}.type
       return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToId(value: Id):String{
         return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesId(value: String): Id{
        val typeToken = object : TypeToken< Id>() {}.type
       return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToInfo(value: Info):String{
         return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesInfo(value: String): Info{
        val typeToken = object : TypeToken< Info>() {}.type
       return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToLocation(value: Location):String{
         return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesLocation(value: String): Location{
        val typeToken = object : TypeToken< Location>() {}.type
       return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToLogin(value: Login):String{
        return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesLogin(value: String): Login{
        val typeToken = object : TypeToken< Login>() {}.type
        return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToName(value: Name):String{
        return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesName(value: String): Name{
        val typeToken = object : TypeToken< Name>() {}.type
        return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToPicture(value: Picture):String{
        return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesPicture(value: String): Picture{
        val typeToken = object : TypeToken< Picture>() {}.type
        return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToRegistered(value: Registered):String{
        return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesRegistered(value: String): Registered{
        val typeToken = object : TypeToken< Registered>() {}.type
        return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToStreet(value: Street):String{
        return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesStreet(value: String): Street{
        val typeToken = object : TypeToken< Street>() {}.type
        return Gson().fromJson(value,typeToken )
    }

    @TypeConverter
    public fun fromValuesToTimezone(value: Timezone):String{
        return Gson().toJson(value)
    }

    @TypeConverter
    public fun toOptionValuesTimezone(value: String): Timezone{
        val typeToken = object : TypeToken< Timezone>() {}.type
        return Gson().fromJson(value,typeToken )
    }
}


