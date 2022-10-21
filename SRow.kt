package com.example.expensetrackerproject.Settings

import com.example.expensetrackerproject.R

sealed class SRow(var text:String,var leadingIcon:Int,var trailingIcon:Int){
    object UpdateUserInfo:
        SRow(text="Update User Info", leadingIcon = R.drawable.update, trailingIcon = R.drawable.arrow)
    object ChangePassword :
        SRow(text="change Password" , leadingIcon = R.drawable.ic_lock, trailingIcon = R.drawable.arrow)
    object SwitchAccount :
        SRow(text="Switch Account" , leadingIcon = R.drawable.tabler_switch, trailingIcon = R.drawable.arrow)
    object SignOut : SRow(text="Sign Out" , leadingIcon = R.drawable.exit, trailingIcon = R.drawable.arrow)
}
