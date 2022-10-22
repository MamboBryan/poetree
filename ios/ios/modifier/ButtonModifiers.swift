//
//  ButtonModifiers.swift
//  ios
//
//  Created by MamboBryan on 05/10/2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import Foundation
import SwiftUI

struct Filled : ViewModifier {
    
    var background: Color = Color("Primary")
    var foreground: Color = Color("OnPrimary")
    var radius: CGFloat = 10
    
    func body(content: Content) -> some View {
        return content
            .padding(.vertical, 10)
            .padding(.horizontal, 16)
            .foregroundColor(foreground)
            .background(RoundedRectangle(cornerRadius: radius).foregroundColor(background))
    }
    
}

struct Outlined : ViewModifier {
    
    var radius: CGFloat = 10
    
    func body(content: Content) -> some View {
        return content
            .padding(.vertical, 10)
            .padding(.horizontal, 16)
            .foregroundColor(Color("Primary"))
            .background(RoundedRectangle(cornerRadius: radius).stroke(Color("Primary"), lineWidth: 1))
            .animation(.easeInOut(duration: 0.15))
    }
    
}

struct Clear : ViewModifier {
    
    var radius: CGFloat = 10
    
    func body(content: Content) -> some View {
        return content
            .padding(.vertical, 10)
            .padding(.horizontal, 16)
            .foregroundColor(Color("Primary"))
    }
    
}
