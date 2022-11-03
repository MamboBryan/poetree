//
//  LineField.swift
//  ios
//
//  Created by MamboBryan on 01/11/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI

struct LineTextField: View {
    
    var hint: String = ""
    var error: String = ""
    var textCase: Text.Case = .lowercase
    var capitalization: UITextAutocapitalizationType = .none
    
    var isError : (String) -> Bool
    
    @Binding var text: String
    @State private var height: CGFloat = 2
    
    var body: some View {
        
        VStack(alignment: .leading) {
            if !text.isEmpty {
                Text(hint).font(Font.caption).foregroundColor(getColor()).transition(.slide)
            }
            TextField(hint, text: $text).textCase(textCase).autocapitalization(capitalization)
            getColor().frame(height: height).cornerRadius(10)
            if !text.isEmpty && isError(text) {
                Text(error).font(Font.caption).foregroundColor(Color.red).transition(.slide)
            }

        }
        
    }
    
    func getColor() -> Color {
        if !text.isEmpty && isError(text) {
            return Color.red
        } else {
            return Color("Primary")
        }
    }
    
}

struct SecureLineTextField: View {
    
    var hint: String = ""
    var error: String = ""
    var textCase: Text.Case = .lowercase
    var capitalization: UITextAutocapitalizationType = .none
    
    var isError : (String) -> Bool
    
    @Binding var text: String
    @State private var height: CGFloat = 2
    
    var body: some View {
        
        VStack(alignment: .leading) {
            if !text.isEmpty {
                Text(hint).font(Font.caption).foregroundColor(getColor()).transition(.slide)
            }
            SecureField(hint, text: $text).autocapitalization(capitalization)
            getColor().frame(height: height).cornerRadius(10)
            if !text.isEmpty && isError(text) {
                Text(error).font(Font.caption).foregroundColor(Color.red).transition(.slide)
            }

        }
        
    }
    
    func getColor() -> Color {
        if !text.isEmpty && isError(text) {
            return Color.red
        } else {
            return Color("Primary")
        }
    }
    
}

//struct LineField_Previews: PreviewProvider {
//    static var previews: some View {
//
//        LineTextField(isError: { text in
//            text.isEmpty
//        }, text: Binding<String>(projectedValue: Binding.init(get: "Strng", set: "String", )))
//
//    }
//}
