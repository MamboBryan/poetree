//
//  ContentViewModel.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import common
import KMPNativeCoroutinesAsync

@MainActor
class ContentViewModel : ObservableObject {
    
    
    private let preference = UserPreferences()
    
    var isSignedIn = preference.signedIn
    var hasOnBoarded = preference.isOnBoarded
    var userHasSetup  = preference.isUserSetup
    
    init (){
        let handle = Task {
            do {
                let onBoarded = try await asyncFunction(for: preference.isOnBoardedNative)
                print("User has onBoarded: \(onBoarded)")
                
            } catch {
                print("Failed with error : \(error)")
            }
        }
    }
    
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
