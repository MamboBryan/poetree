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
    
    @Published var hasOnBoarded:Bool?
    @Published var isSignedIn : Bool?
    @Published var hasSetup : Bool?
    
    init (){
       Task {
            do {
                let onBoardedStream = asyncStream(for: UserPreferences().isOnBoardedNative)
                for try await onBoarded in onBoardedStream{
                    hasOnBoarded = onBoarded as? Bool
                }
            } catch {
                print("OnBoarding Failed with error : \(error)")
            }
           
           do {
               let signedInStream = asyncStream(for: UserPreferences().signedInNative)
               for try await signedIn in signedInStream {
                   isSignedIn = signedIn as? Bool
               }
           } catch {
               print("SignedIn Failed with error : \(error)")
           }
           
           do {
               let setupStream = asyncStream(for: UserPreferences().isUserSetupNative)
               for try await isSetup in setupStream {
                   hasSetup = isSetup as? Bool
               }
           } catch {
               print("OnBoarding Failed with error : \(error)")
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
