//
//  AuthScreen.swift
//  ios
//
//  Created by MamboBryan on 05/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import common
import KMPNativeCoroutinesAsync

enum Authentication {
    case sign_in, sign_up
}

struct AuthScreen: View {
    
    @EnvironmentObject var controller : AppController
    @StateObject var viewModel : AuthViewModel = AuthViewModel()

    @State var isSigningIn = true
    @State var email : String = ""
    @State var password: String = ""
    @State var confirmPassword : String = ""
    
    private let repository = AuthRepository()
    
    var body: some View {
        VStack{
            
            VStack(alignment: .leading){
                
                VStack{
                    Image(uiImage: UIImage(named: "AppImage") ?? UIImage())
                        .resizable()
                        .scaledToFit()
                        .frame(width: 24, height: 24, alignment: .center)
                        .padding(16)
                        
                }.background(Color("Primary"))
                    .cornerRadius(10)
                
                Spacer()
                
                VStack(alignment: .leading){
                    
                    Text(getData().0).font(Font.largeTitle)
                    
                    LineTextField(hint: "Email", error: "Invalid Email", isError: {email in
                        !email.isValidEmail()
                    }, text: $email).padding(.top)
                    
                    SecureLineTextField(hint: "Password", error: "Invalid Password", isError: {text in
                        false
                    }, text: $password).padding(.top)
        
                    if !isSigningIn {
                        SecureLineTextField(hint: "Confirm Password", error: "Invalid Password", isError: {text in
                                password == confirmPassword
                        }, text: $confirmPassword)
                        .padding(.top)
                    }
                    
                    
                    
                    
                        Button(getData().3) {
                            if isSigningIn {
                                signIn(email: email, password: password)
                            } else {
                                signUp(email: email, password: password)
                            }
                        }
                        
                        .filled(enabled: getIsEnabled())
                        .padding(.top)
                        
                }
                
                Spacer()
                
            }
            
            Spacer()
            HStack{
                Text(getData().1)
                Button(getData().2) {
                    isSigningIn.toggle()
                }.clear()
            }
            
        }
    }
    
    func getIsEnabled() -> Bool {
        if isSigningIn {
           return email.isValidEmail() && password.isValidPassword()
       } else {
          return  email.isValidEmail() && password.isValidEmail() && password == confirmPassword
       }
    }
    
    func getData() -> (String, String, String, String){
       if isSigningIn {
        return  ("Welcome \nBack", "Oh no, you don't have an account?", "Sign Up", "Sign In")
        } else {
        return ("Create \nAccount", "Wait, ain't you a veteran?", "Sign In", "Sign Up")
        }
    }
    
    func signIn(email: String, password: String){
        
        controller.showLoading()
        
        Task {
            do {
                let response = try await asyncFunction(for: repository.signInNative(email: email, password: password))
                
                controller.hideLoading()
                
                if response.isSuccessful {
                    controller.showDialog(title: "Success", message: response.message)
                } else {
                    controller.showDialog(title: "Error", message: response.message)
                }
            } catch {
                controller.hideLoading()
                controller.showDialog(title: "Error", message: error.localizedDescription)
            }
            
        }
        
    }
    
    func signUp(email: String, password: String){
        controller.showLoading()
        
        Task {
        
            do {
                let response = try await asyncFunction(for: repository.signUpNative(email: email, password: password))
                
                controller.hideLoading()
                
                if response.isSuccessful {
                    controller.showDialog(title: "Success", message: response.message)
                } else {
                    controller.showDialog(title: "Error", message: response.message)
                }
            } catch {
                controller.hideLoading()
                controller.showDialog(title: "Error", message: error.localizedDescription)
            }
            
        }
        
    }
    
}

struct AuthScreen_Previews: PreviewProvider {
    static var previews: some View {
        AuthScreen()
    }
}
