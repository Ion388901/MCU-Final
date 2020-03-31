package com.example.segundaactividad

import java.io.Serializable

class MCUDude(val alias:String, val notes:String, val imageDude: String) : Serializable{
    public var name:String?= null
    public var hnotes:String?= null
    init{
        name = alias
        hnotes = notes
    }
}