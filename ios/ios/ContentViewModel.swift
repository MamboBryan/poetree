//
//  ContentViewModel.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import common

@MainActor
class ContentViewModel : ObservableObject {
    
    var isSignedIn = UserPreferences().signedIn
    var hasOnBoarded = UserPreferences().isOnBoarded
    var userHasSetup  = UserPreferences().isUserSetup
    
    func setUserIsAuthenticated() {
        UserPreferences().signedIn(bool: true)
    }
    
    func setUserHasOnBoarded(){
        UserPreferences().userHasOnBoarded()
    }
    
    func setUserHasSetup(){
        UserPreferences().userHasSetup()
    }
    
}
