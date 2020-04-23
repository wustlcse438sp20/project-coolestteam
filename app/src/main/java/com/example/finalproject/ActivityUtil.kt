package com.example.finalproject

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

class ActivityUtil {
    fun addFragmentToActivty(manager: FragmentManager, fragment: Fragment, frameId: Int) {
        var transaction: FragmentTransaction = manager.beginTransaction()
        transaction.add(frameId, fragment)
        transaction.commit()
    }

    fun removeFragmentFromActivity(manager: FragmentManager, fragment: Fragment) {
        var transaction: FragmentTransaction = manager.beginTransaction()
        transaction.remove(fragment)
        transaction.commit()
    }

}