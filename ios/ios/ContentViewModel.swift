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
    
    var isSignedIn =  UserPreferences().signedIn
    var hasOnBoarded =  UserPreferences().isOnBoarded
    var userHasSetup  =  UserPreferences().isUserSetup
    
    init (){
       Task {
            do {
                let onBoardedStream = asyncStream(for: UserPreferences().isOnBoardedNative)
                for try await onBoarded in onBoardedStream{
                    print("User has onBoarded: \(onBoarded)")
                }
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
