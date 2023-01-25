//
//  AccountScreen.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import common
import KMPNativeCoroutinesAsync

struct AccountScreen: View {
    
    var isSettingUp : Bool
    
    @EnvironmentObject var controller : AppController
    
    @State var email : String = ""
    @State var username : String = ""
    @State var about : String = ""
   
    let dateFormatter: DateFormatter = {
            let formatter = DateFormatter()
            formatter.dateStyle = .long
            return formatter
        }()
    
     private let youngest = Calendar.current.date(byAdding: .year, value: -15, to: Date()) ?? Date()
    private let oldest = Calendar.current.date(byAdding: .year, value: -80, to: Date()) ?? Date()

    @State private var birthDate = Calendar.current.date(byAdding: .year, value: -7, to: Date()) ?? Date()
    @State private var selectedGender = "Female"
    let genders = ["Male", "Female", "Other"]
    
    var body: some View {
        VStack(alignment: .leading) {
            HStack {
                Image(uiImage: UIImage(systemName: "xmark") ??  UIImage())
                    .padding(16)
                Spacer()
                Text("\(PoetreeApp().name())").bold()
                Spacer()
                Image(uiImage: UIImage(systemName: "questionmark.circle") ??  UIImage())
                    .padding(16)
            }
            .background(Color("Surface"))
            VStack {
            
                if(isSettingUp){
                    HStack {
                        VStack(alignment: .leading) {
                            Text("Setup Account").font(.title.bold())
                            Text("Enter details to finish account setup")
                        }
                        Spacer()
                    }
                }
            
            VStack {
                
                if(!isSettingUp){
                    LineTextField(hint: "Email", error: "Invalid Email", textCase: .uppercase, isError: { email in
                        !email.isValidEmail()
                    }, text: $email).padding(.top)
                }
                
                LineTextField(hint: "Username", error: "Invalid Username", textCase: .uppercase, isError: { name in
                    name.isEmpty
                }, text: $username).padding(.top)
                
                LineTextField(hint: "About", error: "Invalid about", textCase: .uppercase, isError: {about in
                     about.isEmpty || about.count > 125
                }, text: $about).padding(.top)
                
                HStack{
                    Text("Select gender")
                    Spacer()
                    Picker("Select Gender", selection: $selectedGender) {
                        ForEach(genders, id: \.self) { gender in
                            Text(gender)
                        }
                    }
                }.padding(.top)
                
                VStack {
                    DatePicker(selection: $birthDate, in: oldest...youngest, displayedComponents: .date) {
                        Text("Select date of birth")
                    }
                }
            
            }
                VStack(alignment: .center){
                
                    Button {
                       update()
                    } label: {
                        Spacer()
                        Text("Finish")
                        Spacer()
                    }.filled(enabled: isUpdateButtonEnabled())
                        .padding(.vertical)
                    
                    if(isSettingUp){
                        Button("Sign Out") {
                            signOut()
                        }
                    }
                    
                }
                
                Spacer()
                
            }.padding()
        }
        .background(Color("Background"))
    }
    
    func isUpdateButtonEnabled() -> Bool {
        if(isSettingUp){
            return !username.isEmpty && !about.isEmpty && about.count < 125
        }else {
            return email.isValidEmail() && !username.isEmpty && !about.isEmpty && about.count < 125
        }
    }
    
    func update(){
        if(isSettingUp){
            setupAccount()
        } else {
            updateAccount()
        }
    }
    
    func updateAccount(){
        
        var gender: Int32 = 0
        if(selectedGender == "Female"){
            gender = 1
        }
        
        let formatter = DateFormatter()
        formatter.dateFormat = "dd-MM-yyyy"
        let date = formatter.string(from: birthDate)
        
        let request = UserUpdateRequest(username: username, email: email, dateOfBirth: date, gender: gender as? KotlinInt, bio: about)
        
        Task {
            do {
                
                controller.showLoading()
                
                let response = try await asyncFunction(for: UserRepository().updateUserNative(request: request))
                
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
    
    func setupAccount(){
        var gender: Int32 = 0
        if(selectedGender == "Female"){
            gender = 1
        }
        
        let formatter = DateFormatter()
        formatter.dateFormat = "dd-MM-yyyy"
        let date = formatter.string(from: birthDate)
        
        let request = SetupRequest(username: username, dateOfBirth: date, gender: gender, bio: about)
        
        Task {
            do {
                
                controller.showLoading()
                
                let response = try await asyncFunction(for: UserRepository().setupNative(request: request))
                
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
    
    func signOut(){
        UserPreferences().signOut()
    }
    
}

struct AccountScreen_Previews: PreviewProvider {
    static var previews: some View {
        AccountScreen(isSettingUp: false)
    }
}
