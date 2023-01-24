//
//  AppController.swift
//  ios
//
//  Created by MamboBryan on 01/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import common
import KMPNativeCoroutinesAsync

@MainActor
class AppController : ObservableObject {
    
    @Published var isLoading : Bool = false
    @Published var isShowingDialog : Bool = false

    @Published var dialog: (String, String)? = nil
    
    func showLoading(){
        isLoading = true
    }
    
    func hideLoading(){
        isLoading = false
    }
    
    func showDialog(title: String, message: String){
        dialog = (title, message)
        isShowingDialog = true
    }
    
    func hideDialog(){
        isShowingDialog = false
        dialog = nil
    }
    
    @Published var hasNetworkAccess:Bool?
    @Published var hasOnBoarded:Bool?
    @Published var isSignedIn : Bool?
    @Published var hasSetup : Bool?
    
    init (){
        
        observeNetworkState()
        observeOnBoardingStatus()
        observeSignInStatus()
        observeSetupStatus()
        
    }
    
    private func observeNetworkState(){
        Task {
            do {
                let stream = asyncStream(for: UserPreferences().hasNetworkConnectionNative)
                for try await hasNetwork in stream {
                    hasNetworkAccess = hasNetwork as? Bool
                }
            } catch {
                
            }
        }
    }
    
    private func observeOnBoardingStatus(){
        Task{
            do {
                let onBoardedStream = asyncStream(for: UserPreferences().isOnBoardedNative)
                for try await onBoarded in onBoardedStream{
                    hasOnBoarded = onBoarded as? Bool
                }
            } catch {
                print("OnBoarding Failed with error : \(error)")
            }
        }
    }
    
    private func observeSignInStatus(){
        Task {
            do {
                let signedInStream = asyncStream(for: UserPreferences().signedInNative)
                for try await signedIn in signedInStream {
                    isSignedIn = signedIn as? Bool
                }
            } catch {
                print("SignedIn Failed with error : \(error)")
            }
            
        }
    }
    
    private func observeSetupStatus(){
        Task {
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
