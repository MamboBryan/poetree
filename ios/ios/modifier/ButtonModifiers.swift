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
    
    var background: Color = .accentColor
    var foreground: Color = .white
    var radius: CGFloat = 5
    
    func body(content: Content) -> some View {
        return content.padding().padding(.horizontal)
            .foregroundColor(foreground)
            .background(RoundedRectangle(cornerRadius: radius).foregroundColor(background))
    }
    
}
