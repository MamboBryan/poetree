//
//  AccountScreen.swift
//  ios
//
//  Created by MamboBryan on 22/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import common

struct AccountScreen: View {
    
    var isSettingUp : Bool
    @State var username : String = ""
    @State var email : String = ""
    @State var about : String = ""
    
    let dateFormatter: DateFormatter = {
            let formatter = DateFormatter()
            formatter.dateStyle = .long
            return formatter
        }()

    @State private var birthDate = Date()
    @State var selectedGender = 0
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
            
                HStack {
                    VStack(alignment: .leading) {
                        Text("Setup Account").font(.title.bold())
                        Text("Enter details to finish account setup")
                    }
                    Spacer()
                }
            
            VStack {
                
                LineTextField(hint: "Email", error: "Invalid Email", textCase: .lowercase,
                              isError: {email in
                    !email.isValidEmail()
                }, text: $email).padding(.top)
                
                LineTextField(hint: "Username", error: "Invalid Username", isError: {name in
                    name.isEmpty
                }, text: $username).padding(.top)
                
                LineTextField(hint: "About", error: "Invalid about", textCase: .lowercase,
                              isError: {about in
                    about.isEmpty
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
                    DatePicker(selection: $birthDate, in: ...Date(), displayedComponents: .date) {
                                    Text("Select date of birth")
                    }
                }
            
            }
                VStack(alignment: .center){
                
                    Button {
                        
                    } label: {
                        Spacer()
                        Text("Finish")
                        Spacer()
                    }.filled()
                        .padding(.vertical)
                    
                    Button("Sign Out") {
                        
                    }
                }
                
                Spacer()
                
            }.padding()
        }
        .background(Color("Background"))
    }
}

struct AccountScreen_Previews: PreviewProvider {
    static var previews: some View {
        AccountScreen(isSettingUp: false)
    }
}
