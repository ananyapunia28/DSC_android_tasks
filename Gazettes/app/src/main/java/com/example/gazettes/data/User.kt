package com.example.gazettes.data


class User{

    var email: String? = null
    var pass: String? = null
    var cpass: String? = null
    var age: String? = null
    var phone: String? = null
    var address: String? = null
    var bio : String? = null

    constructor(
        email:String,
        pass:String,
        cpass:String,
        age:String,
        phone:String,
        address:String,
        bio:String,


    )
    {
        this.email = email
        this.pass = pass
        this.cpass = cpass
        this.age = age
        this.phone = phone
        this.address = address
        this.bio = bio


    }

}


//data class Profile(
//    val email: String? = null,
//    val pass: String? = null,
//    val cpass: String? = null,
//    val age: String? = null,
//    val phone: String? = null,
//    val address: String? = null,
//    val bio : String? = null
//// name nhi liya input? nhi
//)
//{
//}
